package com.jmart.flights_app.ui.pages.airportPage.airportDetails

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.useCases.GetAirPorts
import com.jmart.flights_app.data.useCases.GetUserDistanceUnit
import com.jmart.flights_app.other.utils.DistanceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AirportDetailViewModel @Inject constructor(
    private val getAirports: GetAirPorts,
    private val getUserDistanceUnit: GetUserDistanceUnit
) : ViewModel() {

    private val _airPortDetails = MutableLiveData<Airport?>()
    val airPortDetails: LiveData<Airport?> = _airPortDetails

    private val _airPorts = MutableLiveData<List<Airport>?>()
    val airPorts: LiveData<List<Airport>?> = _airPorts

    private val _closestAirport = MutableLiveData<Airport?>()
    val closestAirport: LiveData<Airport?> = _closestAirport

    private val _closestAirportDistance = MutableLiveData<String>()
    val closestAirportDistance: LiveData<String> = _closestAirportDistance

    fun getAirportDetails(airportName: String) {
        viewModelScope.launch {
            val result = getAirports.invoke()
            if (!result.isNullOrEmpty()) {
                _airPorts.value = result
            }


            val details = getAirportInfo(airportName, result)
            _airPortDetails.postValue(details)

            // convert the airports coordinates to a Location class to facilitate calculations
            val loc = Location("location").apply {
                latitude = details?.latitude ?: 0.00
                longitude = details?.longitude ?: 0.0
            }

            getUserDistanceUnit.invoke().collect { unit ->
                getClosestAirport(loc, result?.filter { it.name != airportName }, unit)
            }
        }
    }

    // filters the current airport from the list of airports
    fun getAirportInfo(airportName: String, airPortList: List<Airport>?): Airport? {
        return airPortList?.find { airport -> airport.toString().contains(airportName) }
    }

    // gets closest airport details and the distance from the current airport in view
    fun getClosestAirport(
        currentAirPortLocation: Location,
        airportList: List<Airport>?,
        userUnits: String?
    ) {
        val distanceInMetersList = mutableListOf<Float>()

        // creates a list of the airports distance from the selected airport in display
        if (airportList != null) {
            for (location in airportList) {
                val loc = Location("").apply {
                    latitude = location.latitude
                    longitude = location.longitude
                }
                distanceInMetersList.add(currentAirPortLocation.distanceTo(loc))
            }
        }

        // gets the min distance from the list and saves the index
        val closestAirportIndex =
            distanceInMetersList.indexOf(Collections.min(distanceInMetersList))
        // get the closest airport details according to the index
        val closestAirport = airportList?.get(closestAirportIndex)
        // get the closest airport distance with it's unit
        val closestAirportDistance =
            DistanceUtils.getUserDistanceUnit(distanceInMetersList[closestAirportIndex], userUnits)

        _closestAirport.postValue(closestAirport)
        _closestAirportDistance.value = closestAirportDistance
    }

}