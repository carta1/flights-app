package com.jmart.flights_app.ui.pages.airportPage

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.models.Flight
import com.jmart.flights_app.data.useCases.GetAirPorts
import com.jmart.flights_app.data.useCases.GetFlights
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AirportViewModel  @Inject constructor (
    private val getAirports: GetAirPorts,
    private val getFlights: GetFlights,
): ViewModel() {

    private val _airPorts = MutableLiveData<List<Airport>?>()
    val airPorts: LiveData<List<Airport>?> = _airPorts

    fun getAllAirports() {
        viewModelScope.launch{
            val airportsResult = getAirports.invoke()
            val flightsResult = getFlights.invoke()

            getClosestAirport(airportsResult, flightsResult )
        }
    }

    private fun getClosestAirport(airportList: List<Airport>?, flightList: List<Flight>?){
        val schipholDetails = airportList?.firstOrNull { it.id == SCHIPHOL_AIRPORT_ID }
        val schipholLocation: Location = Location("").apply{
            latitude = schipholDetails?.latitude?: 0.0
            longitude = schipholDetails?.longitude?: 0.0
        }
/* 1) get the flights list and and make a map with the airport ids
   2) compare the airport codes with the airport list and make a new list of airports
   3) find the distances of the airports
   4) sort the list of airports in ascending order according to airport distance from ams
*/

        val airportsIdList = flightList?.map { it.arrivalAirportId }?.toSet()

        if (airportsIdList != null) {
            airportList?.
            filter { airportsIdList.contains(it.id)}
        }
        if (airportList != null) {
            for (location in airportList) {
                val loc = Location("").apply{
                    latitude = location.latitude
                    longitude = location.longitude
                }
                location.distanceToAms = convertMeterToKilometer(schipholLocation.distanceTo(loc)).toDouble()
            }
        }
        val sortedAirportsList = airportList?.sortedBy { it.distanceToAms }
        _airPorts.postValue(sortedAirportsList?.filter { it.distanceToAms != 0.0})
    }

    private fun convertMeterToKilometer(meter: Float): Float {
        return (meter * 0.001).toFloat()
    }

}