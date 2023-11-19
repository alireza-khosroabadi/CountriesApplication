package com.alireza.countriesapplication.presentation.countryInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alireza.countriesapplication.di.MainDispatcher
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.domain.usecase.CountryInformationUseCase
import com.alireza.countriesapplication.presentation.countryInfo.navigation.countryIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryInfoViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val countryInformationUseCase: CountryInformationUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _countryState = MutableStateFlow(CountryInfoState())
    val countryState = _countryState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _countryState.update {oldState->
            oldState.copy(
                errorMessage = exception.message ?: "Something went wrong, please try again!"
            )
        }
    }


    init {
        val countryId = stateHandle.get<String>(countryIdArg).orEmpty()
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            // Loading state
            _countryState.update { continentsState ->
                continentsState.copy(
                    isLoading = true
                )
            }

            // Get country info
            when(val result = countryInformationUseCase.countryInformation(countryId)){
                is ResultState.Failure -> {
                    _countryState.update { oldState ->
                        oldState.copy(
                            isLoading = false,
                            errorMessage = result.error
                        )
                    }
                }
                is ResultState.Success -> {
                    _countryState.update {oldState ->
                        oldState.copy(
                            countryInfo = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

}