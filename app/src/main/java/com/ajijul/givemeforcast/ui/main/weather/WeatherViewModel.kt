package com.ajijul.givemeforcast.ui.main.weather

import android.util.Log
import androidx.lifecycle.*
import com.ajijul.givemeforcast.models.weather.WeatherBaseModel
import com.ajijul.givemeforcast.utils.base.BaseViewModel
import com.ajijul.givemeforcast.utils.base.ResultWrapper
import com.ajijul.givemeforcast.utils.base.ScreenState
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(var weatherRepo: WeatherRepoImpl) :
    BaseViewModel() {

    private var weather = MutableLiveData<ResultWrapper<WeatherBaseModel>>()
    val TAG = "Weather ViewModel"


    init {
        Log.d(TAG, "Weather ViewModel")
    }

    fun observeWeather(
        cityName: String,
        apiKey: String
    ): LiveData<ResultWrapper<WeatherBaseModel>> {

        if (weather.value != null && weather.value is ResultWrapper.Success<WeatherBaseModel>
            && (weather.value as ResultWrapper.Success<WeatherBaseModel>).value.name == cityName
        )
            return weather

        viewModelScope.launch {
            screenState.value = ScreenState.LOADING
            val result = weatherRepo.getWeatherOfParticularCity(cityName, apiKey)
            weather.postValue(result)
            val newState = if (result == null) ScreenState.ERROR else ScreenState.RENDER
            screenState.postValue(newState)

        }

        return weather
    }

    fun observeScreenState(): LiveData<ScreenState> {
        return screenState
    }


}