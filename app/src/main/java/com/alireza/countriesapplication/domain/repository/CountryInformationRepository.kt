package com.alireza.countriesapplication.domain.repository

import com.alireza.countriesapplication.domain.model.CountryInformation
import com.alireza.countriesapplication.domain.model.ResultState

interface CountryInformationRepository {
    suspend fun countryInformation(countryCode: String): ResultState<CountryInformation?>
}