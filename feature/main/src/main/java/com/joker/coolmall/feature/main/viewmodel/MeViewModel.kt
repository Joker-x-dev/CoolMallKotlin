package com.joker.coolmall.feature.main.viewmodel

import User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.AuthRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 我的页面视图模型
 */
@HiltViewModel
class MeViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val appState: AppState
) : ViewModel() {

    // 用户登录状态
    val isLoggedIn: StateFlow<Boolean> = appState.isLoggedIn
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    // 用户信息
    val userInfo: StateFlow<User?> = appState.userInfo
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        // 如果已登录但没有用户信息，则刷新用户信息
        viewModelScope.launch {
            if (isLoggedIn.value && userInfo.value == null) {
                appState.refreshUserInfo()
            }
        }
    }

    /**
     * 跳转到登录页
     */
    fun toLogin() {
        viewModelScope.launch {
            navigator.navigateTo(AuthRoutes.HOME)
        }
    }
}
