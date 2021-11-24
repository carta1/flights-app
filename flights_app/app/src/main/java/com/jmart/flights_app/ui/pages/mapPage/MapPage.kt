package com.jmart.flights_app.ui.pages.mapPage


import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.jmart.flights_app.R
import com.jmart.flights_app.data.models.Airport
import com.jmart.flights_app.ui.screens.NavScreens
import kotlinx.coroutines.launch

private const val INITIAL_ZOOM = 5f
private const val AMS_LAT = "52.30907"
private const val AMS_LONG = "4.763385"
private const val AMS_AIRPORT = "Amsterdam-Schiphol Airport"

@ExperimentalFoundationApi
@Composable
fun MapPage(navController: NavHostController) {
    val exampleViewModel = hiltViewModel<MapPageViewModel>()
    exampleViewModel.getAllAirports()
    val airportsList by exampleViewModel.airPorts.observeAsState()
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
    ) {

        airportsList?.let { airports ->
            DetailsContent(
                Modifier
                    .fillMaxSize()
                    .fillMaxHeight(),
                airports,
                navController
            )
        }
    }

}

// Remembers a MapView and gives it the lifecycle of the current LifecycleOwner
@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map_frame
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, mapView) {
        // Make MapView follow the current lifecycle
        val lifecycleObserver = getMapLifecycleObserver(mapView)
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

private fun getMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
        }
    }

@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    coordinates: List<Airport>,
    navController: NavHostController
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Top) {
        CityMapView(coordinates, navController)
    }
}

/* The MapView lifecycle is handled by this composable. As the MapView also needs to be updated
   with input from Compose UI, those updates are encapsulated into the MapViewContainer
   composable. In this way, when an update to the MapView happens, this composable won't
   recompose and the MapView won't need to be recreated. */
@Composable
private fun CityMapView(
    coordinates: List<Airport>,
    navController: NavHostController
) {
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(mapView, coordinates, navController)
}

@Composable
private fun MapViewContainer(
    map: MapView,
    coordinates: List<Airport>,
    navController: NavHostController
) {
    val cameraPosition = remember(AMS_LAT, AMS_LONG) {
        LatLng(AMS_LAT.toDouble(), AMS_LONG.toDouble())
    }

    LaunchedEffect(map) {
        val googleMap = map.awaitMap()

        // adds the market and position to all the airports
        coordinates.forEach {
            googleMap.addMarker {
                position(LatLng(it.latitude, it.longitude))
                    .title(it.name)
            }
        }

        // Initial marker for reference purposes is AMS
        googleMap.addMarker { position(cameraPosition).title(AMS_AIRPORT) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, INITIAL_ZOOM))

        googleMap.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { marker ->
            // which is clicked it will navigate to the storeDetails
            val markerName: String = marker.title ?: "N/A"
            navController.navigate(NavScreens.AirportDetails.getNavigationRouteWithArgs(markerName))
            false
        })

    }

    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        // Reading zoom so that AndroidView recomposes when it changes. The getMapAsync lambda
        // is stored for later, Compose doesn't recognize state reads
        coroutineScope.launch {
            mapView.awaitMap()
        }
    }
}

private fun getAirportInfo(airportName: String, airPortList: List<Airport>): Airport? {
    return airPortList.find { airport -> airport.toString().contains(airportName) }
}