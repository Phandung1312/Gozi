package com.app.gozi.presentation.dashboard

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.gozi.presentation.components.AnimatedBottomNavigation
import com.app.gozi.presentation.navigation.BottomNavItem
import com.app.gozi.presentation.group.GroupScreen
import com.app.gozi.home.presentation.HomeScreen
import com.app.gozi.presentation.notification.NotificationScreen
import com.app.gozi.presentation.profile.ProfileScreen
import com.app.gozi.food.presentation.FoodScreen

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomNavItem.Home.route
    val bottomNavItems = remember {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Group,
            BottomNavItem.Notification,
            BottomNavItem.Profile
        )
    }
    Log.d("DashboardScreen", "Current route: $currentRoute")
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            AnimatedBottomNavigation(
                items = bottomNavItems,
                selectedItem = currentRoute,
                onItemClick = { item ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavigationHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(
                onFoodFeatureClick = {
                    navController.navigate("food_screen")
                }
            )
        }
        composable(BottomNavItem.Group.route) {
            GroupScreen()
        }
        composable(BottomNavItem.Notification.route) {
            NotificationScreen()
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
        }
        composable("food_screen") {
            FoodScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}
