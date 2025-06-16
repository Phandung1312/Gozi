package com.app.gozi.food.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gozi.food.domain.model.FoodItem
import com.app.gozi.food.domain.usecase.GetAllFoodItemsUseCase
import com.app.gozi.food.domain.usecase.SearchFoodItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FoodScreenState(
    val foodItems: List<FoodItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val getAllFoodItemsUseCase: GetAllFoodItemsUseCase,
    private val searchFoodItemsUseCase: SearchFoodItemsUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow(FoodScreenState())
    val state: StateFlow<FoodScreenState> = _state.asStateFlow()
    
    private var searchJob: Job? = null
    
    init {
        loadFoodItems()
    }
    
    fun loadFoodItems() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            getAllFoodItemsUseCase()
                .catch { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "An unknown error occurred"
                    )
                }
                .collect { foodItems ->
                    _state.value = _state.value.copy(
                        foodItems = foodItems,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }
    
    fun searchFoodItems(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        
        searchJob?.cancel()
        
        if (query.isBlank()) {
            loadFoodItems()
            return
        }
        
        searchJob = viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            searchFoodItemsUseCase(query)
                .catch { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Search failed"
                    )
                }
                .collect { foodItems ->
                    _state.value = _state.value.copy(
                        foodItems = foodItems,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }
    
    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
