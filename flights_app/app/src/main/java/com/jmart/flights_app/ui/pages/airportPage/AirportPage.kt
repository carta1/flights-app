package com.jmart.flights_app.ui.pages.airportPage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jmart.flights_app.R
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.ui.pages.airportPage.airportDetails.customHighlightTextView

const val SCHIPHOL_AIRPORT_ID = "AMS"
const val KILOMETER = "Kilometers"
const val MILES = "Miles"


@ExperimentalFoundationApi
@Composable
fun AirportPage(navController: NavHostController) {
    val airportViewModel = hiltViewModel<AirportViewModel>()

    airportViewModel.getAllAirports()
    val airports by airportViewModel.airPorts.observeAsState()

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
    ) {
        Text(
            text = stringResource(R.string.airport_page_title),
            style = MaterialTheme.typography.h1
        )
        AirportsLister(airports ?: listOf<Airport>())
    }
}

@Composable
fun AirportsLister(
    airports: List<Airport>,
) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .wrapContentSize(
                Alignment.TopStart
            )

    )
    {
        items(airports) {
            airportRow(
                it.name, it.distanceToAmsAsString, modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(all = 8.dp)

            )
        }
    }
}

@Composable
fun airportRow(airportName: String, airportDistance: String, modifier: Modifier) {
    Column {
        customHighlightTextView(
            HighlightText = stringResource(R.string.airport_page_airport),
            nonHighLightText = airportName,
            modifier
        )
        customHighlightTextView(
            HighlightText = stringResource(R.string.airport_page_distance),
            nonHighLightText = airportDistance,
            modifier
        )
        Divider(
            color = colorResource(R.color.lightGray),
            modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
        )
    }
}