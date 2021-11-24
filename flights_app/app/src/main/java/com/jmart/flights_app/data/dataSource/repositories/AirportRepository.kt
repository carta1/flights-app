package com.jmart.flights_app.data.dataSource.repositories

import com.jmart.flights_app.data.dataSource.remote.RemoteAirportDataSource
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.models.Flights
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AirportRepository(
    private val remoteAirportDataSource: RemoteAirportDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getFlights(): List<Flights>? {
        return withContext(ioDispatcher) {
            remoteAirportDataSource.getFlights()
        }
    }

    suspend fun getAirports(): List<Airport>? {
        return withContext(ioDispatcher) {
            remoteAirportDataSource.getAirports()
        }
    }
}