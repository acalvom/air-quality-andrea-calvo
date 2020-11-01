package es.upm.miw.airquality;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.airquality.models.Measurement;

public class LocationDetailAdapter extends ArrayAdapter {

    private Context _contexto;
    private int _idLayout;
    private List<Measurement> _measurements;

    public LocationDetailAdapter(Context context, int idLayout, List<Measurement> measurements) {
        super(context, idLayout, measurements);
        this._contexto = context;
        this._idLayout = idLayout;
        this._measurements = measurements;
        setNotifyOnChange(true);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) _contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this._idLayout, null);
        }

        Measurement measurement = _measurements.get(position);
        if (measurement != null) {

            TextView tvDetailPosition = convertView.findViewById(R.id.tvDetailPositionLocationDetail);
            TextView tvDetailParameter = convertView.findViewById(R.id.tvDetailParameterLocationDetail);
            TextView tvDetailValue = convertView.findViewById(R.id.tvDetailValueLocationDetail);
            TextView tvDetailUnit = convertView.findViewById(R.id.tvDetailUnitLocationDetail);
            TextView tvDetailLastUpdate = convertView.findViewById(R.id.tvDetailLastUpdateLocationDetail);
            ImageView ivDetailAQI = convertView.findViewById(R.id.ivDetailAQILocationDetail);

            String parameter = measurement.getParameter();
            Double value = measurement.getValue();
            String unit = measurement.getUnit();
            String lastUpdate = measurement.getLastUpdated();

            tvDetailPosition.setText(Integer.toString(position + 1));
            tvDetailParameter.setText(parameter.toUpperCase());
            tvDetailValue.setText(Double.toString(value));
            tvDetailUnit.setText(unit);

            String regexTarget = "\\b:00.00.000Z\\b";
            tvDetailLastUpdate.setText(lastUpdate.replaceAll(regexTarget, "h"));

            AQIParameter aqiParameter = new AQIParameter(parameter, value);
            int AQILevel = aqiParameter.compareParameter();

            if (AQILevel == 0) {
                // AQI is good
                ivDetailAQI.setImageResource(R.mipmap.good);
            } else if (AQILevel == 1) {
                // AQI is moderate
                ivDetailAQI.setImageResource(R.mipmap.moderate);
            } else if (AQILevel == 2) {
                // AQI is unhealthy
                ivDetailAQI.setImageResource(R.mipmap.unhealthy);
            }
        }
        return convertView;
    }
}

