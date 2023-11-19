package com.alireza.countriesapplication.presentation.countryInfo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alireza.uisystem.common.LoadingItem

@Composable
fun CountryInfoScreen(state: CountryInfoState) {
    
    Box(modifier = Modifier.fillMaxSize()) {
        
        AnimatedContent(
            modifier = Modifier,
            targetState = state,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "Animated Content"
            ) {targetState ->
            if (targetState.isLoading){
                LoadingItem()
            }else{
                Text(text = targetState.countryInfo?.name.toString())
            }
        }
    }
}