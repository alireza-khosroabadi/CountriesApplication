package com.alireza.countriesapplication.data.datasourse.remote.continent

import com.apollographql.apollo3.api.ApolloResponse
import com.alireza.ContinentQuery
import com.alireza.ContinentsQuery

interface ContinentDataSource {
    suspend fun getContinents(): ApolloResponse<ContinentsQuery.Data>
    suspend fun getContinent(code: String): ApolloResponse<ContinentQuery.Data>
}