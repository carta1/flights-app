package com.jmart.flights_app.data.dataSource.remote

import com.jmart.flights_app.data.dataSource.api.AirportApi
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.models.Flight
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteAirportDataSource @Inject constructor(private val airportApi: AirportApi) {
    suspend fun getAirports(): List<Airport>? {
        return try {
            val response = airportApi.getAirports()
            if (response.isSuccessful) {
                response.body()
            } else {
                Timber.e("ResponseCode: ${response.code()}\nResponse: ${response.raw()}")
                null
            }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    suspend fun getFlights(): List<Flight>? {
        return try {
            val response = airportApi.getFlights()
            if (response.isSuccessful) {
                response.body()
            } else {
                Timber.e("ResponseCode: ${response.code()}\nResponse: ${response.raw()}")
                null
            }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}