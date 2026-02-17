package com.joker.coolmall.feature.auth.viewmodel

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.common.manager.QQLoginManager
import com.joker.coolmall.core.common.manager.QQLoginResult
import com.joker.coolmall.core.data.repository.AuthRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.model.request.QQLoginRequest
import com.joker.coolmall.core.navigation.NavigationService.navigateBack
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.auth.R
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 登录主页 ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appState: AppState,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    /**
     * 启动 QQ 登录
     *
     * @param activity 当前 Activity 实例
     * @author Joker.X
     */
    fun startQQLogin(activity: Activity) {
        try {
            // 启动 QQ 登录
            QQLoginManager.getInstance().startQQLogin(activity)

            // 监听登录结果
            viewModelScope.launch {
                QQLoginManager.getInstance().loginResult.collect { result ->
                    when (result) {
                        is QQLoginResult.Success -> {
                            // QQ 登录成功，调用后端登录接口
                            qqLoginSuccess(result.accessToken, result.openId)
                            // 清除登录结果
                            QQLoginManager.getInstance().clearLoginResult()
                        }

                        is QQLoginResult.Error -> {
                            // QQ 登录失败
                            ToastUtils.showError(R.string.login_failed)
                            QQLoginManager.getInstance().clearLoginResult()
                        }

                        is QQLoginResult.Cancel -> {
                            // 用户取消登录
                            ToastUtils.showWarning(R.string.login_cancelled)
                            QQLoginManager.getInstance().clearLoginResult()
                        }

                        else -> {}
                    }
                }
            }
        } catch (_: Exception) {
            ToastUtils.showError(R.string.start_qq_login_failed)
        }
    }

    /**
     * QQ 登录成功
     *
     * @param accessToken QQ 登录成功返回的 accessToken
     * @param openId QQ 登录成功返回的 openId
     * @author Joker.X
     */
    private fun qqLoginSuccess(accessToken: String, openId: String) {
        val params = QQLoginRequest(
            accessToken = accessToken,
            openId = openId
        )
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = authRepository.loginByQqApp(params).asResult(),
            onData = { authData -> loginSuccess(authData) }
        )
    }

    /**
     * 登录成功统一处理
     *
     * @param authData 登录成功返回的认证数据
     * @author Joker.X
     */
    fun loginSuccess(authData: Auth) {
        viewModelScope.launch {
            ToastUtils.showSuccess(R.string.login_success)
            appState.updateAuth(authData)
            appState.refreshUserInfo()
            navigateBack()
        }
    }

    /**
     * 微信登录点击
     *
     * @author Joker.X
     */
    fun onWechatLoginClick() {
        onWechatAndAlipayLoginTipClick()
    }

    /**
     * 支付宝登录点击
     *
     * @author Joker.X
     */
    fun onAlipayLoginClick() {
        onWechatAndAlipayLoginTipClick()
    }

    /**
     * 微信和支付宝登录提示
     *
     * @author Joker.X
     */
    fun onWechatAndAlipayLoginTipClick() {
        ToastUtils.showWarning(R.string.third_party_login_only_qq)
    }

}
