package com.ajijul.givemeforcast.ui.main.forecast

import com.ajijul.givemeforcast.models.forecast.ForecastBaseModel
import com.ajijul.givemeforcast.utils.base.ResultWrapper

interface ForecastRepository {
    suspend fun getForecastOfMyCurrentLocation(lat: String,lon : String, apiKey: String)
            : ResultWrapper<ForecastBaseModel>?
}