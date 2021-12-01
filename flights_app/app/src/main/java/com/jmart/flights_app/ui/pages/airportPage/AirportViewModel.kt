package com.jmart.flights_app.ui.pages.airportPage

import android.content.Context
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmart.flights_app.R
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.models.Flight
import com.jmart.flights_app.data.useCases.GetAirPorts
import com.jmart.flights_app.data.useCases.GetFlights
import com.jmart.flights_app.data.useCases.GetUserDistanceUnit
import com.jmart.flights_app.other.utils.DistanceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportViewModel  @Inject constructor (
    private val getAirports: GetAirPorts,
    private val getFlights: GetFlights,
    private val getUserDistanceUnit: GetUserDistanceUnit
): ViewModel() {
    private val _airPorts = MutableLiveData<List<Airport>?>()
    val airPorts: LiveData<List<Airport>?> = _airPorts


    fun getAllAirports() {
        viewModelScope.launch{
            val airportsResult = getAirports.invoke()
            val flightsResult = getFlights.invoke()

            getUserDistanceUnit.invoke().collect { unit ->
                getClosestAirport(airportsResult, flightsResult, unit)
            }
        }
    }

    private fun getClosestAirport(airportList: List<Airport>?, flightList: List<Flight>?, userUnit: String){
        val schipholDetails = airportList?.firstOrNull { it.id == SCHIPHOL_AIRPORT_ID }
        val schipholLocation: Location = Location("").apply{
            latitude = schipholDetails?.latitude?: 0.0
            longitude = schipholDetails?.longitude?: 0.0
        }

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
                location.distanceToAms = DistanceUtils.convertFloatToDoubleInKm(schipholLocation.distanceTo(loc))
                location.distanceToAmsAsString = DistanceUtils.getUserDistanceUnit(schipholLocation.distanceTo(loc), userUnit)
            }
        }
        val sortedAirportsList = airportList?.sortedBy { it.distanceToAms }
        _airPorts.postValue(sortedAirportsList?.filter { it.distanceToAms != 0.0})
    }
}