package com.alireza.countriesapplication.presentation.country.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alireza.countriesapplication.presentation.country.CountriesViewModel
import com.alireza.countriesapplication.presentation.country.DetailsScreen
import com.alireza.countriesapplication.presentation.navigation.ScreenRouts

internal const val continentNameArg = "continentName"
internal const val continentIdArg = "continentId"

fun NavGraphBuilder.countryListScreen() {
    composable(ScreenRouts.CountryList.rout) {
        val viewModel = hiltViewModel<CountriesViewModel>()
        val countriesState by viewModel.continentsState.collectAsStateWithLifecycle()
        DetailsScreen(countriesState)
    }
}

fun NavController.navigateToCountryList(continentName: String, continentCode:String) {
    this.navigate(ScreenRouts.CountryList.rout
        .replace("{$continentNameArg}",continentName)
        .replace("{$continentIdArg}", continentCode
    ))
}