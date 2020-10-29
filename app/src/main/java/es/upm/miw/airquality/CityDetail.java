package es.upm.miw.airquality;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import es.upm.miw.airquality.models.Cities;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static es.upm.miw.airquality.MainActivity.API_BASE_URL;
import static es.upm.miw.airquality.MainActivity.KEY_ID;
import static es.upm.miw.airquality.MainActivity.KEY_LOCATION;
import static es.upm.miw.airquality.MainActivity.LOG_TAG;

public class CityDetail extends AppCompatActivity {

    AirQualityAdapter airQualityAdapter;
    ListView lvLocationDetailsList;

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

        lvLocationDetailsList = findViewById(R.id.lvLocationDetailList);

        // Recupero el bundle con los datos
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            int positionReceived = bundle.getInt(MainActivity.KEY_ID);
            String locationReceived = bundle.getString(MainActivity.KEY_LOCATION);

            getLocationDetail(locationReceived);

            // Asignar datos en la vista
            //tvPosition.setText(String.format(Locale.getDefault(), "%2d", positionReceived));
            //tvCityName.setText(cityReceived);
        }
    }

    public void getLocationDetail(String location) {



    }
}
