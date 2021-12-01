package com.jmart.flights_app.other.utils

import com.jmart.flights_app.ui.pages.airportPage.KILOMETER
import com.jmart.flights_app.ui.pages.airportPage.METERS_IN_KILOMETER
import com.jmart.flights_app.ui.pages.airportPage.METERS_IN_MILE
import com.jmart.flights_app.ui.pages.airportPage.MILES
import timber.log.Timber


object DistanceUtils {
    fun convertMeterToKilometerToString(meter: Float): String {
        // converts it to a string to show two decimal places
        // and round automatically
        val distanceInKilometers = (meter / METERS_IN_KILOMETER).toDouble()
        val distanceAsString = String.format("%.2f", distanceInKilometers)
        return "$distanceAsString $KILOMETER"

    }

    fun convertMeterToMilesToString(meter: Float): String {
        val distanceInMiles = (meter / METERS_IN_MILE)
        val distanceAsString = String.format("%.2f", distanceInMiles)
        return "$distanceAsString $MILES"
    }

    fun getUserDistanceUnit(meter: Float, userUnits: String?): String {
        return if (userUnits == MILES) {
            convertMeterToMilesToString(meter)
        } else {
            convertMeterToKilometerToString(meter)
        }
    }

    fun convertFloatToDoubleInKm(meter: Float) : Double {
        return (meter / METERS_IN_KILOMETER).toDouble()
    }
}