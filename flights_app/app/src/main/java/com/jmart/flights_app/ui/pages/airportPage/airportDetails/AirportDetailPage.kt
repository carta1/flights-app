package com.jmart.flights_app.ui.pages.airportPage.airportDetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jmart.flights_app.R
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.ui.customComponents.customHighlightTextView

@ExperimentalFoundationApi
@Composable
fun AirportDetailsPage(navController: NavHostController, airportName: String?, pressOnBack: () -> Unit = {}) {
    val airportDetailsViewModel = hiltViewModel<AirportDetailViewModel>()

    // Runs only on initial composition
    LaunchedEffect(key1 = Unit) {
        // replaces the * for / because they are part of the original airport name
        airportDetailsViewModel.getAirportDetails(airportName?.replace("*", "/") ?: "")
    }
    val mAirPortDetails by airportDetailsViewModel.airPortDetails.observeAsState()
    val mClosestAirportDistance by airportDetailsViewModel.closestAirportDistance.observeAsState()
    val mClosestAirport by airportDetailsViewModel.closestAirport.observeAsState()

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
            .fillMaxWidth()

    ) {
        ConstraintLayoutContent(
            mAirPortDetails,
            mClosestAirport,
            mClosestAirportDistance,
            pressOnBack
        )
    }
}

@Composable
fun ConstraintLayoutContent(
    details: Airport?,
    nearestAirPort: Airport?,
    nearestAirportDistance: String?,
    pressOnBack: () -> Unit
) {
    ConstraintLayout {
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()

        // Create references for the composables to constrain
        val (titleText, idHeaderText, latitudeHeaderText, longitudeHeaderText, nameHeaderText,
            cityHeaderText, countryIdHeaderText, nearestAirportHeaderText,
            nearestAirportDistanceHeaderText, arrowIcon) = createRefs()

        Icon(
            imageVector = Icons.Filled.ArrowBack,
            tint = Color.Black,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(arrowIcon) {
                    top.linkTo(parent.top)
                }
                .padding(12.dp)
                .clickable(onClick = { pressOnBack() })
        )

        Text(
            text = stringResource(R.string.airport_details_title),
            style = MaterialTheme.typography.h5,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(titleText) {
                start.linkTo(arrowIcon.end, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            }
        )

        Modifier.constrainAs(idHeaderText) {
            top.linkTo(titleText.bottom, margin = 32.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customHighlightTextView(
                stringResource(R.string.airport_details_id),
                "${details?.id}",
                it
            )
        }

        Modifier.constrainAs(latitudeHeaderText) {
            top.linkTo(idHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customHighlightTextView(
                stringResource(R.string.airport_details_latitude),
                "${details?.latitude}",
                it
            )
        }

        Modifier.constrainAs(longitudeHeaderText) {
            top.linkTo(latitudeHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customHighlightTextView(
                stringResource(R.string.airport_details_longitude),
                "${details?.longitude}",
                it
            )
        }

        Modifier.constrainAs(nameHeaderText) {
            top.linkTo(longitudeHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customHighlightTextView(
                stringResource(R.string.airport_details_name),
                "${details?.name}",
                it
            )
        }

        Modifier.constrainAs(cityHeaderText) {
            top.linkTo(nameHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customHighlightTextView(
                stringResource(R.string.airport_details_city),
                "${details?.city}",
                it
            )
        }

        Modifier.constrainAs(countryIdHeaderText) {
            top.linkTo(cityHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customHighlightTextView(
                stringResource(R.string.airport_details_country_id),
                "${details?.countryId}",
                it
            )
        }

        Modifier.constrainAs(nearestAirportHeaderText) {
            top.linkTo(countryIdHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customHighlightTextView(
                stringResource(R.string.airport_details_nearest_airport),
                "${nearestAirPort?.name}",
                it
            )
        }

        Modifier.constrainAs(nearestAirportDistanceHeaderText) {
            top.linkTo(nearestAirportHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customHighlightTextView(
                stringResource(R.string.airport_details_nearest_airport_distance),
                "$nearestAirportDistance",
                it
            )
        }
    }
}