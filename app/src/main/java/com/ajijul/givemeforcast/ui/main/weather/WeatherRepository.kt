package com.ajijul.givemeforcast.ui.main.weather

import com.ajijul.givemeforcast.models.weather.WeatherBaseModel
import com.ajijul.givemeforcast.utils.base.ResultWrapper

interface WeatherRepository {
    suspend fun getWeatherOfParticularCity(cityName: String, apiKey: String):
            ResultWrapper<WeatherBaseModel>?
}