package com.joker.coolmall.feature.auth.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.AuthRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.model.entity.Captcha
import com.joker.coolmall.core.util.notification.NotificationUtil
import com.joker.coolmall.core.util.storage.MMKVUtils
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
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 短信登录ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class SmsLoginViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val authRepository: AuthRepository,
    @param:ApplicationContext private val context: Context
) : BaseViewModel(
    navigator = navigator,
    appState = appState
) {

    companion object {
        private const val KEY_SAVED_PHONE = "saved_phone"
    }

    /**
     * 手机号输入
     */
    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    init {
        // 加载已保存的手机号
        loadSavedPhone()
    }

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
    val isPhoneValid = _phone.combine(_phone) { phone, _ ->
        ValidationUtil.isValidPhone(phone)
    }

    /**
     * 登录按钮是否可用
     */
    val isLoginEnabled = _phone.combine(_verificationCode) { phone, code ->
        ValidationUtil.isValidPhone(phone) && ValidationUtil.isValidSmsCode(code)
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
     * 更新手机号输入
     *
     * @param value 手机号值
     * @author Joker.X
     */
    fun updatePhone(value: String) {
        _phone.value = value
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
     * 执行短信登录操作
     *
     * @author Joker.X
     */
    fun login() {
        // 再次验证手机号和验证码是否有效
        if (!ValidationUtil.isValidPhone(_phone.value)) {
            ToastUtils.showError(R.string.invalid_phone_number)
            return
        }

        if (!ValidationUtil.isValidSmsCode(_verificationCode.value)) {
            ToastUtils.showError(R.string.invalid_verification_code)
            return
        }

        val params = mapOf(
            "phone" to phone.value,
            "smsCode" to verificationCode.value
        )

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = authRepository.loginByPhone(params).asResult(),
            onData = { authData -> loginSuccess(authData) }
        )
    }

    /**
     * 登录成功
     *
     * @param authData 认证数据
     * @author Joker.X
     */
    fun loginSuccess(authData: Auth) {
        viewModelScope.launch {
            // 保存手机号
            savePhone(_phone.value)

            ToastUtils.showSuccess(R.string.login_success)
            appState.updateAuth(authData)
            appState.refreshUserInfo()
            super.navigateBack()
            super.navigateBack()
        }
    }

    /**
     * 加载已保存的手机号
     *
     * @author Joker.X
     */
    private fun loadSavedPhone() {
        val savedPhone = MMKVUtils.getString(KEY_SAVED_PHONE, "")
        if (savedPhone.isNotEmpty()) {
            _phone.value = savedPhone
        }
    }

    /**
     * 保存手机号
     *
     * @param phone 手机号
     * @author Joker.X
     */
    private fun savePhone(phone: String) {
        MMKVUtils.putString(KEY_SAVED_PHONE, phone)
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