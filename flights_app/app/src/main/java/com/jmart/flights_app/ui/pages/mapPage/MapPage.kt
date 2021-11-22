package com.jmart.flights_app.ui.pages.mapPage

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.jmart.flights_app.R

@ExperimentalFoundationApi
    @Composable
    fun MapPage(navController: NavHostController) {
        Box(modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()) {
            Column {
                Gretings()
               MyMap(
                   modifier = Modifier
                       .padding(12.dp)
                       .size(300.dp)
                       .clip(RoundedCornerShape(10.dp))

                ){}

            }
        }
    }

    @Composable
    fun Gretings(){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()) {

            Column(verticalArrangement = Arrangement.Center) {

                Text(text = stringResource(R.string.maps_page_airports_title),
                    style = MaterialTheme.typography.h5,
                    color = Color.Black)

                Text(text = "Good Day!",
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
@Composable
fun MyMap(modifier: Modifier = Modifier, onReady: (GoogleMap) -> Unit) {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context)
    }

    val lifeCycle = LocalLifecycleOwner.current.lifecycle

    lifeCycle.addObserver(rememberMapLifeCycle(map = mapView))

    AndroidView(
        factory = {
            mapView.apply {
                mapView.getMapAsync(onReady)
            }
        },
        modifier = Modifier
    )

}

@Composable
fun rememberMapLifeCycle(map: MapView): LifecycleEventObserver {
    return remember {
        LifecycleEventObserver{ sourcer, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> map.onCreate(Bundle())
                Lifecycle.Event.ON_START -> map.onStart()
                Lifecycle.Event.ON_RESUME -> map.onResume()
                Lifecycle.Event.ON_PAUSE -> map.onPause()
                Lifecycle.Event.ON_STOP -> map.onStop()
                Lifecycle.Event.ON_DESTROY -> map.onDestroy()
                Lifecycle.Event.ON_ANY -> throw IllegalStateException()
            }
        }
    }
}