package es.upm.miw.airquality;

import android.util.Log;

import static es.upm.miw.airquality.MainActivity.LOG_TAG;

public class AQIParameter {

    final static int CO_GOOD = 400;
    final static int CO_MODERATE = 800;
    final static int NO2_GOOD = 50;
    final static int NO2_MODERATE = 100;
    final static int PM25_GOOD = 15;
    final static int PM25_MODERATE = 30;
    final static int PM10_GOOD = 25;
    final static int PM10_MODERATE = 50;
    final static int O3_GOOD = 65;
    final static int O3_MODERATE = 120;
    final static int SO2_GOOD = 50;
    final static int SO2_MODERATE = 120;

    final static int AQI_GOOD = 0;
    final static int AQI_MODERATE = 1;
    final static int AQI_UNHEALTHY = 2;

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

    public int compareParameter() {
        int AQILevel = 100;
        if (parameterName.equals("bc")) {
            Log.i(LOG_TAG, "Parameter is: Black Carbon");

        } else if (parameterName.equals("co")) {
            Log.i(LOG_TAG, "Parameter is: Carbon Monoxide");
            if (value < CO_GOOD) {
                AQILevel = AQI_GOOD;
            } else if (value >= CO_GOOD && value < CO_MODERATE) {
                AQILevel = AQI_MODERATE;
            } else {
                AQILevel = AQI_UNHEALTHY;
            }

        } else if (parameterName.equals("no2")) {
            Log.i(LOG_TAG, "Parameter is: Nitrogen Dioxide");
            if (value < NO2_GOOD) {
                AQILevel = AQI_GOOD;
            } else if (value >= NO2_GOOD && value < NO2_MODERATE) {
                AQILevel = AQI_MODERATE;
            } else {
                AQILevel = AQI_UNHEALTHY;
            }

        } else if (parameterName.equals("pm25")) {
            Log.i(LOG_TAG, "Parameter is: Suspended particulates smaller than 2.5 μm");
            if (value < PM25_GOOD) {
                AQILevel = AQI_GOOD;
            } else if (value >= PM25_GOOD && value < PM25_MODERATE) {
                AQILevel = AQI_MODERATE;
            } else {
                AQILevel = AQI_UNHEALTHY;
            }

        } else if (parameterName.equals("pm10")) {
            Log.i(LOG_TAG, "Parameter is: Suspended particulates smaller than 10 μm");
            if (value < PM10_GOOD) {
                AQILevel = AQI_GOOD;
            } else if (value >= PM10_GOOD && value < PM10_MODERATE) {
                AQILevel = AQI_MODERATE;
            } else {
                AQILevel = AQI_UNHEALTHY;
            }

        } else if (parameterName.equals("o3")) {
            Log.i(LOG_TAG, "Parameter is: Ozone");
            if (value < O3_GOOD) {
                AQILevel = AQI_GOOD;
            } else if (value >= O3_GOOD && value < O3_MODERATE) {
                AQILevel = AQI_MODERATE;
            } else {
                AQILevel = AQI_UNHEALTHY;
            }

        } else if (parameterName.equals("so2")) {
            Log.i(LOG_TAG, "Parameter is: Sulfur Dioxide");
            if (value < SO2_GOOD) {
                AQILevel = AQI_GOOD;
            } else if (value >= SO2_GOOD && value < SO2_MODERATE) {
                AQILevel = AQI_MODERATE;
            } else {
                AQILevel = AQI_UNHEALTHY;
            }

        }
        return AQILevel;
    }

}
