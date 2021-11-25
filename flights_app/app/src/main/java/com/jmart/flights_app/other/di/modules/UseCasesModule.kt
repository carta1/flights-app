package com.jmart.flights_app.other.di.modules

import com.jmart.flights_app.data.dataSource.repositories.AirportRepository
import com.jmart.flights_app.data.useCases.GetAirPorts
import com.jmart.flights_app.data.useCases.GetFlights
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
}