package es.upm.miw.airquality;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.upm.miw.airquality.models.Cities;
import es.upm.miw.airquality.models.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static es.upm.miw.airquality.MainActivity.API_BASE_URL;
import static es.upm.miw.airquality.MainActivity.KEY_ID;
import static es.upm.miw.airquality.MainActivity.KEY_LOCATION;
import static es.upm.miw.airquality.MainActivity.LOG_TAG;

public class LocationDetailAdapter extends ArrayAdapter {

    private Context _contexto;
    private int _idLayout;
    private List<Result> _resultados;

    public LocationDetailAdapter(Context context, int idLayout, List<Result> results) {
        super(context, idLayout, results);
        this._contexto = context;
        this._idLayout = idLayout;
        this._resultados = results;
        setNotifyOnChange(true);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /**
         * Comprobar si existe una lista convertible para permitir hacer scroll
         * sobre la lista que aparece en pantalla. Si no la tiene, la crea
         * con el inflador que trae el contexto de la actividad anterior
         */
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) _contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this._idLayout, null);
        }

        /**
         * Traer el resultado de la lista que este en "position"
         * Comprobar que al menos hay un resultado
         * Asignar las vistas a los datos
         */
        Result result = _resultados.get(position);
        if (result != null) {

            TextView tvDetailPosition = convertView.findViewById(R.id.tvDetailListPosition);
            TextView tvDetail = convertView.findViewById(R.id.tvDetailListLocationCode);

            tvDetailPosition.setText(Integer.toString(position + 1));
            tvDetail.setText(result.getMeasurements().get(position).getParameter());

        }
        return convertView;
    }
}

