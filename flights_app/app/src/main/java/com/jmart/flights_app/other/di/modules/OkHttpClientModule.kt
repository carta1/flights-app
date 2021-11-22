package com.jmart.flights_app.other.di.modules

import com.jmart.flights_app.data.network.HttpClient
import com.jmart.flights_app.other.di.annotation.NormalOkHttpClientQualifier
import com.jmart.flights_app.other.di.annotation.OkHttpClientMapKey
import com.jmart.flights_app.other.di.annotation.UnsafeOkHttpClientQualifier
import com.jmart.flights_app.other.enums.BuildFlavorEnum
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OkHttpClientModule {
    companion object {
        @Provides
        @Singleton
        @NormalOkHttpClientQualifier
        fun normalHttpClient(): OkHttpClient {
            return HttpClient.getNormalClient().build()
        }

        @Provides
        @Singleton
        @UnsafeOkHttpClientQualifier
        fun unsafeHttpClient(): OkHttpClient {
            return HttpClient.getUnsafeOkHttpClient().build()
        }

    }
    /**
     * map the UnsafeOkHttpClient to the 'tst' flavor
     * @see [HttpClient.getUnsafeOkHttpClient]
     *
     * use the [BuildFlavorEnum] of the flavor as the key
     */
    @Binds
    @Singleton
    @IntoMap
    @OkHttpClientMapKey(BuildFlavorEnum.TST)
    abstract fun tstOkHttpClient(@UnsafeOkHttpClientQualifier okHttpClient: OkHttpClient): OkHttpClient

    /**
     * map the NormalOkHttpClient to the 'accp' flavor
     * @see [HttpClient.getNormalClient]
     *
     * use the [BuildFlavorEnum] of the flavor as the key
     */
    @Binds
    @Singleton
    @IntoMap
    @OkHttpClientMapKey(BuildFlavorEnum.ACCP)
    abstract fun accpOkHttpClient(@NormalOkHttpClientQualifier okHttpClient: OkHttpClient): OkHttpClient

    /**
     * map the NormalOkHttpClient to the 'prod' flavor
     * @see [HttpClient.getNormalClient]
     *
     * use the [BuildFlavorEnum] of the flavor as the key
     */
    @Binds
    @Singleton
    @IntoMap
    @OkHttpClientMapKey(BuildFlavorEnum.PROD)
    abstract fun prodOkHttpClient(@NormalOkHttpClientQualifier okHttpClient: OkHttpClient): OkHttpClient
}