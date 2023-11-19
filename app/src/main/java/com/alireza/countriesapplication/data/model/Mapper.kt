package com.alireza.countriesapplication.data.model

import com.alireza.ContinentQuery
import com.alireza.ContinentsQuery
import com.alireza.CountryDetailQuery
import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.Country
import com.alireza.countriesapplication.domain.model.CountryInformation
import com.alireza.countriesapplication.domain.model.Language
import com.alireza.countriesapplication.domain.model.State
import com.alireza.countriesapplication.domain.model.Subdivision

fun ContinentQuery.Continent.toCountryInformation(): List<Country> {
    return this.countries.map {
        Country(
            name = it.name,
            emoji = it.emoji,
            phone = it.phone,
            code = it.code
//            states = it.states.map { state -> state.name },
//            languages = it.languages.map { language -> language.name }
        )
    }
}

fun ContinentsQuery.Continent.toContinent(): Continent {
    return Continent(
        name = this.name,
        code = this.code
    )
}

fun CountryDetailQuery.Country.toCountryInformation(): CountryInformation {
    return CountryInformation(
        name= name,
        awsRegion = awsRegion,
        currencies = currencies,
        emoji = emoji,
        emojiU = emojiU,
        capital = capital,
        native = native,
        phones = phones,
        states = states.map { it.toState() },
        subdivisions = subdivisions.map { it.toSubdivision()},
        languages = languages.map { it.toLanguage() }

    )
}

fun CountryDetailQuery.Language.toLanguage():Language{
    return Language(
        name = name,
        native = native,
        code = code,
        rtl = rtl
    )
}

fun CountryDetailQuery.State.toState():State{
    return State(
        code = code,
        name = name
    )
}

fun CountryDetailQuery.Subdivision.toSubdivision(): Subdivision{
    return Subdivision(
        code = code,
        name = name
    )
}