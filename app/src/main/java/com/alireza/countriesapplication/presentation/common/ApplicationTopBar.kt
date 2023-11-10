package com.alireza.countriesapplication.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alireza.countriesapplication.R
import com.alireza.countriesapplication.presentation.navigation.ScreenRouts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationTopBar(
    currentScreen: ScreenRouts,
    canNavigateBack:Boolean,
    navigateUp:()->Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(id = currentScreen.title))},
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                        id = R.string.back_button
                    ))
                }
            }
        }
    )
}