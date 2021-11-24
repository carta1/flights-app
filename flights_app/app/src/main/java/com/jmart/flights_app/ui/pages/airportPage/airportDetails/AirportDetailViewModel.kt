package com.jmart.flights_app.ui.pages.airportPage.airportDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.data.useCases.GetAirPorts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun getAirportDetails(airportName: String) {
        viewModelScope.launch{
            val result = getAirports.invoke()
            _airPorts.postValue(result)

            val details = getAirportInfo(airportName, result)
            _airPortDetails.postValue(details)
        }
    }

    private fun getAirportInfo(airportName: String, airPortList: List<Airport>?): Airport? {
        return airPortList?.find { airport -> airport.toString().contains(airportName) }
    }
}