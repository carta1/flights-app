package com.jmart.flights_app.ui.pages.airportPage.airportDetails

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.useCases.GetAirPorts
import com.jmart.flights_app.ui.pages.airportPage.KILOMETER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AirportDetailViewModel @Inject constructor (
    private val getAirports: GetAirPorts,
): ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _airPortDetails = MutableLiveData<Airport?>()
    val airPortDetails: LiveData<Airport?> = _airPortDetails

    private val _airPorts = MutableLiveData<List<Airport>?>()
    val airPorts: LiveData<List<Airport>?> = _airPorts

    private val _closestAirport = MutableLiveData<Airport?>()
    val closestAirport: LiveData<Airport?> = _closestAirport

    private val _closestAirportDistance = MutableLiveData<String>()
    val closestAirportDistance: LiveData<String> = _closestAirportDistance

    fun getAirportDetails(airportName: String) {
        viewModelScope.launch{
            val result = getAirports.invoke()
            _airPorts.postValue(result)

            val details = getAirportInfo(airportName, result)
            _airPortDetails.postValue(details)

            // convert the airports coordinates to a Location class to facilitate calculations
            val loc = Location("location").apply{
                latitude = details?.latitude?: 0.00
                longitude = details?.longitude?: 0.0
            }

            getClosestAirport(loc, result?.filter { it.name != airportName} )
        }
    }

    // filters the current airport from the list of airports
    private fun getAirportInfo(airportName: String, airPortList: List<Airport>?): Airport? {
        return airPortList?.find { airport -> airport.toString().contains(airportName) }
    }

    private fun getClosestAirport(currentAirPortLocation: Location, airportList: List<Airport>?){
        val distanceInMetersList = mutableListOf<Float>()

        if (airportList != null) {
            for (location in airportList) {
                val loc = Location("").apply{
                    latitude = location.latitude
                    longitude = location.longitude
                }
                distanceInMetersList.add(currentAirPortLocation.distanceTo(loc))
            }
        }

        val closestAirportIndex = distanceInMetersList.indexOf(Collections.min(distanceInMetersList))
        val closestAirport = airportList?.get(closestAirportIndex)
        val closestAirportDistance = convertMeterToKilometer(distanceInMetersList[closestAirportIndex]).toDouble()

        _closestAirport.postValue(closestAirport)

        // post the distance value to the live data and converts it to a string to show two decimal places
        // and round automatically
        val distanceAsString = String.format("%.2f", closestAirportDistance)
        _closestAirportDistance.postValue("$distanceAsString $KILOMETER")
    }

    private fun convertMeterToKilometer(meter: Float): Float {
        return (meter * 0.001).toFloat()
    }

}