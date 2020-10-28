package es.upm.miw.airquality;

import es.upm.miw.airquality.models.Cities;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@SuppressWarnings("Unused")
interface ICityRESTAPIService {


    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    // https://api.openaq.org/v1/latest?country=ES&limit=2000&order_by=city
    @GET("/v1/latest?country=ES&limit=2000&order_by=city")
    Call<Cities> getAllAirQuality();

    // https://api.openaq.org/v1/latest?city=Madrid
    @GET("/v1/latest?city={cityName}")
    Call<Cities> getAirQualityByLocation(@Path("cityName") String cityName);
}
