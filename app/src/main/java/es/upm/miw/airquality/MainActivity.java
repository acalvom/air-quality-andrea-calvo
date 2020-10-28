package es.upm.miw.airquality;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// Firebase
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import es.upm.miw.airquality.models.Cities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String API_BASE_URL = "https://api.openaq.org";

    final static String LOG_TAG = "MiW";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private static final int RC_SIGN_IN = 2018;

    private TextView tvResponse;
    private EditText etCityName;

    private ICityRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnLogout).setOnClickListener(this);

        tvResponse = (TextView) findViewById(R.id.tvResponse);
        etCityName = (EditText) findViewById(R.id.etCityName);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ICityRESTAPIService.class);

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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        mFirebaseAuth.signOut();
        Log.i(LOG_TAG, getString(R.string.signed_out));
    }

    public void getAllCities(View v) {
        Call<Cities> call_async = apiService.getAllCities();

        call_async.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                Cities cityList = response.body();
                if (null != cityList) {
                    for (int i = 0; i < cityList.getResults().size(); i++) {
                        tvResponse.append(i +
                                " - [" + cityList.getResults().get(i).getLocation()+ "] " +
                                " - [" + cityList.getResults().get(i).getCity()+ "] " +
                                "\n");
                    }

                    Log.i(LOG_TAG, "getAllCities => respuesta=" + cityList.getResults());
                } else {
                    tvResponse.setText(getString(R.string.strError));
                    Log.i(LOG_TAG, getString(R.string.strError));
                }
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

    public void getByCity(View v) {
        String cityName = etCityName.getText().toString();
        tvResponse.setText("");

        // Realiza la llamada por nombre de ciudad
        Call<Cities> call_async = apiService.getAirQualityByLocation(cityName);
        Log.i(LOG_TAG, "getByCity => ciudad=" + cityName);

        // Asíncrona
        call_async.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                Cities city = response.body();
                if (null != city) {
                    for (int i = 0; i < city.getResults().size(); i++) {
                        tvResponse.append(i +
                                " - [" + city.getResults().get(i).getLocation()+ "] " +
                                " - [" + city.getResults().get(i).getCity()+ "] " +
                                "\n");
                    }

                    Log.i(LOG_TAG, "obtenerInfoCiudad => respuesta=" + city);
                } else {
                    tvResponse.setText(getString(R.string.strError));
                    Log.i(LOG_TAG, getString(R.string.strError));
                }
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
