package es.upm.miw.airquality;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.ListView;
import android.widget.Toast;

import es.upm.miw.airquality.models.Cities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static es.upm.miw.airquality.MainActivity.API_BASE_URL;

import static es.upm.miw.airquality.MainActivity.LOG_TAG;

public class LocationDetail extends AppCompatActivity {

    LocationDetailAdapter locationDetailAdapter;
    ListView lvLocationDetailsList;

    private ICityRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ICityRESTAPIService.class);

        // Recupero el recurso asociado en la vista
        lvLocationDetailsList = findViewById(R.id.lvLocationDetailList);

        // Recupero el bundle con los datos
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            int positionReceived = bundle.getInt(LocationsActivity.KEY_ID);
            String locationReceived = bundle.getString(LocationsActivity.KEY_LOCATION);

            getLocationDetail(locationReceived);
        }
    }

    public void getLocationDetail(String locationReceived) {
        // Realiza la llamada por nombre de ciudad
        Call<Cities> call_async = apiService.getAllMeasurementsFromCity(locationReceived);
        Log.i(LOG_TAG, "getLocation => location=" + locationReceived);

        // Asíncrona
        call_async.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                final Cities detailsList = response.body();
                if (null != detailsList) {
                    for (int i = 0; i < detailsList.getResults().size(); i++){
                        locationDetailAdapter = new LocationDetailAdapter(
                                getApplicationContext(),
                                R.layout.activity_location_detail_item,
                                detailsList.getResults().get(i).getMeasurements()
                        );
                        lvLocationDetailsList.setAdapter(locationDetailAdapter);
                    }

                } else {
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
