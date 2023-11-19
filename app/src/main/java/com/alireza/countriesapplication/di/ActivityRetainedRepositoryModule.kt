package com.alireza.countriesapplication.di

import com.alireza.countriesapplication.data.repository.DefaultContinentRepository
import com.alireza.countriesapplication.data.repository.DefaultCountryInformationRepository
import com.alireza.countriesapplication.data.repository.DefaultCountryRepository
import com.alireza.countriesapplication.domain.repository.ContinentRepository
import com.alireza.countriesapplication.domain.repository.CountryInformationRepository
import com.alireza.countriesapplication.domain.repository.CountryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedRepositoryModule {

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

    @ActivityRetainedScoped
    @Binds
    abstract fun bindCountryInfoRepository(
        defaultCountryInformationRepository: DefaultCountryInformationRepository
    ):CountryInformationRepository

}