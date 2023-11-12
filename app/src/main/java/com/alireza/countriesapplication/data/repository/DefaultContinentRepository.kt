package com.alireza.countriesapplication.data.repository

import com.apollographql.apollo3.exception.ApolloException
import com.alireza.countriesapplication.data.datasourse.remote.continent.ContinentDataSource
import com.alireza.countriesapplication.data.model.toContinent
import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.repository.ContinentRepository
import javax.inject.Inject

class DefaultContinentRepository @Inject constructor(
    private val continentDataSource: ContinentDataSource
) : ContinentRepository {

    override suspend fun getContinents(): ResultState<List<Continent>> {
        try {
            val response = continentDataSource.getContinents()
            return if (!response.hasErrors()) {
                val continents = response.data?.continents
                    ?.map {
                        it.toContinent()
                    }?.sortedBy { it.name }.orEmpty()
                ResultState.Success(continents)
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