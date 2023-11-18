package com.alireza.countriesapplication.data.repository

import com.alireza.countriesapplication.data.datasourse.remote.continent.ContinentDataSource
import com.alireza.countriesapplication.data.model.toCountryInformation
import com.alireza.countriesapplication.domain.model.CountryInformation
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.repository.CountryInformationRepository
import com.apollographql.apollo3.exception.ApolloException
import javax.inject.Inject

class DefaultCountryInformation @Inject constructor(private val continentDataSource: ContinentDataSource) :
    CountryInformationRepository {
    override suspend fun countryInformation(countryCode: String): ResultState<CountryInformation?> {
        try {
            val response = continentDataSource.getCountryInformation(countryCode)
            return if (!response.hasErrors()){
                ResultState.Success(response.data?.country?.toCountryInformation())
            }else{
                ResultState.Failure(
                    response.errors?.getOrNull(0)?.message
                        .orEmpty()
                        .ifEmpty { "Something went wrong" }
                )
            }
        }catch (exception: ApolloException) {
            throw exception
        }
    }
}