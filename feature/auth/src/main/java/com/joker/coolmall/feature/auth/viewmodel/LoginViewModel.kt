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
 * 账号密码登录ViewModel
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: AppNavigator
) : ViewModel() {

    /**
     * 登录UI状态
     */
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val uiState: StateFlow<AuthUiState> = _uiState

    /**
     * 账号输入
     */
    private val _account = MutableStateFlow("")
    val account: StateFlow<String> = _account

    /**
     * 密码输入
     */
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    /**
     * 更新账号输入
     */
    fun updateAccount(value: String) {
        _account.value = value
    }

    /**
     * 更新密码输入
     */
    fun updatePassword(value: String) {
        _password.value = value
    }

    /**
     * 执行登录操作
     */
    fun login() {
        // 此处仅为空实现，实际项目中需要调用登录API
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            // TODO: 实现实际登录逻辑
        }
    }

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
} 