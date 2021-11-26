package com.jmart.flights_app.ui.pages.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults.colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jmart.flights_app.R
import com.jmart.flights_app.ui.pages.airportPage.MILES

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun SettingsPage(navController: NavHostController) {
    val settingsViewModel = hiltViewModel<SettingViewModel>()
    val distanceUnit by settingsViewModel.userDistanceUnit.observeAsState()

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        constraintLayoutContent(settingsViewModel, distanceUnit)
    }
}

@ExperimentalComposeUiApi
@Composable
fun constraintLayoutContent(viewModel: SettingViewModel, distanceUnit: String?) {
    val isMilesChecked: MutableState<Boolean>
    val isKilometerChecked: MutableState<Boolean>
    if (distanceUnit == MILES) {
        isMilesChecked = remember { mutableStateOf(true) }
        isKilometerChecked = remember { mutableStateOf(false) }
    } else {
        isMilesChecked = remember { mutableStateOf(false) }
        isKilometerChecked = remember { mutableStateOf(true) }
    }

    ConstraintLayout {
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()

        // Create references for the composables to constrain
        val (titleText, kilometerText, kilometersCheckbox, milesText, milesCheckbox) = createRefs()

        Text(
            text = stringResource(R.string.settings_page_title),
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

        Text(
            text = stringResource(R.string.settings_page_distance_unit_kilometer),
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(kilometerText) {
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(titleText.bottom, margin = 16.dp)
            }
        )

        Text(
            text = stringResource(R.string.settings_page_distance_unit_mile),
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(milesText) {
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(kilometerText.bottom, margin = 16.dp)
            }
        )

        Checkbox(
            checked = isKilometerChecked.value,
            onCheckedChange = {
                isKilometerChecked.value = it
                if (it) {
                    isMilesChecked.value = false
                    viewModel.setKilometerDistanceUnit()
                }
            },
            colors = colors(
                checkedColor = colorResource(R.color.black),
                uncheckedColor = colorResource(R.color.black),
                checkmarkColor = Color.White,
            ),
            modifier = Modifier.constrainAs(kilometersCheckbox) {
                start.linkTo(kilometerText.end, margin = 16.dp)
                top.linkTo(titleText.bottom, margin = 16.dp)
            }
        )

        Checkbox(
            checked = isMilesChecked.value,
            onCheckedChange = {
                isMilesChecked.value = it
                if (it) {
                    isKilometerChecked.value = false
                    viewModel.setMilesDistanceUnit()
                }

            },
            colors = colors(
                checkedColor = colorResource(R.color.black),
                uncheckedColor = colorResource(R.color.black),
                checkmarkColor = Color.White,
            ),
            modifier = Modifier.constrainAs(milesCheckbox) {
                start.linkTo(kilometerText.end, margin = 16.dp)
                top.linkTo(kilometersCheckbox.bottom, margin = 16.dp)
            }
        )
    }
}