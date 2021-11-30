package com.jmart.flights_app

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jmart.flights_app.data.useCases.GetAirPorts
import com.jmart.flights_app.data.useCases.GetUserDistanceUnit
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.ui.pages.airportPage.airportDetails.AirportDetailViewModel
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AirportDetailViewModelTest {
    private val getAirports: GetAirPorts = mock()
    private val getUserDistanceUnit: GetUserDistanceUnit = mock()
    private lateinit var mViewModel: AirportDetailViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val airportList = listOf<Airport>(
        Airport(id = "AAL", latitude = 57.08655, longitude = 9.872241, name = "Aalborg Airport", city = "Aalborg", countryId = "DK"),
        Airport(id = "ABQ", latitude = 35.049625, longitude = -106.617195, name = "Albuquerque International Airport", city = "Albuquerque", countryId = "US"),
        Airport(id = "ABZ", latitude = 57.200253, longitude = -2.204186, name = "Dyce Airport", city = "Aberdeen", countryId = "GB"),
        Airport(id = "ACC", latitude = 5.60737, longitude = -0.171769, name = "Kotoka Airport", city = "Accra", countryId = "GH"),
        Airport(id = "AGP", latitude = 36.675182, longitude = -4.489616, name = "Malaga Airport", city = "Malaga", countryId = "ES"),
    )


    @Before
    fun setup() {
        mViewModel = AirportDetailViewModel(getAirports, getUserDistanceUnit)
    }

    @Test
    fun getAirportByName() {
        val malagaAirport = Airport(id = "AGP", latitude = 36.675182, longitude = -4.489616, name = "Malaga Airport", city = "Malaga", countryId = "ES")
        val response = mViewModel.getAirportInfo("Malaga Airport", airportList )
        org.junit.Assert.assertEquals(malagaAirport, response)
    }
}
