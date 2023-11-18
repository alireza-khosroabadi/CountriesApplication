package com.alireza.countriesapplication.data.datasourse.remote.continent

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.alireza.ContinentQuery
import com.alireza.ContinentsQuery
import com.alireza.CountryDetailQuery
import javax.inject.Inject

class DefaultContinentDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : ContinentDataSource {
    override suspend fun getContinents(): ApolloResponse<ContinentsQuery.Data> {
        return apolloClient
            .query(ContinentsQuery())
            .execute()
    }

    override suspend fun getCountries(code: String): ApolloResponse<ContinentQuery.Data> {
        return apolloClient
            .query(ContinentQuery(continent_code = code))
            .execute()
    }

    override suspend fun getCountryInformation(code: String): ApolloResponse<CountryDetailQuery.Data> {
        return apolloClient
            .query(CountryDetailQuery(country_code = code))
            .execute()
    }
}