package es.upm.miw.airquality;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.airquality.models.Measurement;

import static es.upm.miw.airquality.MainActivity.LOG_TAG;

public class LocationDetailAdapter extends ArrayAdapter {

    private Context _contexto;
    private int _idLayout;
    private List<Measurement> _measurements;

    public LocationDetailAdapter(Context context, int idLayout, List<Measurement> measurements) {
        super(context, idLayout, measurements);
        this._contexto     = context;
        this._idLayout     = idLayout;
        this._measurements = measurements;
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
        Measurement measurement = _measurements.get(position);

        if (measurement != null) {

            TextView tvDetailPosition   = convertView.findViewById(R.id.tvDetailPositionLocationDetail);
            TextView tvDetailParameter  = convertView.findViewById(R.id.tvDetailParameterLocationDetail);
            TextView tvDetailValue      = convertView.findViewById(R.id.tvDetailValueLocationDetail);
            TextView tvDetailUnit       = convertView.findViewById(R.id.tvDetailUnitLocationDetail);
            TextView tvDetailLastUpdate = convertView.findViewById(R.id.tvDetailLastUpdateLocationDetail);
            ImageView ivDetailAQI       = convertView.findViewById(R.id.ivDetailAQILocationDetail);

            String parameter  = measurement.getParameter();
            Double value      = measurement.getValue();
            String unit       = measurement.getUnit();
            String lastUpdate = measurement.getLastUpdated();

            tvDetailPosition.setText(Integer.toString(position));
            tvDetailParameter.setText(parameter.toUpperCase());
            tvDetailValue.setText(Double.toString( value));
            tvDetailUnit.setText(unit);

            String regexTarget = "\\b:00.00.000Z\\b";
            tvDetailLastUpdate.setText(lastUpdate.replaceAll(regexTarget, "h"));

            if (parameter.equals("bc")){
                Log.i(LOG_TAG, "Parameter is: Black Carbon");

            }else if(parameter.equals("co")){
                Log.i(LOG_TAG, "Parameter is: Carbon Monoxide");
                if(value < 400){
                    // AQI is good
                    ivDetailAQI.setImageResource(R.mipmap.good);
                }else if (value >= 400 && value < 800){
                    // AQI is moderate
                    ivDetailAQI.setImageResource(R.mipmap.moderate);
                }else{
                    // AQI is unhealthy
                    ivDetailAQI.setImageResource(R.mipmap.unhealthy);
                }

            }else if(parameter.equals("no2")){
                Log.i(LOG_TAG, "Parameter is: Nitrogen Dioxide");
                if(value < 50){
                    // AQI is good
                    ivDetailAQI.setImageResource(R.mipmap.good);

                }else if (value >= 50 && value < 100){
                    // AQI is moderate
                    ivDetailAQI.setImageResource(R.mipmap.moderate);

                }else{
                    // AQI is unhealthy
                    ivDetailAQI.setImageResource(R.mipmap.unhealthy);
                }

            }else if(parameter.equals("pm25")){
                Log.i(LOG_TAG, "Parameter is: Suspended particulates smaller than 2.5 μm");
                if(value < 15){
                    // AQI is good
                    ivDetailAQI.setImageResource(R.mipmap.good);

                }else if (value >= 15 && value < 30){
                    // AQI is moderate
                    ivDetailAQI.setImageResource(R.mipmap.moderate);

                }else{
                    // AQI is unhealthy
                    ivDetailAQI.setImageResource(R.mipmap.unhealthy);
                }

            }else if(parameter.equals("pm10")){
                Log.i(LOG_TAG, "Parameter is: Suspended particulates smaller than 10 μm");
                if(value < 25){
                    // AQI is good
                    ivDetailAQI.setImageResource(R.mipmap.good);

                }else if (value >= 25 && value < 50){
                    // AQI is moderate
                    ivDetailAQI.setImageResource(R.mipmap.moderate);

                }else{
                    // AQI is unhealthy
                    ivDetailAQI.setImageResource(R.mipmap.unhealthy);
                }

            }else if(parameter.equals("o3")){
                Log.i(LOG_TAG, "Parameter is: Ozone");
                if(value < 65){
                    // AQI is good
                    ivDetailAQI.setImageResource(R.mipmap.good);

                }else if (value >= 65 && value < 120){
                    // AQI is moderate
                    ivDetailAQI.setImageResource(R.mipmap.moderate);

                }else{
                    // AQI is unhealthy
                    ivDetailAQI.setImageResource(R.mipmap.unhealthy);
                }

            }else if(parameter.equals("so2")){
                Log.i(LOG_TAG, "Parameter is: Sulfur Dioxide");
                if(value < 50){
                    // AQI is good
                    ivDetailAQI.setImageResource(R.mipmap.good);

                }else if (value >= 50 && value < 120){
                    // AQI is moderate
                    ivDetailAQI.setImageResource(R.mipmap.moderate);

                }else{
                    // AQI is unhealthy
                    ivDetailAQI.setImageResource(R.mipmap.unhealthy);
                }

            }



            /*
            //List<Measurement> measurementes = result.getMeasurements();

            int numberMeasurements = measurement.getMeasurements().size();
            for (int i = 0; i < numberMeasurements; i++){
                String parameter = result.getMeasurements().get(i).getParameter();
                Double value = result.getMeasurements().get(i).getValue();
                String unit = result.getMeasurements().get(i).getUnit();
                String lastUpdate = result.getMeasurements().get(i).getLastUpdated();

                //tvDetailPosition.append(Integer.toString(i + 1)+ "\n\n");
                //tvDetailParameter.append(parameter+ "\n\n");
                //tvDetailValue.append((Double.toString( value))+ "\n\n");
                //tvDetailUnit.append(unit+ "\n\n");
                //tvDetailLastUpdate.append(lastUpdate + "\n\n" );

                tvDetailPosition.setText(Integer.toString(i));
                tvDetailParameter.setText(parameter);
                tvDetailValue.setText(Double.toString( value));
                tvDetailUnit.setText(unit);
                tvDetailLastUpdate.setText(lastUpdate);
            }
*/
        }
        return convertView;
    }
}

