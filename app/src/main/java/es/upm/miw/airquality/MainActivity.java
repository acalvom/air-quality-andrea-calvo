package es.upm.miw.airquality;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// Firebase
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import es.upm.miw.airquality.models.Cities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity implements View.OnClickListener {

    static final String API_BASE_URL = "https://api.openaq.org";
    final static String LOG_TAG = "MiW";

    // Firebase authentication variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // Firebase database variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    private static final int RC_SIGN_IN = 2018;
    final static String KEY_ID = "KEY_ID";
    final static String KEY_CITY = "KEY_CITY";
    final static String KEY_LOCATION = "KEY_LOCATION";
    final static String KEY_DETAIL = "KEY_DETAIL";

    private ImageButton ibUploadToCloud;
    ListView lvCityList;
    CityListAdapter cityListAdapter;

    private ICityRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnLogout).setOnClickListener(this);

        ibUploadToCloud = (ImageButton) findViewById(R.id.ibUploadToCloud);
        lvCityList = (ListView) findViewById(R.id.lvCityList);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ICityRESTAPIService.class);

        // Get instance of Firebase database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("measurement");

        // Get instance of Firebase authentication
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    CharSequence username = user.getDisplayName();
                    Toast.makeText(MainActivity.this, getString(R.string.firebase_user_fmt, username), Toast.LENGTH_LONG).show();
                    Log.i(LOG_TAG, "onAuthStateChanged() " + getString(R.string.firebase_user_fmt, username));
                    ((TextView) findViewById(R.id.tvLoggedUser)).setText(getString(R.string.firebase_user_fmt, username));
                } else {
                    // user is signed out
                    startActivityForResult(
                            // Get an instance of AuthUI based on the default app
                            AuthUI.getInstance().
                                    createSignInIntentBuilder().
                                    setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()
                                    )).
                                    setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */).
                                    build(),
                                    RC_SIGN_IN
                    );
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.signed_in, Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG, "onActivityResult " + getString(R.string.signed_in));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.signed_cancelled, Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG, "onActivityResult " + getString(R.string.signed_cancelled));
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        mFirebaseAuth.signOut();
        Log.i(LOG_TAG, getString(R.string.signed_out));
    }

    public void getAllCities(final View v) {
        Call<Cities> call_async = apiService.getAllCities();

        call_async.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                final Cities cityList = response.body();

                if (null != cityList) {
                    cityListAdapter = new CityListAdapter(
                            getApplicationContext(),
                            R.layout.activity_main_item,
                            cityList.getResults()
                    );
                    lvCityList.setAdapter(cityListAdapter);
                    lvCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String texto = "Opción elegida (" +
                                    position +
                                    "): " +
                                    cityList.getResults().get(position).getCity();

                            Log.i(LOG_TAG, texto);
                            String cityName = cityList.getResults().get(position).getCity();

                            // Clave valor. Lo que le pasas a la siguiente aplicacion
                            Bundle bundle = new Bundle();
                            bundle.putInt(KEY_ID, position);
                            bundle.putString(KEY_CITY, cityName);

                            // Intent es lo que necesitamos para pasar de una actividad a otra
                            Intent intent = new Intent(getApplicationContext(), LocationsActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });

                    Log.i(LOG_TAG, "getAllCities => respuesta=" + cityList.getResults());
                } else {
                    Log.i(LOG_TAG, getString(R.string.strError));
                }

                //  Send button sends a message and clears the EditText. Send message to Backend
                ibUploadToCloud.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < cityList.getResults().size(); i++) {
                            mMessagesDatabaseReference.push().setValue(cityList.getResults().get(i).getCity());
                            Log.i(LOG_TAG, "Update to Cloud => city=" + cityList.getResults().get(i).getCity());
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "ERROR: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
