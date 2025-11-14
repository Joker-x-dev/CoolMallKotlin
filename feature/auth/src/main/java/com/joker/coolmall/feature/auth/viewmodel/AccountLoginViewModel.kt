package com.joker.coolmall.feature.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.AuthRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.core.util.validation.ValidationUtil
import com.joker.coolmall.feature.auth.R
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.AuthRoutes
import com.joker.coolmall.navigation.routes.CommonRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 账号密码登录ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class AccountLoginViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val authRepository: AuthRepository,
) : BaseViewModel(
    navigator = navigator,
    appState = appState
) {

    companion object {
        private const val KEY_SAVED_PHONE = "saved_phone"
        private const val KEY_SAVED_PASSWORD = "saved_password"
    }

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

    init {
        // 加载已保存的账号和密码
        loadSavedCredentials()
    }

    /**
     * 登录按钮是否可用
     */
    val isLoginEnabled = _account.combine(_password) { account, password ->
        ValidationUtil.isValidPhone(account) && ValidationUtil.isValidPassword(password)
    }

    /**
     * 更新账号输入
     *
     * @param value 账号值
     * @author Joker.X
     */
    fun updateAccount(value: String) {
        _account.value = value
    }

    /**
     * 更新密码输入
     *
     * @param value 密码值
     * @author Joker.X
     */
    fun updatePassword(value: String) {
        _password.value = value
    }

    /**
     * 执行登录操作
     *
     * @author Joker.X
     */
    fun login() {
        // 验证手机号
        if (!ValidationUtil.isValidPhone(_account.value)) {
            ToastUtils.showError(R.string.invalid_phone_number)
            return
        }

        // 验证密码
        if (!ValidationUtil.isValidPassword(_password.value)) {
            ToastUtils.showError(R.string.invalid_password)
            return
        }

        val params = mapOf(
            "phone" to account.value,
            "password" to password.value
        )

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = authRepository.loginByPassword(params).asResult(),
            onData = { authData -> loginSuccess(authData) }
        )
    }

    /**
     * 登录成功
     *
     * @param authData 认证数据
     * @author Joker.X
     */
    private fun loginSuccess(authData: Auth) {
        viewModelScope.launch {
            // 保存账号和密码
            saveCredentials(_account.value, _password.value)

            ToastUtils.showSuccess(R.string.login_success)
            appState.updateAuth(authData)
            appState.refreshUserInfo()
            super.navigateBack()
            super.navigateBack()
        }
    }

    /**
     * 加载已保存的凭据
     *
     * @author Joker.X
     */
    private fun loadSavedCredentials() {
        val savedPhone = MMKVUtils.getString(KEY_SAVED_PHONE, "")
        val savedPassword = MMKVUtils.getString(KEY_SAVED_PASSWORD, "")

        if (savedPhone.isNotEmpty()) {
            _account.value = savedPhone
        }
        if (savedPassword.isNotEmpty()) {
            _password.value = savedPassword
        }
    }

    /**
     * 保存凭据
     *
     * @param phone 手机号
     * @param password 密码
     * @author Joker.X
     */
    private fun saveCredentials(phone: String, password: String) {
        MMKVUtils.putString(KEY_SAVED_PHONE, phone)
        MMKVUtils.putString(KEY_SAVED_PASSWORD, password)
    }

    /**
     * 导航到注册页面
     *
     * @author Joker.X
     */
    fun toRegisterPage() {
        navigate(AuthRoutes.Register)
    }

    /**
     * 导航到重置密码页面
     *
     * @author Joker.X
     */
    fun toResetPasswordPage() {
        navigate(AuthRoutes.ResetPassword)
    }

    /**
     * 点击用户协议
     * 显示用户使用协议
     *
     * @author Joker.X
     */
    fun onUserAgreementClick() {
        navigate(CommonRoutes.UserAgreement)
    }

    /**
     * 点击隐私政策
     * 显示隐私政策内容
     *
     * @author Joker.X
     */
    fun onPrivacyPolicyClick() {
        navigate(CommonRoutes.PrivacyPolicy)
    }
}