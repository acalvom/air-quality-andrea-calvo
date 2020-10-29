package es.upm.miw.airquality;

import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

public class CityDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        // Recupero el recurso asociado en la vista
        TextView tvPosition = findViewById(R.id.tvPosition);
        TextView tvCityName = findViewById(R.id.tvCityName);

        // Recupero el bundle con los datos
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            int positionReceived = bundle.getInt(MainActivity.KEY_ID);
            String cityReceived = bundle.getString(MainActivity.KEY_CITY);

            // Asignar datos en la vista
            tvPosition.setText(String.format(Locale.getDefault(), "%2d", positionReceived));
            tvCityName.setText(cityReceived);
        }
    }
}
