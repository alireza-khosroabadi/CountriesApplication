package com.alireza.countriesapplication.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alireza.countriesapplication.presentation.continents.navigation.continentListScreen
import com.alireza.countriesapplication.presentation.countryInfo.navigation.countryInfoScreen
import com.alireza.countriesapplication.presentation.countryInfo.navigation.navigateToCountryInfo
import com.alireza.countriesapplication.presentation.countryList.navigateToCountryListScreen
import com.alireza.countriesapplication.presentation.countryList.navigation.countryListScreen
import com.alireza.countriesapplication.presentation.countryList.navigation.navigateToCountryList


@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    snackBarHostState: SnackbarHostState
) {
    NavHost(navController = navController, startDestination = ScreenRouts.ContinentList.rout) {
        continentListScreen{navController.navigateToCountryListScreen(it.code.orEmpty())}
        countryListScreen{navController.navigateToCountryInfo(countryCode = it)}
        countryInfoScreen()
    }
}