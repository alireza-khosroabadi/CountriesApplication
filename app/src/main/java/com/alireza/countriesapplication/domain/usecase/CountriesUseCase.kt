package com.alireza.countriesapplication.domain.usecase

import com.alireza.countriesapplication.domain.model.Country
import com.alireza.countriesapplication.domain.model.ResultState

interface CountriesUseCase {
    suspend fun getCountries(code: String): ResultState<List<Country>>
}