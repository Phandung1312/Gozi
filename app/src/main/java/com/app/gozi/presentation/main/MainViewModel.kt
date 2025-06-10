package com.app.gozi.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    val isLoading: Boolean = true,
    val isReady: Boolean = false,
    val userName: String = "Android",
    val initializationProgress: Int = 0,
    val currentTask: String = "Initializing..."
)

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    // Splash screen state flows
    private val _isReadyFlow = MutableStateFlow(false)
    val isReadyFlow: StateFlow<Boolean> = _isReadyFlow.asStateFlow()
    
    init {
        initializeApp()
    }
    
    private fun initializeApp() {
        viewModelScope.launch {
            updateState { 
                it.copy(
                    currentTask = "Starting application...",
                    initializationProgress = 10
                )
            }
            delay(500)
              // Simulate initialization tasks
            val tasks = listOf(
                "Loading user preferences..." to 30,
                "Checking authentication..." to 50,
                "Initializing services..." to 70,
                "Preparing UI components..." to 90,
                "Finalizing setup..." to 100
            )
            
            tasks.forEach { (taskName, progress) ->
                updateState { 
                    it.copy(
                        currentTask = taskName,
                        initializationProgress = progress
                    )
                }
                delay(350) // Slightly faster to sync with animations
            }
            
            // Mark initialization as complete
            updateState { 
                it.copy(
                    isLoading = false,
                    isReady = true,
                    currentTask = "Ready!",
                    userName = "Gozi User"
                )
            }
            
            // Update splash screen state
            _isReadyFlow.value = true
        }
    }
    
    private fun updateState(update: (MainUiState) -> MainUiState) {
        _uiState.value = update(_uiState.value)
    }
}
