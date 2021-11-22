package com.jmart.flights_app.data.dataSource.remote

import com.jmart.flights_app.data.dataSource.api.AirportApi
import com.jmart.flights_app.data.models.Airports
import com.jmart.flights_app.data.models.Flights
import timber.log.Timber
import javax.inject.Inject

class RemoteAirportDataSource @Inject constructor(private val airportApi: AirportApi) {
    suspend fun getAirports(): List<Airports>? {
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

    suspend fun getFlights(): List<Flights>? {
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