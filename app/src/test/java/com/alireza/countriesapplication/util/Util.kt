package com.alireza.countriesapplication.util

import com.alireza.CountryDetailQuery
import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.countriesapplication.domain.model.CountryInformation
import com.alireza.test.CountryDetailQuery_TestBuilder.Data

object Util {

    val listOfContinents = listOf(Continent("Africa", "AF"), Continent("Asia", "AS"))
    val fakeCountryInformation = CountryInformation(
        name = "test",
        awsRegion = "",
        capital = "test"   ,
        currencies = emptyList(),
        emojiU = "",
        emoji = "",
        native = "",
        phones = emptyList(),
        states = emptyList(),
        subdivisions = emptyList(),
        languages = emptyList()
    )
}