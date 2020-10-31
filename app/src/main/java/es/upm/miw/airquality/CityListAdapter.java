package es.upm.miw.airquality;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.airquality.models.Result;

public class CityListAdapter extends ArrayAdapter {

    private Context _contexto;
    private int _idLayout;
    private List<Result> _resultados;

    public CityListAdapter(Context context, int idLayout, List<Result> results) {
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

            TextView tvPosition = convertView.findViewById(R.id.tvListPosition);
            TextView tvCity = convertView.findViewById(R.id.tvListCityName);

            tvPosition.setText(Integer.toString(position + 1));
            tvCity.setText(result.getCity());
        }
        return convertView;
    }

}
