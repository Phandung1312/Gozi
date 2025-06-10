package com.app.gozi.presentation.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.gozi.presentation.dashboard.DashboardScreen
import com.app.ui.theme.GoziTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val mainViewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen before calling super.onCreate()
        val splashScreen = installSplashScreen()
        
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
          // Keep the splash screen on-screen until the app is ready
        splashScreen.setKeepOnScreenCondition {
            !mainViewModel.isReadyFlow.value
        }
          // Set the splash screen exit animation with zoom out effect
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Create a simple zoom out animation for the icon
            val iconScaleOutX = ObjectAnimator.ofFloat(
                splashScreenView.iconView,
                "scaleX",
                1f,
                0f
            )
            val iconScaleOutY = ObjectAnimator.ofFloat(
                splashScreenView.iconView,
                "scaleY",
                1f,
                0f
            )
            
            iconScaleOutX.duration = 300L
            iconScaleOutY.duration = 300L
            iconScaleOutX.interpolator = AnticipateInterpolator()
            iconScaleOutY.interpolator = AnticipateInterpolator()
            
            // Remove the splash screen when animation completes
            iconScaleOutX.doOnEnd { splashScreenView.remove() }
            
            // Start the zoom out animation
            iconScaleOutX.start()
            iconScaleOutY.start()
        }
          setContent {
            GoziTheme {
                MainContent(mainViewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun MainContent(mainViewModel: MainViewModel) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    
    // Main app content - always show once splash is ready
//    DashboardScreen()
}