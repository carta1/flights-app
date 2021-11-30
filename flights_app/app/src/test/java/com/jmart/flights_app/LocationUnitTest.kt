package com.jmart.flights_app

import android.location.Location
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.models.AirportToAirportDistances
import com.jmart.flights_app.other.utils.DistanceUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*


/** This test class is set with Robolectric in order to access static classes like Location which
 * can not be mocked by moquito, so those functions dealing with distance to airport will be tested
 * here
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class LocationUnitTest {
    val airportList = listOf<Airport>(
        Airport(id = "ABZ", latitude = 57.200253, longitude = -2.204186, name = "Dyce Airport", city = "Aberdeen", countryId = "GB"),
        Airport(id = "ACC", latitude = 5.60737, longitude = -0.171769, name = "Kotoka Airport", city = "Accra", countryId = "GH"),
        Airport(id = "AGP", latitude = 36.675182, longitude = -4.489616, name = "Malaga Airport", city = "Malaga", countryId = "ES"),
        Airport(id = "BLL", latitude = 55.747383, longitude = 9.147874, name = "Billund Airport", city = "Billund", countryId = "DK"),
        Airport(id = "ABQ", latitude = 35.049625, longitude = -106.617195, name = "Albuquerque International Airport", city = "Albuquerque", countryId = "US"),)

    @Test
    fun getClosestAiportAndCLosestDistance() {
        // location of Billund Airport in DK
        val currentAirPortLocation = Location("").apply {
            latitude =  57.08655
            longitude = 9.872241
        }

        val distanceInMetersList = mutableListOf<Float>()

            for (location in airportList) {
                val loc = Location("").apply {
                    latitude = location.latitude
                    longitude = location.longitude
                }
                distanceInMetersList.add(currentAirPortLocation.distanceTo(loc))
            }

        val closestAirportIndex = distanceInMetersList.indexOf(Collections.min(distanceInMetersList))
        val closestAirport = airportList.get(closestAirportIndex)

        val billundAirport = Airport(id = "BLL", latitude = 55.747383, longitude = 9.147874, name = "Billund Airport", city = "Billund", countryId = "DK")
        // test the closest airport in the list from the location set
        org.junit.Assert.assertEquals(billundAirport, closestAirport)

        val closestAirportDistance = DistanceUtils.getUserDistanceUnit(distanceInMetersList[closestAirportIndex], "")
        val distanceToCLosesAirport = "155.67 km"
        // test the closest airport distance in the list from the location set
        org.junit.Assert.assertEquals(distanceToCLosesAirport, closestAirportDistance)
    }

    @Test
    fun getFurtherAirportFromEachOtherTest() {
        val listOfDistances = mutableListOf<AirportToAirportDistances>()
            for (airportX in airportList) {
                for (airportY in airportList) {
                    val aiportXLat = airportX.latitude
                    val aiportXLong = airportX.longitude
                    val aiportYLat = airportY.latitude
                    val aiportYLong = airportY.longitude

                    val locX = Location("locationX").apply {
                        latitude = aiportXLat
                        longitude = aiportXLong
                    }

                    val locY = Location("locationY").apply {
                        latitude = aiportYLat
                        longitude = aiportYLong
                    }

                    val distance = locX.distanceTo(locY)

                    val airportToAirportDistances = AirportToAirportDistances(
                        id = airportX.id,
                        latitude = airportX.latitude,
                        longitude = airportX.longitude,
                        name = airportX.name,
                        city = airportX.city,
                        countryId = airportX.countryId,
                        toAirportCity = airportY.city,
                        toAirportId = airportY.id,
                        distanceToInUnit = DistanceUtils.convertFloatToDoubleInKm(distance),
                        toAirportName = airportY.name
                    )

                    listOfDistances.add(airportToAirportDistances)
                }
            }


        val furthestAirports = listOfDistances.sortedByDescending { it.distanceToInUnit }.take(2)
        val mAirportList = listOf<AirportToAirportDistances>(
            AirportToAirportDistances(id= "ACC", latitude= 5.60737, longitude= -0.171769, name= "Kotoka Airport",
                city= "Accra", countryId= "GH", toAirportName= "Albuquerque International Airport" ,
                toAirportId= "ABQ", toAirportCity= "Albuquerque", distanceToInUnit= 11136.408203125),
            AirportToAirportDistances(id= "ABQ", latitude= 35.049625, longitude= -106.617195,
                name= "Albuquerque International Airport", city= "Albuquerque", countryId= "US",
                toAirportName= "Kotoka Airport", toAirportId= "ACC", toAirportCity= "Accra", distanceToInUnit=11136.408203125)
        )

        org.junit.Assert.assertEquals(mAirportList, furthestAirports)



//        setFurthestAirportsExtraDate(furthestAirports, airportList )
    }


    @Test
    fun setFurthestAirportsExtraDate() {
        val mAirportList = listOf<AirportToAirportDistances>(
            AirportToAirportDistances(id= "ACC", latitude= 5.60737, longitude= -0.171769, name= "Kotoka Airport",
                city= "Accra", countryId= "GH", toAirportName= "Albuquerque International Airport" ,
                toAirportId= "ABQ", toAirportCity= "Albuquerque", distanceToInUnit= 11136.408203125),
            AirportToAirportDistances(id= "ABQ", latitude= 35.049625, longitude= -106.617195,
                name= "Albuquerque International Airport", city= "Albuquerque", countryId= "US",
                toAirportName= "Kotoka Airport", toAirportId= "ACC", toAirportCity= "Accra", distanceToInUnit=11136.408203125),
        )

        mAirportList.forEach { airport ->
            airportList.find {
                it.id == airport.id
            }?.isThisAirportTheFurthest = true
        }

        val testAirport = Airport(id = "ACC", latitude = 5.60737, longitude = -0.171769,
            name = "Kotoka Airport", city = "Accra", countryId = "GH")
        val testAirport2 =Airport(id = "ABQ", latitude = 35.049625, longitude = -106.617195,
            name = "Albuquerque International Airport", city = "Albuquerque", countryId = "US")

        org.junit.Assert.assertEquals( true, testAirport.isThisAirportTheFurthest)
        org.junit.Assert.assertEquals( true, testAirport2.isThisAirportTheFurthest)
    }

////    private fun setFurthestAirportsExtraDate(furthestAirports: List<AirportToAirportDistances>, airports: List<Airport>?) {
//        furthestAirports.forEach { airport ->
//            airports?.find {
//                it.id == airport.id
//            }?.isThisAirportTheFurthest = true
//        }
////    }

}