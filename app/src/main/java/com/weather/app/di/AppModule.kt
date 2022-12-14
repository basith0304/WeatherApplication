package com.weather.app.di
import android.content.Context
import androidx.appcompat.app.AlertDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.Executor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    @Named("Context")
    fun provideContext(@ApplicationContext context: Context):Context {
        return context
    }

    //
}