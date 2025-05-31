package com.joker.coolmall.feature.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.AuthRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 账号密码登录ViewModel
 */
@HiltViewModel
class AccountLoginViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState
) : BaseViewModel(
    navigator = navigator,
    appState = appState
) {

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
            // TODO: 实现实际登录逻辑
        }
    }

    /**
     * 导航到注册页面
     */
    fun toRegisterPage() {
        super.toPage(AuthRoutes.REGISTER)
    }

    /**
     * 导航到重置密码页面
     */
    fun toResetPasswordPage() {
        super.toPage(AuthRoutes.RESET_PASSWORD)
    }
}