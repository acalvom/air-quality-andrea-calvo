package es.upm.miw.airquality;

import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import es.upm.miw.airquality.models.Cities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static es.upm.miw.airquality.MainActivity.API_BASE_URL;
import static es.upm.miw.airquality.MainActivity.LOG_TAG;

public class CityDetail extends AppCompatActivity {

    String cityReceived = "";
    CityAdapter cityAdapter;
    ListView lvCityDetail;

    private ICityRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ICityRESTAPIService.class);

        // Recupero el recurso asociado en la vista
        TextView tvPosition = findViewById(R.id.tvPosition);
        TextView tvCityName = findViewById(R.id.tvCityName);
        lvCityDetail = findViewById(R.id.lvCityDetail);


        // Recupero el bundle con los datos
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            int positionReceived = bundle.getInt(MainActivity.KEY_ID);
            String cityReceived = bundle.getString(MainActivity.KEY_CITY);

            getByCity(cityReceived);

            // Asignar datos en la vista
            //tvPosition.setText(String.format(Locale.getDefault(), "%2d", positionReceived));
            //tvCityName.setText(cityReceived);
        }
    }

    public void getByCity(String cityReceived) {
        //String cityName = etCityName.getText().toString();
        //tvResponse.setText("");

        // Realiza la llamada por nombre de ciudad
        Call<Cities> call_async = apiService.getAirQualityByLocation(cityReceived);
        Log.i(LOG_TAG, "getByCity => ciudad=" + cityReceived);

        // Asíncrona
        call_async.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                Cities city = response.body();
                if (null != city) {

                    cityAdapter = new CityAdapter(
                            getApplicationContext(),
                            R.layout.result_item,
                            city.getResults()
                    );
                    lvCityDetail.setAdapter(cityAdapter);
                    /*
                    for (int i = 0; i < city.getResults().size(); i++) {
                        tvResponse.append(i +
                                " - [" + city.getResults().get(i).getLocation()+ "] " +
                                " - [" + city.getResults().get(i).getCity()+ "] " +
                                "\n");
                    }
                     */

                    Log.i(LOG_TAG, "obtenerInfoCiudad => respuesta=" + city);
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
