package com.alireza.countriesapplication.domain.usecase

import com.alireza.countriesapplication.data.model.toCountryInformation
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.repository.CountryInformationRepository
import com.alireza.countriesapplication.util.Util
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
class DefaultCountryInformationUseCaseTest {

    @Mock
    private lateinit var repository:CountryInformationRepository

    private lateinit var useCase: CountryInformationUseCase

    @Before
    fun setup(){
        useCase = DefaultCountryInformationUseCase(repository)
    }

    @Test
    fun `should return success with empty list when calling get country information`() = runTest {
        // Given
        whenever(repository.countryInformation(anyString())).thenReturn(ResultState.Success(
            Util.fakeCountryInformation))

        // When
        val countries = useCase.countryInformation("test")

        // Then
        Truth.assertThat(countries).isInstanceOf(ResultState.Success::class.java)
        Truth.assertThat(countries).isEqualTo(ResultState.Success(Util.fakeCountryInformation))
    }

    @Test
    fun `should return success with some data when calling get countries`() = runTest {
        // Given
        whenever(repository.countryInformation(anyString())).thenReturn(ResultState.Success(Util.fakeCountryInformation))

        // When
        val countries = useCase.countryInformation("test")

        // Then
        Truth.assertThat(countries).isInstanceOf(ResultState.Success::class.java)
        Truth.assertThat(countries).isEqualTo(ResultState.Success(Util.fakeCountryInformation))
    }

    @Test
    fun `should return failure when calling get countries`() = runTest {
        // Given
        whenever(repository.countryInformation(anyString())).thenReturn(ResultState.Failure(error = ""))

        // When
        val countries = useCase.countryInformation("test")

        // Then
        Truth.assertThat(countries).isInstanceOf(ResultState.Failure::class.java)
    }
}