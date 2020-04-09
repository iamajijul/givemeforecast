package com.ajijul.givemeforcast.ui.main.forecast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import com.ajijul.givemeforcast.models.forecast.ForecastBaseModel
import com.ajijul.givemeforcast.models.forecast.ThreeHoursModel
import com.ajijul.givemeforcast.utils.base.BaseViewModel
import com.ajijul.givemeforcast.utils.base.ResultWrapper
import com.ajijul.givemeforcast.utils.base.ScreenState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ForecastViewModel @Inject constructor(var forecastRepo: ForecastRepoImpl) :
    BaseViewModel() {

    private  var groups = MutableLiveData<Map<String, List<ThreeHoursModel>>>()
    private var forecast = MutableLiveData<ResultWrapper<ForecastBaseModel>>()
    val TAG = "Forecast ViewModel"
    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var outFormat = SimpleDateFormat("EEE", Locale.getDefault())


    init {
        Log.d(TAG, "Forecast ViewModel")
    }

    fun observeForecast(
        lat: String,
        lon: String,
        apiKey: String
    ): LiveData<ResultWrapper<ForecastBaseModel>> {

        viewModelScope.launch {
            screenState.value = ScreenState.LOADING
            val result = forecastRepo.getForecastOfMyCurrentLocation(lat, lon, apiKey)
            forecast.postValue(result)
            val newState = if (result == null) ScreenState.ERROR else {
                startGrouping()
                ScreenState.RENDER
            }
            screenState.postValue(newState)

        }

        return forecast
    }

    private fun startGrouping() {
        if (forecast.value !is ResultWrapper.Success<ForecastBaseModel>)
            return
        (forecast.value as ResultWrapper.Success<ForecastBaseModel>).let {
            groups.postValue(it.value.list.groupBy { item ->
                val date = format.parse(item.dt_txt)
                outFormat.format(date?:return);
            })
        }
    }

    fun observeScreenState(): LiveData<ScreenState> {
        return screenState
    }


    fun observeGroupData(): LiveData<Map<String, List<ThreeHoursModel>>> {
        return groups
    }

    fun getForecastResult():LiveData<ResultWrapper<ForecastBaseModel>>{
        return forecast
    }

}

