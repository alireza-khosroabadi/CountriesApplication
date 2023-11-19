package com.alireza.countriesapplication.presentation.countryInfo

import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.Country
import com.alireza.countriesapplication.domain.model.CountryInformation
import javax.annotation.concurrent.Immutable


@Immutable
data class CountryInfoState(
    val countryInfo: CountryInformation? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
