package com.alireza.countriesapplication.domain.usecase

import com.alireza.countriesapplication.domain.model.CountryInformation
import com.alireza.countriesapplication.domain.model.ResultState

interface CountryInformationUseCase {
    suspend fun countryInformation(code:String):ResultState<CountryInformation?>
}