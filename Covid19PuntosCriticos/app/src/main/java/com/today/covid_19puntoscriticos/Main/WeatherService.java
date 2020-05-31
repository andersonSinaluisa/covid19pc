package com.today.covid_19puntoscriticos.Main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// 20200531165923
// assets?lon=-95.33&lat=29.78&date=2018-01-01&&dim=0.10&api_key=DEMO_KEY

public interface WeatherService {
    @GET("assets")
    Call<Earth> getEarth(@Query("lon") String longitud, @Query("lat") String latitud, @Query("date") String date,@Query("dim") String dimencion,@Query("api_key") String api_key);
}
