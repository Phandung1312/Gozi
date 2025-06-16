package com.app.gozi.sport.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.gozi.sport.presentation.SportScreen

const val SPORT_ROUTE = "sport"

fun NavController.navigateToSport() {
    navigate(SPORT_ROUTE)
}

fun NavGraphBuilder.sportScreen(
    onBackPressed: () -> Unit
) {
    composable(route = SPORT_ROUTE) {
        SportScreen(
            onBackPressed = onBackPressed
        )
    }
}
