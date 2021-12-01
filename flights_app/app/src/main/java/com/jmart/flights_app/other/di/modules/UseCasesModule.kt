package com.jmart.flights_app.other.di.modules

import com.jmart.flights_app.data.dataSource.repositories.AirportRepository
import com.jmart.flights_app.data.useCases.GetAirPorts
import com.jmart.flights_app.data.useCases.GetFlights
import com.jmart.flights_app.data.useCases.GetUserDistanceUnit
import com.jmart.flights_app.data.useCases.SetUserDistanceUnit
import com.jmart.flights_app.other.preferences.AppPrefsStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun getAirPorts(
        airportRepository: AirportRepository
    ): GetAirPorts {
        return GetAirPorts(airportRepository)
    }

    @ViewModelScoped
    @Provides
    fun getFlights(
        airportRepository: AirportRepository
    ): GetFlights {
        return GetFlights(airportRepository)
    }

    @ViewModelScoped
    @Provides
    fun getUserDistanceUnit(
        appPrefsStorage: AppPrefsStorage
    ): GetUserDistanceUnit{
        return GetUserDistanceUnit(appPrefsStorage)
    }

    @ViewModelScoped
    @Provides
    fun setUserDistanceUnit(
        appPrefsStorage: AppPrefsStorage
    ): SetUserDistanceUnit {
        return SetUserDistanceUnit(appPrefsStorage)
    }
}