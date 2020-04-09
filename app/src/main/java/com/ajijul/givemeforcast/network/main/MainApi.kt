package com.ajijul.givemeforcast.network.main

import com.ajijul.givemeforcast.models.forecast.ForecastBaseModel
import com.ajijul.givemeforcast.models.weather.WeatherBaseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {

    @GET("weather")
    suspend fun getWeatherOfParticularCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): WeatherBaseModel

    @GET("forecast")
    suspend fun getForecastOfMyCurrentLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String
    ): ForecastBaseModel

}