package com.alireza.countriesapplication.presentation.navigation

import androidx.annotation.StringRes
import com.alireza.countriesapplication.R
import com.alireza.countriesapplication.presentation.countryInfo.navigation.countryIdArg
import com.alireza.countriesapplication.presentation.countryList.navigation.continentIdArg

enum class ScreenRouts(
    val rout: String,
    @StringRes val title: Int,
    val showToolbar: Boolean = true
) {
    ContinentList(rout = "ContinentList", R.string.screen_title_continent_list),
    CountryList(rout = "CountryList/{$continentIdArg}",R.string.screen_title_country_list),
    CountryInfo(rout = "CountryInfo/{$countryIdArg}", R.string.screen_title_country_info);

    companion object{
        fun findByRout(rout: String):ScreenRouts?{
            return when(rout){
                ContinentList.rout -> ContinentList
                CountryList.rout -> CountryList
                CountryInfo.rout -> CountryInfo
                else ->null
            }
        }
    }
}