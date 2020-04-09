package com.ajijul.givemeforcast.di.main

import androidx.lifecycle.ViewModel
import com.ajijul.givemeforcast.di.ViewModelKey
import com.ajijul.givemeforcast.ui.main.weather.WeatherViewModel
import com.ajijul.givemeforcast.ui.main.forecast.ForecastViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel::class)
    abstract fun bindRepoViewModel(repoListViewModel: ForecastViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindRepoDetailsViewModel(repoDetailsViewModel: WeatherViewModel):ViewModel


}