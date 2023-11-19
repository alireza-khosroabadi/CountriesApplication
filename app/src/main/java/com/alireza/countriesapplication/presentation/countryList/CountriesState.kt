package com.alireza.countriesapplication.presentation.countryList

import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.Country
import javax.annotation.concurrent.Immutable


@Immutable
data class CountriesState(
    val countries: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedContinent: Continent? = null
)
