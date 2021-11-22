package com.jmart.flights_app.data.models


import com.squareup.moshi.Json

data class Airports(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "latitude")
    val latitude: Double = 0.0,
    @Json(name = "longitude")
    val longitude: Double = 0.0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "city")
    val city: String = "",
    @Json(name = "countryId")
    val countryId: String = ""
)