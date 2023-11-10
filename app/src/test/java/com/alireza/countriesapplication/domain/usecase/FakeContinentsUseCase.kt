package com.alireza.countriesapplication.domain.usecase

import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.ResultState
import com.alireza.countriesapplication.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeContinentsUseCase : ContinentsUseCase {

    private var isSuccessful: Boolean = true

    fun setSuccessful(isSuccessful: Boolean) {
        this.isSuccessful = isSuccessful
    }

    override suspend fun getContinents(): ResultState<List<Continent>> =
        withContext(Dispatchers.Default) {
            delay(1000L)
            if (isSuccessful) {
                ResultState.Success(Util.listOfContinents)
            } else {
                ResultState.Failure(error = "")
            }
        }
}