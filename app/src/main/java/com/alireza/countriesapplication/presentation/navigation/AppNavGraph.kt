package com.alireza.countriesapplication.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alireza.countriesapplication.presentation.continents.navigation.continentListScreen
import com.alireza.countriesapplication.presentation.details.navigation.countryListScreen
import com.alireza.countriesapplication.presentation.details.navigation.navigateToCountryList


@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    snackBarHostState: SnackbarHostState
) {
    NavHost(navController = navController, startDestination = ScreenRouts.ContinentList.rout) {
        continentListScreen{navController.navigateToCountryList(
            it.name.orEmpty(), it.code.orEmpty()
        )}

        countryListScreen()
    }
}