package com.jmart.flights_app.ui.screens


import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.jmart.flights_app.R

/** This is a seal class that manages the nav routes, nav args, and also the bottom nav components,
it creates singletons so they are only created once for efficiency
 */
sealed class NavScreens(
    var route: String,
    @StringRes val resourceId: Int,
    var icon: ImageVector,
    var args: String
) {
    object Map : NavScreens(
        NavRoutes.MAP.name,
        R.string.bottom_nav_menu_map,
        Icons.Default.LocationOn,
        NavArgs.MAP.name
    ) {
        fun getNavigationRouteWithArgs(airportName: String): String {
            return "${NavRoutes.AIRPORT_DETAILS.name}/$airportName"
        }
    }

    object Airport : NavScreens(
        NavRoutes.AIRPORT.name,
        R.string.bottom_nav_menu_airport,
        Icons.Default.LocationOn,
        NavArgs.AIRPORT.name
    )

    object Settings : NavScreens(
        NavRoutes.SETTINGS.name,
        R.string.bottom_nav_menu_settings,
        Icons.Default.AccountBox,
        NavArgs.SETTINGS.name
    )

    object AirportDetails : NavScreens(
        "${NavRoutes.AIRPORT_DETAILS.name}/{${NavArgs.AIRPORT_NAME.name}}",
        R.string.bottom_nav_menu_airport,
        Icons.Default.LocationOn,
        NavArgs.AIRPORT_NAME.name
    ) {
        fun getNavigationRouteWithArgs(airportName: String): String {
            return "${NavRoutes.AIRPORT_DETAILS.name}/$airportName"
        }
    }
}

// enum class to keep the args organized
enum class NavArgs(args: String) {
    MAP(""),
    AIRPORT(""),
    AIRPORT_NAME("airportDetails"),
    SETTINGS("")
}

// enum class to keep the routes organized organized
enum class NavRoutes(route: String) {
    MAP("home"),
    AIRPORT("search"),
    AIRPORT_DETAILS("airportDetails"),
    SETTINGS("settings")
}
