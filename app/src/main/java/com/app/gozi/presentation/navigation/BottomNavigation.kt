package com.app.gozi.presentation.navigation

import androidx.annotation.DrawableRes

sealed class BottomNavItem(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    data object Home : BottomNavItem(
        route = "home",
        title = "Home",
        icon = com.app.gozi.R.drawable.home
    )

    data object Group : BottomNavItem(
        route = "group",
        title = "Group",
        icon = com.app.gozi.R.drawable.group
    )
    
    data object Notification : BottomNavItem(
        route = "notification",
        title = "Notification",
        icon = com.app.gozi.R.drawable.notification
    )
    
    data object Profile : BottomNavItem(
        route = "profile",
        title = "Profile",
        icon = com.app.gozi.R.drawable.profile
    )

}
