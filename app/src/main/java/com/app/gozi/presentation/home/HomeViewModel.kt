package com.app.gozi.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    
    private val _isFloatingButtonVisible = MutableStateFlow(true)
    val isFloatingButtonVisible: StateFlow<Boolean> = _isFloatingButtonVisible.asStateFlow()
    
    private val _showMessageBox = MutableStateFlow(false)
    val showMessageBox: StateFlow<Boolean> = _showMessageBox.asStateFlow()
    
    private val _animatedText = MutableStateFlow("")
    val animatedText: StateFlow<String> = _animatedText.asStateFlow()
    
    private val fullMessage = "Xin chào! Tôi là AI Assistant của bạn. Hãy nhấn vào tôi để bắt đầu trò chuyện nhé! 🤖✨"
    
    private var autoHideJob: Job? = null
    
    init {
        showInitialMessage()
    }    fun onFloatingButtonClick() {
        autoHideJob?.cancel() // Hủy auto hide nếu user click
        
        if (!_showMessageBox.value) {
            _showMessageBox.value = true
            startTextAnimation()
            startAutoHideTimer() // Bắt đầu timer mới
        } else {
            hideMessageBox()
        }
    }    private fun showInitialMessage() {
        viewModelScope.launch {
            delay(5000) // Tăng delay lên 1.5s để có thời gian cho màn hình load hoàn toàn
            _showMessageBox.value = true
            startTextAnimation()
            startAutoHideTimer() // Bắt đầu timer auto hide
        }
    }
    
    private fun startAutoHideTimer() {
        autoHideJob?.cancel() // Hủy timer cũ nếu có
        autoHideJob = viewModelScope.launch {
            delay(10000) // Đợi 5 giây
            hideMessageBox()
        }
    }
      private fun startTextAnimation() {
        viewModelScope.launch {
            _animatedText.value = ""
            delay(500) // Tăng delay cho slide animation hoàn thành
            
            for (i in fullMessage.indices) {
                _animatedText.value = fullMessage.substring(0, i + 1)
                delay(40) // Giảm delay giữa các ký tự để mượt hơn
            }
        }
    }
      private fun hideMessageBox() {
        autoHideJob?.cancel() // Hủy timer khi ẩn manual
        _showMessageBox.value = false
        _animatedText.value = ""
    }
}
