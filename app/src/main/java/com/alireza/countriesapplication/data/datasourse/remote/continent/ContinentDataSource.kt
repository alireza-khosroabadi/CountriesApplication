package com.alireza.countriesapplication.data.datasourse.remote.continent

import com.apollographql.apollo3.api.ApolloResponse
import com.alireza.ContinentQuery
import com.alireza.ContinentsQuery
import com.alireza.CountryDetailQuery

interface ContinentDataSource {
    suspend fun getContinents(): ApolloResponse<ContinentsQuery.Data>
    suspend fun getCountries(code: String): ApolloResponse<ContinentQuery.Data>
    suspend fun getCountryInformation(code: String): ApolloResponse<CountryDetailQuery.Data>
}