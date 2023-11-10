package com.alireza.countriesapplication.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alireza.countriesapplication.presentation.common.ApplicationTopBar
import com.alireza.countriesapplication.presentation.navigation.AppNavGraph
import com.alireza.countriesapplication.presentation.navigation.ScreenRouts

@Composable
fun HomeAppScreen(navController: NavHostController = rememberNavController()) {
    // Get current back stack entry
    val backStackEntry = navController.currentBackStackEntry

    // Get the name of the current screen
    val currentScreen = ScreenRouts.findByRout(
        backStackEntry?.destination?.route ?: ScreenRouts.ContinentList.rout
    )?: ScreenRouts.ContinentList

    val snackBarHostState : SnackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            ApplicationTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavGraph(navController = navController, snackBarHostState= snackBarHostState)
        }

    }

}