package es.upm.miw.airquality;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import es.upm.miw.airquality.models.Result;

import static es.upm.miw.airquality.MainActivity.LOG_TAG;

public class LocationsAdapter extends ArrayAdapter {

    private Context _contexto;
    private int _idLayout;
    private List<Result> _resultados;

    public LocationsAdapter(Context context, int idLayout, List<Result> results) {
        super(context, idLayout, results);
        this._contexto   = context;
        this._idLayout   = idLayout;
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
            TextView tvLocationPosition  = convertView.findViewById(R.id.tvLocationListPosition);
            TextView tvLocationCode      = convertView.findViewById(R.id.tvLocationListLocationCode);
            TextView tvLocationAddress      = convertView.findViewById(R.id.tvLocationListLocationAddress);
            //TextView tvLocationLatitude  = convertView.findViewById(R.id.tvLocationListLocationLatitude);
            //TextView tvLocationLongitude = convertView.findViewById(R.id.tvLocationListLocationLongitude);

            String locationCode = result.getLocation();
            Double latitude     = result.getCoordinates().getLatitude();
            Double longitude    = result.getCoordinates().getLongitude();

            Geocoder geocoder = new Geocoder(_contexto, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String locationAddress = addresses.get(0).getAddressLine(0);

            Log.i(LOG_TAG, locationAddress);

            tvLocationPosition.setText(Integer.toString(position + 1));
            tvLocationCode.setText(locationCode);
            tvLocationAddress.setText(locationAddress);
            //tvLocationCode.setText(locationCode);
            //tvLocationLatitude.setText(Double.toString(latitude));
            //tvLocationLongitude.setText(Double.toString(longitude));
        }
        return convertView;
    }
}
