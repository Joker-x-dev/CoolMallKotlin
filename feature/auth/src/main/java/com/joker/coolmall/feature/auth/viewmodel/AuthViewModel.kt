package com.joker.coolmall.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.feature.auth.state.AuthUiState
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 认证模块基础ViewModel
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val navigator: AppNavigator
) : ViewModel() {

    /**
     * 认证UI状态
     */
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val uiState: StateFlow<AuthUiState> = _uiState

    /**
     * 导航回上一页
     */
    fun navigateBack() {
        viewModelScope.launch {
            navigator.navigateBack()
        }
    }

    /**
     * 导航到指定路由
     */
    fun navigateTo(route: String) {
        viewModelScope.launch {
            navigator.navigateTo(route)
        }
    }

    /**
     * 重置UI状态到初始状态
     */
    fun resetState() {
        _uiState.value = AuthUiState.Initial
    }
} 