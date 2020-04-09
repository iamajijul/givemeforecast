package com.ajijul.givemeforcast.di

import com.ajijul.givemeforcast.di.main.MainFragmentBuildersModule
import com.ajijul.givemeforcast.di.main.MainModule
import com.ajijul.givemeforcast.di.main.MainScope
import com.ajijul.givemeforcast.di.main.MainViewModelsModule
import com.ajijul.givemeforcast.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [MainFragmentBuildersModule::class,
            MainViewModelsModule::class, MainModule::class]
    )
    abstract fun connectMainActivity(): MainActivity

}