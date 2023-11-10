package com.alireza.countriesapplication.data.di

import com.alireza.countriesapplication.data.datasourse.remote.continent.ContinentDataSource
import com.alireza.countriesapplication.data.datasourse.remote.continent.DefaultContinentDataSource
import com.alireza.countriesapplication.data.repository.DefaultContinentRepository
import com.alireza.countriesapplication.data.repository.DefaultCountryRepository
import com.alireza.countriesapplication.domain.repository.ContinentRepository
import com.alireza.countriesapplication.domain.repository.CountryRepository
import com.alireza.countriesapplication.domain.usecase.ContinentsUseCase
import com.alireza.countriesapplication.domain.usecase.CountriesUseCase
import com.alireza.countriesapplication.domain.usecase.DefaultContinentsUseCase
import com.alireza.countriesapplication.domain.usecase.DefaultCountriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    // Datasource
    @Binds
    abstract fun bindContinentsDataSource(
        defaultContinentDataSource: DefaultContinentDataSource
    ): ContinentDataSource

    // Repositories
    @ActivityRetainedScoped
    @Binds
    abstract fun bindContinentRepository(
        defaultContinentRepository: DefaultContinentRepository
    ): ContinentRepository

    @ActivityRetainedScoped
    @Binds
    abstract fun bindCountryRepository(
        defaultCountryRepository: DefaultCountryRepository
    ): CountryRepository

    // Usecases
    @Binds
    abstract fun bindContinentsUseCase(
        defaultContinentsUseCase: DefaultContinentsUseCase
    ): ContinentsUseCase

    @Binds
    abstract fun bindCountriesUseCase(
        defaultCountriesUseCase: DefaultCountriesUseCase
    ): CountriesUseCase
}