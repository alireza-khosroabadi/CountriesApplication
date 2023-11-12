package com.alireza.countriesapplication.data.repository

import com.apollographql.apollo3.exception.ApolloException
import com.alireza.countriesapplication.data.datasourse.remote.continent.ContinentDataSource
import com.alireza.countriesapplication.data.model.toCountry
import com.alireza.countriesapplication.domain.model.Country
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.repository.CountryRepository
import javax.inject.Inject

class DefaultCountryRepository @Inject constructor(
    private val continentDataSource: ContinentDataSource
) : CountryRepository {
    override suspend fun getCountries(code: String): ResultState<List<Country>> {
        try {
            val response = continentDataSource.getCountries(code = code)
            return if (!response.hasErrors()) {
                ResultState.Success(
                    response.data?.continent?.toCountry().orEmpty()
                )
            } else {
                ResultState.Failure(
                    response.errors?.getOrNull(0)?.message
                        .orEmpty()
                        .ifEmpty { "Something went wrong" }
                )
            }
        } catch (exception: ApolloException) {
            throw exception
        }
    }
}