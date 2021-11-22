package com.jmart.flights_app.ui.pages.airportPage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@ExperimentalFoundationApi
@Composable
fun AirportPage(navController: NavHostController) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
    ) {
        Column {
            Gretings()

        }
    }
}


@Composable
fun Gretings(name: String = "Hello Jair Airport") {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {

        Column(verticalArrangement = Arrangement.Center) {

            Text(
                text = "Good Morning, $name",
                style = MaterialTheme.typography.h5,
                color = Color.Black
            )

            Text(
                text = "Good Day!",
                style = MaterialTheme.typography.body2,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}