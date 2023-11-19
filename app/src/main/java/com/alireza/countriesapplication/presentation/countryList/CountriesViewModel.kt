package com.alireza.countriesapplication.presentation.countryList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alireza.countriesapplication.di.MainDispatcher
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.usecase.CountriesUseCase
import com.alireza.countriesapplication.presentation.countryList.navigation.continentIdArg
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
    savedStateHandle: SavedStateHandle,
    private val countriesUseCase: CountriesUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _countryListState = MutableStateFlow(CountriesState())
    val countryListState = _countryListState.asStateFlow()

    private val continentId = savedStateHandle.get<String>(continentIdArg).orEmpty()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _countryListState.update { oldState ->
            oldState.copy(
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
            _countryListState.update { oldState ->
                oldState.copy(
                    isLoading = true
                )
            }

            // Getting country list
            when (val result = countriesUseCase.getCountries(continentCode)) {
                is ResultState.Success -> {
                    _countryListState.update { oldState ->
                        oldState.copy(
                            countries = result.data,
                            isLoading = false
                        )
                    }
                }

                is ResultState.Failure -> {
                    _countryListState.update { oldState ->
                        oldState.copy(
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
            _countryListState.update { oldState ->
                oldState.copy(
                    selectedContinent = null
                )
            }
        }
    }
}