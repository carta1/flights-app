package com.jmart.flights_app

import com.jmart.flights_app.other.utils.DistanceUtils
import com.jmart.flights_app.ui.pages.airportPage.KILOMETER
import com.jmart.flights_app.ui.pages.airportPage.MILES
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DistanceUtilTest {
    private lateinit var mDistanceUtils: DistanceUtils

    @Before
    fun setup() {
        mDistanceUtils = DistanceUtils
    }

    @Test
    fun itConvertsMeterToKilometer() {
        val floatDistance: Float = 90192.97F
        val response = mDistanceUtils.convertMeterToKilometerToString(floatDistance)
        Assert.assertEquals("90.19 km", response)
    }

    @Test
    fun itConvertsMeterToMiles() {
        val floatDistance: Float = 90192.97F
        val response = mDistanceUtils.convertMeterToMilesToString(floatDistance)
        Assert.assertEquals("56.04 mi", response)
    }

    @Test
    fun itConvertsToDistanceStringUsingUserUnitKm() {
        val floatDistance: Float = 90192.97F
        val response = mDistanceUtils.getUserDistanceUnit(floatDistance, KILOMETER)
        Assert.assertEquals("90.19 km", response)
    }

    @Test
    fun itConvertsToDistanceStringUsingUserUnitMl() {
        val floatDistance: Float = 90192.97F
        val response = mDistanceUtils.getUserDistanceUnit(floatDistance, MILES)
        Assert.assertEquals("56.04 mi", response)
    }

    @Test
    fun itConvertsToDistanceStringUsingUserUnitNullOrEmpty() {
        val floatDistance: Float = 90192.97F
        val response = mDistanceUtils.getUserDistanceUnit(floatDistance, "")
        Assert.assertEquals("90.19 km", response)
    }

    @Test
    fun itConvertFloatToDoubleInKm() {
        val floatDistance: Float = 1758.4742431640625F
        val response = mDistanceUtils.convertFloatToDoubleInKm(floatDistance)
        Assert.assertEquals(1.7584742307662964, response, 1.7584742307662964)
    }
}