package com.jmart.flights_app.ui.pages.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmart.flights_app.data.useCases.GetAirPorts
import com.jmart.flights_app.data.useCases.GetFlights
import com.jmart.flights_app.data.useCases.GetUserDistanceUnit
import com.jmart.flights_app.data.useCases.SetUserDistanceUnit
import com.jmart.flights_app.ui.pages.airportPage.KILOMETER
import com.jmart.flights_app.ui.pages.airportPage.MILES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor (
    private val setUserDistanceUnit: SetUserDistanceUnit,
    private val getUserDistanceUnit: GetUserDistanceUnit
): ViewModel() {

    private val _userDistanceUnit = MutableLiveData<String>()
    val userDistanceUnit: LiveData<String> = _userDistanceUnit

    init {
        getDistanceUnit()
    }


    private fun getDistanceUnit() {
        viewModelScope.launch {
            getUserDistanceUnit.invoke().collect { unit ->
                _userDistanceUnit.postValue(unit)
            }
        }
    }


    fun setMilesDistanceUnit() {
        viewModelScope.launch{
            setUserDistanceUnit(MILES)
            Timber.e("miles set")
        }
    }

    fun setKilometerDistanceUnit() {
        viewModelScope.launch{
            setUserDistanceUnit(KILOMETER)
            Timber.e("kilometers set")
        }
    }
}