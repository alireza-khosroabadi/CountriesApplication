package com.alireza.countriesapplication.data.model

import com.alireza.ContinentQuery
import com.alireza.ContinentsQuery
import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.Country

fun ContinentQuery.Continent.toCountry(): List<Country> {
    return this.countries.map {
        Country(
            name = it.name,
            emoji = it.emoji,
            currency = it.currency,
            capital = it.capital,
            phone = it.phone,
            states = it.states.map { state -> state.name },
            languages = it.languages.map { language -> language.name }
        )
    }
}

fun ContinentsQuery.Continent.toContinent(): Continent {
    return Continent(
        name = this.name,
        code = this.code
    )
}