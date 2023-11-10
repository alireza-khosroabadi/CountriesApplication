package com.alireza.countriesapplication.domain.repository

import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.ResultState

interface ContinentRepository {
    suspend fun getContinents(): ResultState<List<Continent>>
}