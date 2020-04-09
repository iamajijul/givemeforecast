package com.ajijul.givemeforcast.di

import com.ajijul.givemeforcast.utils.Constants
import com.ajijul.givemeforcast.utils.base.MessageHandlerImp
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule {

    @Module
    companion object {



        @JvmStatic
        @Provides
        @Singleton
        fun provideMessageHandler(): MessageHandlerImp = MessageHandlerImp()

    }

}