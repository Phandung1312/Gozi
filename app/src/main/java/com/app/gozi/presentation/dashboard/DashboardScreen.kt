package com.app.gozi.presentation.dashboard

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
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
import com.app.gozi.sport.presentation.SportScreen



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
    
    // Danh sách các route có BottomNavigation
    val routesWithBottomNav = remember {
        setOf(
            BottomNavItem.Home.route,
            BottomNavItem.Group.route,
            BottomNavItem.Notification.route,
            BottomNavItem.Profile.route
        )
    }
    
    // Kiểm tra xem route hiện tại có cần hiển thị BottomNavigation không
    val shouldShowBottomNav = currentRoute in routesWithBottomNav
    
    Log.d("DashboardScreen", "Current route: $currentRoute, shouldShowBottomNav: $shouldShowBottomNav")
    
    Scaffold(
        modifier = modifier.fillMaxSize(),        bottomBar = {
            // Sử dụng AnimatedVisibility để tạo animation trượt lên/xuống
            AnimatedVisibility(
                visible = shouldShowBottomNav,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight }, // Bắt đầu từ dưới màn hình
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 300,
                        delayMillis = 50,
                        easing = EaseInOutCubic
                    )
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight }, // Trượt xuống dưới màn hình
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = EaseInOutCubic
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 200
                    )
                )
            ) {
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
        }    ) { paddingValues ->
        NavigationHost(
            navController = navController,
            paddingValues = paddingValues,
            shouldShowBottomNav = shouldShowBottomNav
        )
    }
}

@Composable
private fun NavigationHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    shouldShowBottomNav: Boolean,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current
    
    // Điều chỉnh padding: giữ top, start, end nhưng chỉ bỏ bottom khi không có bottom nav
    val contentPadding = if (shouldShowBottomNav) {
        paddingValues
    } else {
        PaddingValues(
            top = paddingValues.calculateTopPadding(),
            start = paddingValues.calculateStartPadding(layoutDirection),
            end = paddingValues.calculateEndPadding(layoutDirection),
            bottom = 0.dp // Chỉ bỏ bottom padding
        )
    }
    
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier.padding(contentPadding)
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(
                onFoodFeatureClick = {
                    navController.navigate("food_screen")
                },
                onSportFeatureClick = {
                    navController.navigate("sport_screen")
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
        composable("sport_screen") {
            SportScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}
