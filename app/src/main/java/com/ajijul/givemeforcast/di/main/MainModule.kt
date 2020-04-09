package com.ajijul.givemeforcast.di.main

import com.ajijul.givemeforcast.network.main.MainApi
import com.ajijul.givemeforcast.ui.main.forecast.ForecastRepoImpl
import com.ajijul.givemeforcast.ui.main.weather.WeatherRepoImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class MainModule {

    @MainScope
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }


    @MainScope
    @Provides
    fun provideWeatherRepository(mainApi: MainApi): WeatherRepoImpl {
        return WeatherRepoImpl(mainApi)
    }


    @MainScope
    @Provides
    fun provideForecastRepository(mainApi: MainApi): ForecastRepoImpl {
        return ForecastRepoImpl(mainApi)
    }
}