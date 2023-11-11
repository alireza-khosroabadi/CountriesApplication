package com.alireza.countriesapplication.presentation.navigation

import androidx.annotation.StringRes
import com.alireza.countriesapplication.R
import com.alireza.countriesapplication.presentation.country.navigation.continentIdArg
import com.alireza.countriesapplication.presentation.country.navigation.continentNameArg

enum class ScreenRouts(val rout:String, @StringRes val title: Int, val needFab:Boolean = false) {
    ContinentList(rout = "ContinentList", R.string.screen_title_continent_list, true),
    CountryList(rout = "CountryList/{$continentNameArg}/{$continentIdArg}",R.string.screen_title_country_list);

    companion object{
        fun findByRout(rout: String):ScreenRouts?{
            return when(rout){
                ContinentList.rout -> ContinentList
                CountryList.rout -> CountryList
                else ->null
            }
        }
    }
}