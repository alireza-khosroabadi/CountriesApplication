package com.alireza.countriesapplication.domain.usecase

import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.repository.ContinentRepository
import javax.inject.Inject

class DefaultContinentsUseCase @Inject constructor(
    private val continentRepository: ContinentRepository
) : ContinentsUseCase {
    override suspend fun getContinents(): ResultState<List<Continent>> {
        return continentRepository.getContinents()
    }
}