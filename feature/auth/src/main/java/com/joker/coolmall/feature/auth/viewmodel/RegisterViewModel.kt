package com.joker.coolmall.feature.auth.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.AuthRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.model.entity.Captcha
import com.joker.coolmall.core.util.notification.NotificationUtil
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.core.util.validation.ValidationUtil
import com.joker.coolmall.feature.auth.R
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.CommonRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 用户注册ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val authRepository: AuthRepository,
    @param:ApplicationContext private val context: Context
) : BaseViewModel(
    navigator = navigator,
    appState = appState
) {

    /**
     * 手机号输入
     */
    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    /**
     * 密码输入
     */
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    /**
     * 确认密码输入
     */
    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    /**
     * 验证码输入
     */
    private val _verificationCode = MutableStateFlow("")
    val verificationCode: StateFlow<String> = _verificationCode

    /**
     * 图片验证码 Popup 是否展示
     */
    private val _showImageCodePopup = MutableStateFlow(false)
    val showImageCodePopup: StateFlow<Boolean> = _showImageCodePopup

    /**
     * 图片验证码
     */
    private val _captcha = MutableStateFlow(Captcha())
    val captcha: StateFlow<Captcha> = _captcha

    /**
     * 图形验证码输入
     */
    private val _imageCode = MutableStateFlow("")
    val imageCode: StateFlow<String> = _imageCode

    /**
     * 验证码加载状态
     */
    private val _isLoadingCaptcha = MutableStateFlow(false)
    val isLoadingCaptcha: StateFlow<Boolean> = _isLoadingCaptcha

    /**
     * 手机号是否有效
     */
    val isPhoneValid = _phone.map { phone ->
        ValidationUtil.isValidPhone(phone)
    }

    /**
     * 注册按钮是否可用
     */
    val isRegisterEnabled = combine(
        _phone,
        _verificationCode,
        _password,
        _confirmPassword
    ) { phone, code, password, confirmPassword ->
        ValidationUtil.isValidPhone(phone) &&
                ValidationUtil.isValidSmsCode(code) &&
                ValidationUtil.isValidPassword(password) &&
                password == confirmPassword
    }

    /**
     * 更新手机号输入
     *
     * @param value 手机号值
     * @author Joker.X
     */
    fun updatePhone(value: String) {
        _phone.value = value
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
     * 更新确认密码输入
     *
     * @param value 确认密码值
     * @author Joker.X
     */
    fun updateConfirmPassword(value: String) {
        _confirmPassword.value = value
    }

    /**
     * 更新验证码输入
     *
     * @param value 验证码值
     * @author Joker.X
     */
    fun updateVerificationCode(value: String) {
        _verificationCode.value = value
    }

    /**
     * 更新图形验证码输入
     *
     * @param value 图形验证码值
     * @author Joker.X
     */
    fun updateImageCode(value: String) {
        _imageCode.value = value
    }

    /**
     * 显示图片验证码 Popup
     * 在显示之前会先刷新验证码
     *
     * @author Joker.X
     */
    fun onSendCodeButtonClick() {
        // 检查手机号是否有效
        if (!ValidationUtil.isValidPhone(_phone.value)) {
            ToastUtils.showError(R.string.invalid_phone_number)
            return
        }

        viewModelScope.launch {
            _isLoadingCaptcha.value = true
            fetchCaptcha()
            _isLoadingCaptcha.value = false
            _showImageCodePopup.value = true
        }
    }

    /**
     * 隐藏图片验证码 Popup
     *
     * @author Joker.X
     */
    fun onHideImageCodePopup() {
        _showImageCodePopup.value = false
        // 清空图形验证码输入
        _imageCode.value = ""
    }

    /**
     * 验证码确认
     * 当用户在图形验证码对话框中点击确认按钮时调用
     *
     * @param imageCode 图形验证码
     * @author Joker.X
     */
    fun onImageCodeConfirm(imageCode: String) {
        updateImageCode(imageCode)
        sendVerificationCode()
    }

    /**
     * 发送短信验证码
     *
     * @author Joker.X
     */
    fun sendVerificationCode() {
        val currentImageCode = imageCode.value
        onHideImageCodePopup()

        val params = mapOf(
            "phone" to phone.value,
            "captchaId" to captcha.value.captchaId,
            "code" to currentImageCode
        )

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = authRepository.getSmsCode(params).asResult(),
            onData = { smsCode ->
                // 成功获取验证码，发送通知
                NotificationUtil.sendVerificationCodeNotification(
                    context = context,
                    code = smsCode
                )
            }
        )
    }

    /**
     * 获取图片验证码
     * 当需要刷新验证码时调用（如用户点击验证码图片）
     *
     * @author Joker.X
     */
    fun getCaptcha() {
        viewModelScope.launch {
            _isLoadingCaptcha.value = true
            fetchCaptcha()
            _isLoadingCaptcha.value = false
        }
    }

    /**
     * 实际获取验证码的网络请求
     *
     * @author Joker.X
     */
    private fun fetchCaptcha() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = authRepository.getCaptcha().asResult(),
            onData = { captcha ->
                _captcha.value = captcha
            }
        )
    }

    /**
     * 执行注册操作
     *
     * @author Joker.X
     */
    fun register() {
        // 验证手机号
        if (!ValidationUtil.isValidPhone(_phone.value)) {
            ToastUtils.showError(R.string.invalid_phone_number)
            return
        }

        // 验证验证码
        if (!ValidationUtil.isValidSmsCode(_verificationCode.value)) {
            ToastUtils.showError(R.string.invalid_verification_code)
            return
        }

        // 验证密码
        if (!ValidationUtil.isValidPassword(_password.value)) {
            ToastUtils.showError(R.string.invalid_password)
            return
        }

        // 验证确认密码
        if (_password.value != _confirmPassword.value) {
            ToastUtils.showError(R.string.password_mismatch)
            return
        }

        val params = mapOf(
            "phone" to phone.value,
            "smsCode" to verificationCode.value,
            "password" to password.value,
            "confirmPassword" to confirmPassword.value
        )

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = authRepository.register(params).asResult(),
            onData = { authData -> registerSuccess(authData) }
        )
    }

    /**
     * 注册成功
     *
     * @param authData 认证数据
     * @author Joker.X
     */
    private fun registerSuccess(authData: Auth) {
        viewModelScope.launch {
            ToastUtils.showSuccess(R.string.register_success)
            appState.updateAuth(authData)
            appState.refreshUserInfo()
            super.navigateBack()
        }
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