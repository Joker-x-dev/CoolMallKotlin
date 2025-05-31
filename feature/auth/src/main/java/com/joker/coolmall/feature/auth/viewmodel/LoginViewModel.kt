package com.joker.coolmall.feature.auth.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.AuthRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 登录主页 ViewModel
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState
) : BaseViewModel(
    navigator = navigator,
    appState = appState
) {
    /**
     * 导航到短信登录页面
     */
    fun toSMSLoginPage() {
        super.toPage(AuthRoutes.SMS_LOGIN)
    }

    /**
     * 导航到账号密码登录页面
     */
    fun toAccountLoginPage() {
        super.toPage(AuthRoutes.ACCOUNT_LOGIN)
    }
}