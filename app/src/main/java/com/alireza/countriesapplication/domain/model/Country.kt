package com.alireza.countriesapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val capital: String?,
    val phone: String?,
    val states: List<String?>?,
    val languages: List<String?>?
) : Parcelable
