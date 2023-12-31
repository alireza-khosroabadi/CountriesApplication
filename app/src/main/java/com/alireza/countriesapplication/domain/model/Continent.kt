package com.alireza.countriesapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Continent(
    val name: String?,
    val code: String?,
) : Parcelable
