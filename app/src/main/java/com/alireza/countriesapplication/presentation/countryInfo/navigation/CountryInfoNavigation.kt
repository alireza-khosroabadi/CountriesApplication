package com.alireza.countriesapplication.presentation.countryInfo.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alireza.countriesapplication.presentation.countryInfo.CountryInfoScreen
import com.alireza.countriesapplication.presentation.countryInfo.CountryInfoViewModel
import com.alireza.countriesapplication.presentation.navigation.ScreenRouts

const val countryIdArg = "countryIdArg"
fun NavGraphBuilder.countryInfoScreen(){
    composable(ScreenRouts.CountryInfo.rout){
        val viewModel = hiltViewModel<CountryInfoViewModel>()
        val state by viewModel.countryState.collectAsStateWithLifecycle()
        CountryInfoScreen(state = state)
    }
}

fun NavController.navigateToCountryInfo(countryCode:String){
    this.navigate(ScreenRouts.CountryInfo.rout
        .replace("{$countryIdArg}", countryCode))
}