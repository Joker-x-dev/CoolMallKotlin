package com.joker.coolmall.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 短信登录ViewModel
 */
@HiltViewModel
class SmsLoginViewModel @Inject constructor(
    private val navigator: AppNavigator
) : ViewModel() {


    /**
     * 手机号输入
     */
    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    /**
     * 验证码输入
     */
    private val _verificationCode = MutableStateFlow("")
    val verificationCode: StateFlow<String> = _verificationCode

    /**
     * 更新手机号输入
     */
    fun updatePhone(value: String) {
        _phone.value = value
    }

    /**
     * 更新验证码输入
     */
    fun updateVerificationCode(value: String) {
        _verificationCode.value = value
    }

    /**
     * 发送验证码
     */
    fun sendVerificationCode() {
        // 此处仅为空实现，实际项目中需要调用发送验证码API
        viewModelScope.launch {
            // TODO: 实现发送验证码逻辑
        }
    }

    /**
     * 执行短信登录操作
     */
    fun login() {
        // 此处仅为空实现，实际项目中需要调用登录API
        viewModelScope.launch {
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