package com.alireza.countriesapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String?,
    val emoji: String?,
    val phone: String?,
) : Parcelable
