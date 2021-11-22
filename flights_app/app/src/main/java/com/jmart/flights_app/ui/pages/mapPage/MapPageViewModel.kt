package com.jmart.flights_app.ui.pages.mapPage

import androidx.lifecycle.ViewModel
import com.jmart.flights_app.data.useCases.GetAirPorts
import javax.inject.Inject

class MapPageViewModel  @Inject constructor (
    private val getAirports: GetAirPorts,
): ViewModel()