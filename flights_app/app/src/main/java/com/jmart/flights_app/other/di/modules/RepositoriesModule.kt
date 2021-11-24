package com.jmart.flights_app.other.di.modules

import com.jmart.flights_app.data.dataSource.remote.RemoteAirportDataSource
import com.jmart.flights_app.data.dataSource.repositories.AirportRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun airPortRepository(
        remoteAuthAirportDataSource: RemoteAirportDataSource): AirportRepository {
        return AirportRepository(remoteAuthAirportDataSource)
    }
}