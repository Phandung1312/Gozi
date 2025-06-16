package com.app.gozi.sport.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gozi.sport.domain.model.SportItem
import com.app.gozi.sport.domain.usecase.GetAllSportItemsUseCase
import com.app.gozi.sport.domain.usecase.SearchSportItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SportScreenState(
    val sportItems: List<SportItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class SportViewModel @Inject constructor(
    private val getAllSportItemsUseCase: GetAllSportItemsUseCase,
    private val searchSportItemsUseCase: SearchSportItemsUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow(SportScreenState())
    val state: StateFlow<SportScreenState> = _state.asStateFlow()
    
    private var searchJob: Job? = null
    
    init {
        loadSportItems()
    }
    
    fun loadSportItems() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            getAllSportItemsUseCase()
                .catch { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "An unknown error occurred"
                    )
                }
                .collect { sportItems ->
                    _state.value = _state.value.copy(
                        sportItems = sportItems,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }
    
    fun searchSportItems(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        
        searchJob?.cancel()
        
        if (query.isBlank()) {
            loadSportItems()
            return
        }
        
        searchJob = viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            searchSportItemsUseCase(query)
                .catch { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Search failed"
                    )
                }
                .collect { sportItems ->
                    _state.value = _state.value.copy(
                        sportItems = sportItems,
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
