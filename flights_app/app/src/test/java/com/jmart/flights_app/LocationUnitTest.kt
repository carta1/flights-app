package com.jmart.flights_app

import android.location.Location
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.models.AirportToAirportDistances
import com.jmart.flights_app.data.models.Flight
import com.jmart.flights_app.other.utils.DistanceUtils
import com.jmart.flights_app.ui.pages.airportPage.SCHIPHOL_AIRPORT_ID
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
    private val airportList = listOf<Airport>(
        Airport(id = "ABZ", latitude = 57.200253, longitude = -2.204186, name = "Dyce Airport", city = "Aberdeen", countryId = "GB"),
        Airport(id = "ACC", latitude = 5.60737, longitude = -0.171769, name = "Kotoka Airport", city = "Accra", countryId = "GH"),
        Airport(id = "AGP", latitude = 36.675182, longitude = -4.489616, name = "Malaga Airport", city = "Malaga", countryId = "ES"),
        Airport(id = "BLL", latitude = 55.747383, longitude = 9.147874, name = "Billund Airport", city = "Billund", countryId = "DK"),
        Airport(id = "ABQ", latitude = 35.049625, longitude = -106.617195, name = "Albuquerque International Airport", city = "Albuquerque", countryId = "US"),
        Airport(id = "AMS", latitude = 52.30907, longitude = 4.763385, name = "Amsterdam-Schiphol Airport", city = "Amsterdam", countryId = "NL"))

    private val flightList = listOf<Flight>(
        Flight(airlineId = "30", flightNumber = 2128, departureAirportId = "AMS", arrivalAirportId = "TNG"),
        Flight(airlineId = "AF", flightNumber = 1141, departureAirportId = "AMS", arrivalAirportId = "CDG"),
        Flight(airlineId = "BA", flightNumber = 2765, departureAirportId = "AMS", arrivalAirportId = "LGW"),
        Flight(airlineId = "BE", flightNumber = 102, departureAirportId = "AMS", arrivalAirportId = "BHX"),
        Flight(airlineId = "CJ", flightNumber = 8452, departureAirportId = "AMS", arrivalAirportId = "LCY")
    )

    @Test
    fun getClosestAiportAndClosestDistance() {
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
    }


    @Test
    fun setFurthestAirportsExtraDate() {

        val totalAirportsList = listOf<Airport>(
            Airport(id = "ABZ", latitude = 57.200253, longitude = -2.204186, name = "Dyce Airport", city = "Aberdeen", countryId = "GB"),
            Airport(id = "ACC", latitude = 5.60737, longitude = -0.171769, name = "Kotoka Airport", city = "Accra", countryId = "GH"),
            Airport(id = "AGP", latitude = 36.675182, longitude = -4.489616, name = "Malaga Airport", city = "Malaga", countryId = "ES"),
            Airport(id = "BLL", latitude = 55.747383, longitude = 9.147874, name = "Billund Airport", city = "Billund", countryId = "DK"),
            Airport(id = "ABQ", latitude = 35.049625, longitude = -106.617195, name = "Albuquerque International Airport", city = "Albuquerque", countryId = "US"),)

        val furthestAirportList = listOf<AirportToAirportDistances>(
            AirportToAirportDistances(id= "ACC", latitude= 5.60737, longitude= -0.171769, name= "Kotoka Airport",
                city= "Accra", countryId= "GH", toAirportName= "Albuquerque International Airport" ,
                toAirportId= "ABQ", toAirportCity= "Albuquerque", distanceToInUnit= 11136.408203125),
            AirportToAirportDistances(id= "ABQ", latitude= 35.049625, longitude= -106.617195,
                name= "Albuquerque International Airport", city= "Albuquerque", countryId= "US",
                toAirportName= "Kotoka Airport", toAirportId= "ACC", toAirportCity= "Accra", distanceToInUnit=11136.408203125),
        )

        furthestAirportList.forEach { airport ->
            totalAirportsList.find {
                it.id == airport.id
            }?.isThisAirportTheFurthest = true
        }

        // if true the airport is one of the two furthest from each other
        // Kotoka Airport
        org.junit.Assert.assertEquals( true, totalAirportsList[1].isThisAirportTheFurthest)
        // Albuquerque International Airport
        org.junit.Assert.assertEquals( true, totalAirportsList[4].isThisAirportTheFurthest)
        // Malaga Airport
        org.junit.Assert.assertEquals( false, totalAirportsList[2].isThisAirportTheFurthest)
        // Billund Airport
        org.junit.Assert.assertEquals( false, totalAirportsList[3].isThisAirportTheFurthest)
        // Dyce Airport
        org.junit.Assert.assertEquals( false, totalAirportsList[0].isThisAirportTheFurthest)
    }

    @Test
    fun getCloserAirportToAms() {
        val schipholDetails = airportList.firstOrNull { it.id == SCHIPHOL_AIRPORT_ID }
        val schipholLocation: Location = Location("").apply {
            latitude = schipholDetails?.latitude ?: 0.0
            longitude = schipholDetails?.longitude ?: 0.0
        }

        val airportsIdList = flightList.map { it.arrivalAirportId }.toSet()

        airportList.filter { airportsIdList.contains(it.id) }

        for (location in airportList) {
            val loc = Location("").apply {
                latitude = location.latitude
                longitude = location.longitude
            }
            location.distanceToAms =
                DistanceUtils.convertFloatToDoubleInKm(schipholLocation.distanceTo(loc))
            location.distanceToAmsAsString =
                DistanceUtils.getUserDistanceUnit(schipholLocation.distanceTo(loc), "")
        }

        val sortedAirportsByAscDistance = listOf<Airport>(
            Airport(id= "AMS", latitude=52.30907, longitude=4.763385, name= "Amsterdam-Schiphol Airport"
                , city= "Amsterdam", countryId= "NL", distanceToAms= 0.0, distanceToAmsAsString="0.00 km",
                isThisAirportTheFurthest=false),
            Airport(id="BLL", latitude=55.747383, longitude=9.147874, name="Billund Airport",
                city="Billund", countryId="DK", distanceToAms=478.35418701171875, distanceToAmsAsString="478.35 km",
                isThisAirportTheFurthest=false),
            Airport(id="ABZ", latitude=57.200253, longitude=-2.204186, name="Dyce Airport", city="Aberdeen",
                countryId="GB", distanceToAms=704.753662109375, distanceToAmsAsString="704.75 km",
                isThisAirportTheFurthest=false),
            Airport(id="AGP", latitude=36.675182, longitude=-4.489616, name="Malaga Airport", city="Malaga",
                countryId="ES", distanceToAms=1883.089599609375, distanceToAmsAsString="1883.09 km",
                isThisAirportTheFurthest=false),
            Airport(id="ACC", latitude=5.60737, longitude=-0.171769, name="Kotoka Airport", city="Accra",
                countryId="GH", distanceToAms=5197.53515625, distanceToAmsAsString="5197.54 km",
                isThisAirportTheFurthest=false),
            Airport(id="ABQ", latitude=35.049625, longitude=-106.617195, name="Albuquerque International Airport",
                city="Albuquerque", countryId="US", distanceToAms=8272.9892578125, distanceToAmsAsString="8272.99 km",
                isThisAirportTheFurthest=false))

        val sortedAirportsList = airportList.sortedBy { it.distanceToAms }

        org.junit.Assert.assertEquals(sortedAirportsByAscDistance, sortedAirportsList)
    }
}