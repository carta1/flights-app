package com.jmart.flights_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jmart.flights_app.ui.pages.airportPage.AirportPage
import com.jmart.flights_app.ui.pages.airportPage.airportDetails.AirportDetailsPage
import com.jmart.flights_app.ui.pages.mapPage.MapPage
import com.jmart.flights_app.ui.pages.settings.SettingsPage
import com.jmart.flights_app.ui.screens.NavScreens
import com.jmart.flights_app.ui.theme.Flights_appTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Flights_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    mainPageContent()
                }
            }
        }
    }
}


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun mainPageContent() {
    val bottomTabBarItems = listOf(NavScreens.Map, NavScreens.Airport, NavScreens.Settings)

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            TopAppBar(
               title =  {
                   Text(
                       text = stringResource(R.string.app_name_title),
                       textAlign = TextAlign.Center
                   )
               },
            )
        },
        bottomBar = {
            // it does not show the nav bar when the user is in the airport detail page
            if (currentRoute != NavScreens.AirportDetails.route) {
                bottomNavigationComponent(navController, bottomTabBarItems, navBackStackEntry)
            }
        }
    ) { innerPadding ->
        // set up navHost and bottom navigation
        NavHost(
            navController,
            startDestination = NavScreens.Map.route,
            Modifier.padding(innerPadding)
        ) {
            composable(NavScreens.Map.route) { MapPage(navController) }
            composable(NavScreens.Airport.route) { AirportPage(navController) }
            composable(NavScreens.Settings.route) { SettingsPage(navController) }
            composable(
                NavScreens.AirportDetails.route,
                arguments = listOf(navArgument(NavScreens.AirportDetails.args) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val airportName = backStackEntry.arguments?.getString(NavScreens.AirportDetails.args)?: return@composable
                AirportDetailsPage(
                    navController,
                    airportName
                ) {
                    navController.navigateUp()
                }
            }
        }
    }
}

@Composable
fun bottomNavigationComponent(navController: NavHostController, bottomTabBarItems: List<NavScreens>, navBackStackEntry: NavBackStackEntry? ) {
    BottomNavigation {
        val currentDestination = navBackStackEntry?.destination
        bottomTabBarItems.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }

    }
}