package com.alireza.countriesapplication.presentation.country

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alireza.countriesapplication.data.di.MainDispatcher
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.usecase.CountriesUseCase
import com.alireza.countriesapplication.presentation.country.navigation.continentIdArg
import com.alireza.countriesapplication.presentation.country.navigation.continentNameArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val countriesUseCase: CountriesUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _continentsState = MutableStateFlow(CountriesState())
    val continentsState = _continentsState.asStateFlow()

    private val continentName = savedStateHandle.get<String>(continentNameArg).orEmpty()
    private val continentId = savedStateHandle.get<String>(continentIdArg).orEmpty()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _continentsState.update { continentsState ->
            continentsState.copy(
                errorMessage = exception.message ?: "Something went wrong, please try again!"
            )
        }
    }

    init {
        selectContinent(continentId)
    }

    private fun selectContinent(continentCode: String) {
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            // Loading state
            _continentsState.update { continentsState ->
                continentsState.copy(
                    isLoading = true
                )
            }

            // Getting continents
            when (val result = countriesUseCase.getCountries(continentCode)) {
                is ResultState.Success -> {
                    _continentsState.update { continentsState ->
                        continentsState.copy(
                            countries = result.data,
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