package com.alireza.countriesapplication.presentation.continents

sealed class ContinentsEvent {
    data object RequestContinents: ContinentsEvent()
}
