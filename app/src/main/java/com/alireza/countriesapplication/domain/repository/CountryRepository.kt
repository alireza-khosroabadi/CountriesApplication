package com.alireza.countriesapplication.domain.repository

import com.alireza.countriesapplication.domain.model.Country
import com.alireza.countriesapplication.domain.model.ResultState

interface CountryRepository {
    suspend fun getCountries(code: String): ResultState<List<Country>>
}