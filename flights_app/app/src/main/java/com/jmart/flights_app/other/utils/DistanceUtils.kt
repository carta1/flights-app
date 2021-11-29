package com.jmart.flights_app.other.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jmart.flights_app.R
import com.jmart.flights_app.ui.pages.airportPage.KILOMETER
import com.jmart.flights_app.ui.pages.airportPage.METERS_IN_KILOMETER
import com.jmart.flights_app.ui.pages.airportPage.METERS_IN_MILE
import com.jmart.flights_app.ui.pages.airportPage.MILES


object DistanceUtils {
    fun convertMeterToKilometer(meter: Float): String {
        // converts it to a string to show two decimal places
        // and round automatically
        val distanceInKilometers = (meter / METERS_IN_KILOMETER).toDouble()
        val distanceAsString = String.format("%.2f", distanceInKilometers)
        return "$distanceAsString $KILOMETER"

    }

    private fun convertMeterToMiles(meter: Float): String {
        val distanceInMiles = (meter / METERS_IN_MILE)
        val distanceAsString = String.format("%.2f", distanceInMiles)
        return "$distanceAsString $MILES"
    }

    fun getUserDistanceUnit(meter: Float, userUnits: String?): String {
        return if (userUnits == MILES) {
            convertMeterToMiles(meter)
        } else {
            convertMeterToKilometer(meter)
        }
    }

    fun convertFloatToDoubleInKm(meter: Float) : Double {
        return (meter / METERS_IN_KILOMETER).toDouble()
    }
}