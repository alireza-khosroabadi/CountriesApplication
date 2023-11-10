package com.alireza.countriesapplication.presentation.continents

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alireza.countriesapplication.data.di.MainDispatcher
import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.usecase.ContinentsUseCase
import com.alireza.countriesapplication.domain.usecase.CountriesUseCase
import com.alireza.countriesapplication.presentation.continents.ContinentsEvent
import com.alireza.countriesapplication.presentation.continents.ContinentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContinentsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val continentsUseCase: ContinentsUseCase,
    private val countriesUseCase: CountriesUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _continentsState = MutableStateFlow(ContinentsState())
    val continentsState = _continentsState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _continentsState.update { continentsState ->
            continentsState.copy(
                errorMessage = exception.message ?: "Something went wrong, please try again!"
            )
        }
    }

    private fun getContinents() {
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            // Loading state
            _continentsState.update { continentsState ->
                continentsState.copy(
                    isLoading = true
                )
            }

            // Getting continents
            when (val result = continentsUseCase.getContinents()) {
                is ResultState.Success -> {
                    _continentsState.update { continentsState ->
                        continentsState.copy(
                            continents = result.data,
                            isLoading = false
                        )
                    }
                }

                is ResultState.Failure -> {
                    _continentsState.update { continentsState ->
                        continentsState.copy(
                            isLoading = false,
                            errorMessage = result.error
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: ContinentsEvent) {
        when (event) {
            is ContinentsEvent.RequestContinents -> getContinents()
        }
    }

    fun selectContinent(continent: Continent) {
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            _continentsState.update { continentsState ->
                continentsState.copy(
                    selectedContinent = continent
                )
            }
        }
    }

    fun resetSelectedCountry() {
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            _continentsState.update { continentsState ->
                continentsState.copy(
                    selectedContinent = null
                )
            }
        }
    }
}