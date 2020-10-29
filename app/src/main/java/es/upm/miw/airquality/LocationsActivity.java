package es.upm.miw.airquality;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import es.upm.miw.airquality.models.Cities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static es.upm.miw.airquality.MainActivity.API_BASE_URL;
import static es.upm.miw.airquality.MainActivity.KEY_ID;
import static es.upm.miw.airquality.MainActivity.KEY_LOCATION;
import static es.upm.miw.airquality.MainActivity.LOG_TAG;

public class LocationsActivity extends AppCompatActivity {

    String cityReceived = "";
    LocationsAdapter locationsAdapter;
    ListView lvLocationsList;

    private ICityRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ICityRESTAPIService.class);

        // Recupero el recurso asociado en la vista
        TextView tvPosition = findViewById(R.id.tvPosition);
        TextView tvCityName = findViewById(R.id.tvCityName);
        lvLocationsList = findViewById(R.id.lvLocationsList);


        // Recupero el bundle con los datos
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            int positionReceived = bundle.getInt(KEY_ID);
            String cityReceived = bundle.getString(MainActivity.KEY_CITY);

            getAllLocationsByCity(cityReceived);

            // Asignar datos en la vista
            //tvPosition.setText(String.format(Locale.getDefault(), "%2d", positionReceived));
            //tvCityName.setText(cityReceived);
        }
    }

    public void getAllLocationsByCity(String cityReceived) {

        // Realiza la llamada por nombre de ciudad
        Call<Cities> call_async = apiService.getAllLocationsFromCity(cityReceived);
        Log.i(LOG_TAG, "getByCity => ciudad=" + cityReceived);

        // Asíncrona
        call_async.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                final Cities locationsList = response.body();
                if (null != locationsList) {

                    locationsAdapter = new LocationsAdapter(
                            getApplicationContext(),
                            R.layout.result_item,
                            locationsList.getResults()
                    );
                    lvLocationsList.setAdapter(locationsAdapter);
                    lvLocationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String texto = "Opción elegida (" +
                                    position +
                                    "): " +
                                    //parent.getItemAtPosition(position).toString();
                                    locationsList.getResults().get(position).getLocation();

                            Log.i(LOG_TAG, texto);
                            String locationCode = locationsList.getResults().get(position).getLocation();

                            // Clave valor. Lo que le pasas a la siguiente aplicacion
                            Bundle bundle = new Bundle();
                            bundle.putInt(KEY_ID, position);
                            bundle.putString(KEY_LOCATION, locationCode);

                            // Intent es lo que necesitamos para pasar de una actividad a otra
                            Intent intent = new Intent(getApplicationContext(), CityDetail.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });

                    Log.i(LOG_TAG, "obtenerInfoCiudad => respuesta=" + locationsList);
                } else {
                    //tvResponse.setText(getString(R.string.strError));
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
