package com.joker.coolmall.feature.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.AuthRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.model.request.QQLoginRequest
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.AuthRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 登录主页 ViewModel
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val authRepository: AuthRepository
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

    /**
     * QQ 授权成功
     */
    fun qqAuthSuccess() {
        val params = QQLoginRequest(
            accessToken = "",
            openId = ""
        )
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = authRepository.loginByQqApp(params).asResult(),
            onData = { authData -> loginSuccess(authData) }
        )
    }

    /**
     * 登录成功统一处理
     * @param authData 登录成功返回的认证数据
     */
    fun loginSuccess(authData: Auth) {
        viewModelScope.launch {
            ToastUtils.showSuccess("登录成功")
            appState.updateAuth(authData)
            appState.refreshUserInfo()
            super.navigateBack()
        }
    }
}