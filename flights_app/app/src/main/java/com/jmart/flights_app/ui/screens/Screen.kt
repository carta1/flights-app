package com.jmart.flights_app.ui.screens


import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.jmart.flights_app.R

sealed class Screen (var route: String, @StringRes val resourceId: Int, var icon: ImageVector) {
    object Map : Screen("home", R.string.bottom_nav_menu_map, Icons.Default.LocationOn)
    object Airport : Screen("search",R.string.bottom_nav_menu_airport, Icons.Default.ThumbUp )
}