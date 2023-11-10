package com.alireza.countriesapplication.presentation.continents.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.presentation.continents.ContinentsEvent
import com.alireza.countriesapplication.presentation.continents.ContinentsScreen
import com.alireza.countriesapplication.presentation.continents.ContinentsViewModel
import com.alireza.countriesapplication.presentation.navigation.ScreenRouts

fun NavGraphBuilder.continentListScreen(onNavigateCountryList: (Continent) -> Unit) {
    composable(ScreenRouts.ContinentList.rout) {
        val viewModel = hiltViewModel<ContinentsViewModel>()
        val continentListState by viewModel.continentsState.collectAsStateWithLifecycle()
        ContinentsScreen(
            continentListState,
            onNavigateCountryList,
        ) { viewModel.onEvent(ContinentsEvent.RequestContinents) }
    }
}