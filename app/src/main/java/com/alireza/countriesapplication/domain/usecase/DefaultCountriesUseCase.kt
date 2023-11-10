package com.alireza.countriesapplication.domain.usecase

import com.alireza.countriesapplication.domain.model.Country
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.repository.CountryRepository
import javax.inject.Inject

class DefaultCountriesUseCase @Inject constructor(
    private val countryRepository: CountryRepository
) : CountriesUseCase {
    override suspend fun getCountries(code: String): ResultState<List<Country>> {
        return countryRepository.getCountries(code)
    }
}