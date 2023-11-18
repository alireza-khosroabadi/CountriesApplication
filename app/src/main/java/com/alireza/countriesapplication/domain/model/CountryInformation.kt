package com.alireza.countriesapplication.domain.model
 data class CountryInformation(
    val name: String,
    val awsRegion: String,
    val currencies: List<String>,
    val emoji: String,
    val emojiU: String,
    val capital: String?,
    val native: String,
    val phones: List<String>,
    val states: List<State>,
    val subdivisions: List<Subdivision>,
    val languages: List<Language>,
)

data class State(
    val code: String?,
    val name: String,
)

data class Subdivision(
    val code: String,
    val name: String,
)

data class Language(
    val name: String,
    val native: String,
    val code: String,
    val rtl: Boolean,
)
