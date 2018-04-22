package com.borge.wipro_challenge.remote;

import com.borge.wipro_challenge.model.WeatherResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SOService {

    @GET("/data/2.5/forecast")
    Call<WeatherResults> getResults(@Query("zip") String zip,
                                    @Query("units") String units,
                                    @Query("appid") String appid);
}
