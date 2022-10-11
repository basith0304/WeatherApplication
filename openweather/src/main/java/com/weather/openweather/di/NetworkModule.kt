package com.weather.openweather.di

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.weather.openweather.BuildConfig
import com.weather.openweather.common.DispatcherProvider
import com.weather.openweather.data.remote.WeatherApi
import com.weather.openweather.data.repository.MainRepository
import com.weather.openweather.data.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Named("HttpCache")
    lateinit var cache: Cache

    @Singleton
    @Provides
    fun provideWeatherApi(@Named("okHttpClient") okHttpClient: OkHttpClient?): WeatherApi = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(
            Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()))
        .client(okHttpClient)
        .build()
        .create(WeatherApi::class.java)


    @Provides
    @Singleton
    @Named("HttpCache")
    fun provideHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        cache = Cache(application.cacheDir, cacheSize)
        return cache
    }

    @Singleton
    @Provides
    @Named("okHttpClient")
    fun provideOkhttpClient(
        @Named("HttpCache") cache: Cache?): OkHttpClient? {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.cache(cache)
        okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClient.readTimeout(60, TimeUnit.SECONDS)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideMainRepository(api: WeatherApi): MainRepository = NetworkRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}