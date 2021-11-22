package com.jmart.flights_app.data.dataSource.api

import com.jmart.flights_app.data.models.Airports
import com.jmart.flights_app.data.models.Flights
import retrofit2.Response
import retrofit2.http.GET

interface AirportApi {
    @GET("https://flightassets.datasavannah.com/test/airports.json")
    suspend fun getAirports(
    ): Response<List<Airports>>

    @GET("https://flightassets.datasavannah.com/test/flights.json")
    suspend fun getFlights(
    ): Response<List<Flights>>
}