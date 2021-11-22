package com.jmart.flights_app.ui.pages.airportPage

import androidx.lifecycle.ViewModel
import com.jmart.flights_app.data.useCases.GetAirPorts
import javax.inject.Inject

class AirportPageViewModel  @Inject constructor (
    private val getAirports: GetAirPorts,
): ViewModel()