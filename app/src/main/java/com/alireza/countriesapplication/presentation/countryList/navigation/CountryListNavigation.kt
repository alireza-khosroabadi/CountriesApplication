package com.alireza.countriesapplication.presentation.countryList.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alireza.countriesapplication.presentation.countryList.CountriesViewModel
import com.alireza.countriesapplication.presentation.countryList.CountryListScreen
import com.alireza.countriesapplication.presentation.navigation.ScreenRouts

internal const val continentIdArg = "continentId"

fun NavGraphBuilder.countryListScreen(onNavigateCountryInfo: (countryCode: String) -> Unit) {
    composable(ScreenRouts.CountryList.rout) {
        val viewModel = hiltViewModel<CountriesViewModel>()
        val countriesState by viewModel.countryListState.collectAsStateWithLifecycle()
        CountryListScreen(countriesState, onNavigateCountryInfo)
    }
}

fun NavController.navigateToCountryList(continentCode:String) {
    this.navigate(ScreenRouts.CountryList.rout
        .replace("{$continentIdArg}", continentCode
    ))
}