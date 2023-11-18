package com.alireza.countriesapplication.domain.usecase

import com.alireza.countriesapplication.domain.model.CountryInformation
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.repository.CountryInformationRepository
import javax.inject.Inject

class DefaultCountryInformationUseCase @Inject constructor(private val countryInformationRepository: CountryInformationRepository) :
    CountryInformationUseCase {
    override suspend fun countryInformation(code: String): ResultState<CountryInformation?> {
        return countryInformationRepository.countryInformation(code)
    }
}