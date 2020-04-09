package com.ajijul.givemeforcast.di

import android.app.Application
import android.content.Context
import android.os.Build
import com.ajijul.givemeforcast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideInterceptor(): HttpLoggingInterceptor {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideCache(cacheFile: File): Cache {
            return Cache(cacheFile, 10 * 1000 * 1000) //10MB Cahe
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideCacheFile(context: Application): File {
            return File(context.cacheDir, "okhttp_cache")
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            cache: Cache
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
                .build()
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}