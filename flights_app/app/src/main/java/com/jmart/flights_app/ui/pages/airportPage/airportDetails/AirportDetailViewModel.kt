package com.jmart.flights_app.ui.pages.airportPage.airportDetails

import android.location.Location
import androidx.lifecycle.*
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.useCases.GetAirPorts
import com.jmart.flights_app.data.useCases.GetUserDistanceUnit
import com.jmart.flights_app.data.useCases.SetUserDistanceUnit
import com.jmart.flights_app.ui.pages.airportPage.KILOMETER
import com.jmart.flights_app.ui.pages.airportPage.METERS_IN_KILOMETER
import com.jmart.flights_app.ui.pages.airportPage.METERS_IN_MILE
import com.jmart.flights_app.ui.pages.airportPage.MILES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.contracts.Returns

@HiltViewModel
class AirportDetailViewModel @Inject constructor (
    private val getAirports: GetAirPorts,
    private val getUserDistanceUnit: GetUserDistanceUnit
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

            getUserDistanceUnit.invoke().collect { unit ->
                getClosestAirport(loc, result?.filter { it.name != airportName}, unit)
            }



        }
    }

    // filters the current airport from the list of airports
    private fun getAirportInfo(airportName: String, airPortList: List<Airport>?): Airport? {
        return airPortList?.find { airport -> airport.toString().contains(airportName) }
    }

    private fun getClosestAirport(currentAirPortLocation: Location, airportList: List<Airport>?, userUnits: String?){
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
        val closestAirportDistance = getUserDistanceUnit(distanceInMetersList[closestAirportIndex], userUnits)

        _closestAirport.postValue(closestAirport)



        _closestAirportDistance.postValue(closestAirportDistance)
    }

    private fun convertMeterToKilometer(meter: Float): String {
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

    private fun getUserDistanceUnit(meter: Float, userUnits: String?): String {
        return if (userUnits == MILES) {
            convertMeterToMiles(meter)
        } else {
            convertMeterToKilometer(meter)
        }
    }

}