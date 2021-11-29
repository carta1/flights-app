package com.jmart.flights_app.data.models

import com.squareup.moshi.Json

data class AirportToAirportDistances(
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val name: String = "",
    val city: String = "",
    val countryId: String = "",
    var toAirportName: String = "",
    var toAirportId: String = "",
    var toAirportCity: String = "",
    var distanceToInUnit: Double = 0.0,
)

