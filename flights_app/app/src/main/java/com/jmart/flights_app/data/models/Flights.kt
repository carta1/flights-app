package com.jmart.flights_app.data.models


import com.squareup.moshi.Json

data class Flights(
    @Json(name = "airlineId")
    val airlineId: String = "",
    @Json(name = "flightNumber")
    val flightNumber: Int = 0,
    @Json(name = "departureAirportId")
    val departureAirportId: String = "",
    @Json(name = "arrivalAirportId")
    val arrivalAirportId: String = ""
)