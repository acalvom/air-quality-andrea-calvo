package es.upm.miw.airquality;

import es.upm.miw.airquality.models.Cities;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("Unused")
interface ICityRESTAPIService {


    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    // https://api.openaq.org/v1/cities?country=ES&limit=200
    @GET("/v1/cities?country=ES&limit=200")
    Call<Cities> getAllCities();

    // https://api.openaq.org/v1/latest?country=ES&limit=2000&order_by=city
    //@GET("/v1/latest?country=ES&limit=2000&order_by=city")
    //Call<Cities> getAllCities();

    // https://api.openaq.org/v1/locations?city=Madrid
    @GET("/v1/locations")
    Call<Cities> getAllLocationsFromCity(@Query("city") String cityName);

    // https://api.openaq.org/v1/latest?location=ES1901A
    @GET("/v1/latest")
    Call<Cities> getAllMeasurementsFromCity(@Query("location") String locationCode);
}
