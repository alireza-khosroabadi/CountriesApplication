package com.alireza.countriesapplication.domain.usecase

import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.ResultState

interface ContinentsUseCase {
    suspend fun getContinents(): ResultState<List<Continent>>
}