package com.jmart.flights_app.other.extensions

fun Double.toStringWithDistance(distanceType: String): String {
    val distanceAsString = String.format("%.2f", this)
    return "$distanceAsString $distanceType"
}