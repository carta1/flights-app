package com.jmart.flights_app.other.di.annotation

import com.jmart.flights_app.other.enums.BuildFlavorEnum
import dagger.MapKey

@MapKey
annotation class OkHttpClientMapKey(val value: BuildFlavorEnum)
