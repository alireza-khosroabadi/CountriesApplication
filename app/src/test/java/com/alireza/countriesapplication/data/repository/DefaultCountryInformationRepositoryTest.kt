package com.alireza.countriesapplication.data.repository

import com.alireza.CountryDetailQuery
import com.alireza.countriesapplication.data.datasourse.remote.continent.ContinentDataSource
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.repository.CountryInformationRepository
import com.apollographql.apollo3.api.ApolloResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DefaultCountryInformationRepositoryTest {

    @Mock
    private lateinit var dataSource: ContinentDataSource

    @Mock
    private lateinit var apolloResponse: ApolloResponse<CountryDetailQuery.Data>

    private lateinit var repository: CountryInformationRepository


    @Before
    fun setup(){
        repository = DefaultCountryInformation(
           continentDataSource = dataSource
        )
    }


    @Test
    fun `should return success when calling get country information`()= runTest{
        //Given
        whenever(dataSource.getCountryInformation(code = anyString())).thenReturn(apolloResponse)
        whenever(apolloResponse.hasErrors()).thenReturn(false)

        //When
        val countryInformation = repository.countryInformation("IR")

        //Then
        Truth.assertThat(countryInformation).isInstanceOf(ResultState.Success::class.java)
    }

    @Test
    fun `should return failure when calling get country information`() = runTest {
        // Given
        whenever(dataSource.getCountryInformation(code = anyString())).thenReturn(apolloResponse)
        whenever(apolloResponse.hasErrors()).thenReturn(true)

        // When
        val countryInformation = repository.countryInformation("IR")

        // Then
        Truth.assertThat(countryInformation).isInstanceOf(ResultState.Failure::class.java)
    }
}