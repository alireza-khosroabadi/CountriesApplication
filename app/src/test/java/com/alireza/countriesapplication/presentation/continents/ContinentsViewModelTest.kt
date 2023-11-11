package com.alireza.countriesapplication.presentation.continents

import app.cash.turbine.test
import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.usecase.FakeContinentsUseCase
import com.alireza.countriesapplication.util.Util
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ContinentsViewModelTest {


    private val testDispatcher = StandardTestDispatcher()
    private lateinit var useCase: FakeContinentsUseCase
    private lateinit var viewModel: ContinentsViewModel


    @Before
    fun setup() {
        useCase = FakeContinentsUseCase()
        viewModel = ContinentsViewModel(
            continentsUseCase = useCase,
            mainDispatcher = testDispatcher
        )
    }

    @After
    fun teardown() {

    }

    @Test
    fun `testing default values of ui state`() {
        // Given

        // Then
        Truth.assertThat(viewModel.continentsState.value.continents)
            .isEqualTo(emptyList<Continent>())
        Truth.assertThat(viewModel.continentsState.value.selectedContinent).isNull()
        Truth.assertThat(viewModel.continentsState.value.errorMessage).isNull()
        Truth.assertThat(viewModel.continentsState.value.isLoading).isFalse()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return list of continents if successful`() = runTest(testDispatcher.scheduler) {
        // Given
        useCase.setSuccessful(isSuccessful = true)
        val listOfContinents = Util.listOfContinents

        // When
        viewModel.onEvent(ContinentsEvent.RequestContinents)
        advanceUntilIdle()

        // Then
        viewModel.continentsState.test {
            Truth.assertThat(
                ContinentsState(
                    isLoading = true,
                    continents = emptyList(),
                    errorMessage = null,
                    selectedContinent = null
                )
            ).isEqualTo(awaitItem())

            Truth.assertThat(
                ContinentsState(
                    isLoading = false,
                    continents = listOfContinents,
                    errorMessage = null,
                    selectedContinent = null
                )
            ).isEqualTo(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return error if not successful`() = runTest(testDispatcher.scheduler) {
        // Given
        useCase.setSuccessful(isSuccessful = false)

        // When
        viewModel.onEvent(ContinentsEvent.RequestContinents)
        advanceUntilIdle()

        // Then
        viewModel.continentsState.test {
            Truth.assertThat(
                ContinentsState(
                    isLoading = true,
                    continents = emptyList(),
                    errorMessage = null,
                    selectedContinent = null
                )
            ).isEqualTo(awaitItem())

            Truth.assertThat(
                ContinentsState(
                    isLoading = false,
                    continents = emptyList(),
                    errorMessage = "",
                    selectedContinent = null
                )
            ).isEqualTo(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}