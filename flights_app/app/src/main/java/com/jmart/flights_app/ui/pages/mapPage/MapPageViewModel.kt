package com.jmart.flights_app.ui.pages.mapPage

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.models.AirportToAirportDistances
import com.jmart.flights_app.data.useCases.GetAirPorts
import com.jmart.flights_app.other.utils.DistanceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapPageViewModel @Inject constructor(
    private val getAirports: GetAirPorts,
) : ViewModel() {

    private val _airPorts = MutableLiveData<List<Airport>?>()
    val airPorts: LiveData<List<Airport>?> = _airPorts

    init {
        getAllAirports()
    }

    private fun getAllAirports() {
        viewModelScope.launch {
            val result = getAirports.invoke()
            getFurthestAirportsFromEachOtherOther(result)
            _airPorts.postValue(result)

        }
    }

    private fun getFurthestAirportsFromEachOtherOther(mAirports: List<Airport>?) {
        val listOfDistances = mutableListOf<AirportToAirportDistances>()
        if (mAirports != null) {
            for (airportX in mAirports) {
                for (airportY in mAirports) {
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
        }
        val furthestAirports = listOfDistances.sortedByDescending { it.distanceToInUnit }.take(2)

        setFurthestAirportsExtraDate(furthestAirports, mAirports )

    }

    private fun setFurthestAirportsExtraDate(furthestAirports: List<AirportToAirportDistances>, airports: List<Airport>?) {
        furthestAirports.forEach { airport ->
            airports?.find {
                it.id == airport.id
            }?.isThisAirportTheFurthest = true
        }
    }
}
