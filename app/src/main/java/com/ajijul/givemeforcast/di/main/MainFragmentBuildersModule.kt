package com.ajijul.givemeforcast.di.main

import com.ajijul.givemeforcast.ui.main.forecast.ForecastContainerFragment
import com.ajijul.givemeforcast.ui.main.forecast.ForecastPerDayReportFragment
import com.ajijul.givemeforcast.ui.main.weather.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun connectForecastFragment(): ForecastContainerFragment

    @ContributesAndroidInjector
    abstract fun connectPerDayFragmentFragment(): ForecastPerDayReportFragment

    @ContributesAndroidInjector
    abstract fun connectWeatherFragment(): WeatherFragment
}