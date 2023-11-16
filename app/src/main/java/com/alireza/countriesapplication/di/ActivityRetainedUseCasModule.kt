package com.alireza.countriesapplication.di

import com.alireza.countriesapplication.domain.usecase.ContinentsUseCase
import com.alireza.countriesapplication.domain.usecase.CountriesUseCase
import com.alireza.countriesapplication.domain.usecase.DefaultContinentsUseCase
import com.alireza.countriesapplication.domain.usecase.DefaultCountriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedUseCasModule {
    @Binds
    abstract fun bindContinentsUseCase(
        defaultContinentsUseCase: DefaultContinentsUseCase
    ): ContinentsUseCase

    @Binds
    abstract fun bindCountriesUseCase(
        defaultCountriesUseCase: DefaultCountriesUseCase
    ): CountriesUseCase
}