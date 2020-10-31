package es.upm.miw.airquality;

import android.util.Log;

import static es.upm.miw.airquality.MainActivity.LOG_TAG;

public class AQIParameter {

    private String parameterName;
    private Double value;

    public AQIParameter(String parameterName, Double value) {
        this.parameterName = parameterName;
        this.value = value;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int compareParameter(){
        int AQILevel = 100;
        if (parameterName.equals("bc")){
            Log.i(LOG_TAG, "Parameter is: Black Carbon");

        }else if(parameterName.equals("co")){
            Log.i(LOG_TAG, "Parameter is: Carbon Monoxide");
            if(value < 400){
                AQILevel = 0;
            }else if (value >= 400 && value < 800){
                AQILevel = 1;
            }else{
                AQILevel = 2;
            }

        }else if(parameterName.equals("no2")){
            Log.i(LOG_TAG, "Parameter is: Nitrogen Dioxide");
            if(value < 50){
                AQILevel = 0;
            }else if (value >= 50 && value < 100){
                AQILevel = 1;
            }else{
                AQILevel = 2;
            }

        }else if(parameterName.equals("pm25")){
            Log.i(LOG_TAG, "Parameter is: Suspended particulates smaller than 2.5 μm");
            if(value < 15){
                AQILevel = 0;
            }else if (value >= 15 && value < 30){
                AQILevel = 1;
            }else{
                AQILevel = 2;
            }

        }else if(parameterName.equals("pm10")){
            Log.i(LOG_TAG, "Parameter is: Suspended particulates smaller than 10 μm");
            if(value < 25){
                AQILevel = 0;

            }else if (value >= 25 && value < 50){
                AQILevel = 1;
            }else{
                AQILevel = 2;
            }

        }else if(parameterName.equals("o3")){
            Log.i(LOG_TAG, "Parameter is: Ozone");
            if(value < 65){
                AQILevel = 0;
            }else if (value >= 65 && value < 120){
                AQILevel = 1;
            }else{
                AQILevel = 2;
            }

        }else if(parameterName.equals("so2")){
            Log.i(LOG_TAG, "Parameter is: Sulfur Dioxide");
            if(value < 50){
                AQILevel = 0;
            }else if (value >= 50 && value < 120){
                AQILevel = 1;
            }else{
                AQILevel = 2;
            }

        }
        return AQILevel;
    }

}
