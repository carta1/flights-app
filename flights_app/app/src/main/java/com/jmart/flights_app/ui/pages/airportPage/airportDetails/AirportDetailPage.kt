package com.jmart.flights_app.ui.pages.airportPage.airportDetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jmart.flights_app.R
import com.jmart.flights_app.data.models.Airport

@ExperimentalFoundationApi
@Composable
fun AirportDetailsPage(navController: NavHostController, airportName: String?) {
    val airportDetailsViewModel = hiltViewModel<AirportDetailViewModel>()
    airportDetailsViewModel.getAirportDetails(airportName ?: "")
    val mAirPortDetails by airportDetailsViewModel.airPortDetails.observeAsState()
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
            .fillMaxWidth()

    ) {
        ConstraintLayoutContent(airportName, mAirPortDetails)
    }
}

@Composable
fun ConstraintLayoutContent(name: String?, details: Airport?) {
    ConstraintLayout {
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()

        // Create references for the composables to constrain
        val (titleText, idHeaderText, latitudeHeaderText,
            longitudeHeaderText, nameHeaderText, cityHeaderText, countryIdHeaderText) = createRefs()

        Text(
            text = stringResource(R.string.airport_details_title),
            style = MaterialTheme.typography.h5,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(titleText) {
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(parent.top, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }
        )

        Modifier.constrainAs(idHeaderText) {
            top.linkTo(titleText.bottom, margin = 32.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customDetailTextView(stringResource(R.string.airport_details_id), "${details?.id}", it)
        }

        Modifier.constrainAs(latitudeHeaderText) {
            top.linkTo(idHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customDetailTextView(
                stringResource(R.string.airport_details_latitude),
                "${details?.latitude}",
                it
            )
        }

        Modifier.constrainAs(longitudeHeaderText) {
            top.linkTo(latitudeHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customDetailTextView(
                stringResource(R.string.airport_details_longitude),
                "${details?.longitude}",
                it
            )
        }

        Modifier.constrainAs(nameHeaderText) {
            top.linkTo(longitudeHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customDetailTextView(
                stringResource(R.string.airport_details_name),
                "${details?.name}",
                it
            )
        }

        Modifier.constrainAs(cityHeaderText) {
            top.linkTo(nameHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customDetailTextView(
                stringResource(R.string.airport_details_city),
                "${details?.city}",
                it
            )
        }

        Modifier.constrainAs(countryIdHeaderText) {
            top.linkTo(cityHeaderText.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }.let {
            customDetailTextView(
                stringResource(R.string.airport_details_country_id),
                "${details?.countryId}",
                it
            )
        }
    }
}

// custom component for the airport details so the same code is not repeated
@Composable
fun customDetailTextView(
    HighlightText: String,
    nonHighLightText: String,
    modifier: Modifier = Modifier,
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("$HighlightText ")
            }
            append("$nonHighLightText ")
        },
        style = MaterialTheme.typography.body1,
        color = Color.Black,
        modifier = modifier
    )

}