package com.jmart.flights_app.other.di.modules

import android.annotation.SuppressLint
import com.jmart.flights_app.BuildConfig
import com.jmart.flights_app.other.enums.BuildFlavorEnum
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.*
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun moshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @SuppressLint("DefaultLocale")
    @Provides
    @Singleton
    fun retrofit(
        moshi: Moshi,
        providersMap: Map<BuildFlavorEnum, @JvmSuppressWildcards Provider<OkHttpClient>>
    ): Retrofit {

        val flavorName = BuildConfig.FLAVOR.uppercase(Locale.getDefault())
        val okHttpClient = providersMap[BuildFlavorEnum.valueOf(flavorName)]?.get()
            ?: throw RuntimeException("Wrong flavor name: $flavorName")

        Timber.d("Initializing Retrofit for: $flavorName" )

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }


}