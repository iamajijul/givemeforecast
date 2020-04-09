package com.ajijul.givemeforcast.ui.main.weather

import com.ajijul.givemeforcast.models.weather.WeatherBaseModel
import com.ajijul.givemeforcast.network.main.MainApi
import com.ajijul.givemeforcast.utils.base.BaseRepository
import com.ajijul.givemeforcast.utils.base.ResultWrapper
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(var mainApi: MainApi) : BaseRepository(),
    WeatherRepository {


    override suspend fun getWeatherOfParticularCity(
        cityName: String,
        apiKey: String
    ): ResultWrapper<WeatherBaseModel>? {
        return safeApiCall { mainApi.getWeatherOfParticularCity(cityName, apiKey) }
    }


}