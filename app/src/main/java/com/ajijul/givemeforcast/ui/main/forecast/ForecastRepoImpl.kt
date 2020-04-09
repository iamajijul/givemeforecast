package com.ajijul.givemeforcast.ui.main.forecast

import com.ajijul.givemeforcast.models.forecast.ForecastBaseModel
import com.ajijul.givemeforcast.models.weather.WeatherBaseModel
import com.ajijul.givemeforcast.network.main.MainApi
import com.ajijul.givemeforcast.utils.base.BaseRepository
import com.ajijul.givemeforcast.utils.base.ResultWrapper
import javax.inject.Inject

class ForecastRepoImpl @Inject constructor(var mainApi: MainApi) : BaseRepository(),
    ForecastRepository {



    override suspend fun getForecastOfMyCurrentLocation(
        lat: String,
        lon: String,
        apiKey: String
    ): ResultWrapper<ForecastBaseModel>? {
        return safeApiCall { mainApi.getForecastOfMyCurrentLocation(lat,lon, apiKey) }
    }


}