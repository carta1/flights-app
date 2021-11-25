package com.jmart.flights_app.data.models


import com.jmart.flights_app.other.extensions.toStringWithDistance
import com.jmart.flights_app.ui.pages.airportPage.KILOMETER
import com.squareup.moshi.Json

data class Airport(
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
    val countryId: String = "",
    var distanceToAms: Double = 0.0,
){
    val distanceToAmsAsString get () = distanceToAms.toStringWithDistance(KILOMETER)
}