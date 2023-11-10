package com.alireza.countriesapplication.presentation.continents

import com.alireza.countriesapplication.domain.model.Continent
import javax.annotation.concurrent.Immutable


@Immutable
data class ContinentsState(
    val continents: List<Continent> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedContinent: Continent? = null
)
