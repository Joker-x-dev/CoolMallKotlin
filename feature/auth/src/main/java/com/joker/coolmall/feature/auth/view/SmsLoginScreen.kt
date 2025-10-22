package com.joker.coolmall.feature.auth.view

import android.app.Activity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.model.entity.Captcha
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.button.ButtonShape
import com.joker.coolmall.core.util.permission.PermissionUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.auth.R
import com.joker.coolmall.feature.auth.component.AnimatedAuthPage
import com.joker.coolmall.feature.auth.component.BottomNavigationRow
import com.joker.coolmall.feature.auth.component.ImageCaptchaDialog
import com.joker.coolmall.feature.auth.component.PhoneInputField
import com.joker.coolmall.feature.auth.component.UserAgreement
import com.joker.coolmall.feature.auth.component.VerificationCodeField
import com.joker.coolmall.feature.auth.viewmodel.SmsLoginViewModel


/**
 * 短信登录路由
 *
 * @param viewModel 短信登录ViewModel
 * @author Joker.X
 */
@Composable
internal fun SmsLoginRoute(
    viewModel: SmsLoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    // 收集手机号输入
    val phone by viewModel.phone.collectAsState()
    // 收集验证码输入
    val verificationCode by viewModel.verificationCode.collectAsState()
    // 收集图片验证码弹窗显示状态
    val showImageCodePopup by viewModel.showImageCodePopup.collectAsState()
    // 收集图片验证码数据
    val captcha by viewModel.captcha.collectAsState()
    // 收集验证码加载状态
    val isLoadingCaptcha by viewModel.isLoadingCaptcha.collectAsState()
    // 收集手机号验证状态
    val isPhoneValid by viewModel.isPhoneValid.collectAsState(initial = false)
    // 收集登录按钮启用状态
    val isLoginEnabled by viewModel.isLoginEnabled.collectAsState(initial = false)

    // 包装发送验证码函数，先申请权限再发送
    val onSendVerificationCodeWithPermission = {
        if (context is Activity) {
            // 获取通知权限
            PermissionUtils.requestNotificationPermission(context) { granted ->
                if (granted) {
                    // 权限获取成功，发送验证码
                    viewModel.onSendCodeButtonClick()
                } else {
                    // 权限获取失败，提示用户
                    ToastUtils.showError(R.string.notification_permission_required)
                }
            }
        } else {
            // 如果不是 Activity Context，直接发送验证码
            viewModel.onSendCodeButtonClick()
        }
    }

    SmsLoginScreen(
        phone = phone,
        verificationCode = verificationCode,
        showImageCodePopup = showImageCodePopup,
        captcha = captcha,
        isLoadingCaptcha = isLoadingCaptcha,
        isPhoneValid = isPhoneValid,
        isLoginEnabled = isLoginEnabled,
        onHideImageCodePopup = viewModel::onHideImageCodePopup,
        onPhoneChange = viewModel::updatePhone,
        onVerificationCodeChange = viewModel::updateVerificationCode,
        onSendVerificationCode = onSendVerificationCodeWithPermission,
        onImageCodeConfirm = viewModel::onImageCodeConfirm,
        onRefreshCaptcha = viewModel::getCaptcha,
        onLoginClick = viewModel::login,
        onBackClick = viewModel::navigateBack,
        onUserAgreementClick = viewModel::onUserAgreementClick,
        onPrivacyPolicyClick = viewModel::onPrivacyPolicyClick
    )
}

/**
 * 短信登录界面
 *
 * @param phone 手机号
 * @param verificationCode 验证码
 * @param showImageCodePopup 图片验证码 Popup
 * @param captcha 图片验证码
 * @param isLoadingCaptcha 验证码加载状态
 * @param isPhoneValid 手机号是否有效
 * @param isLoginEnabled 登录按钮是否可用
 * @param onHideImageCodePopup 图片验证码 Popup 隐藏
 * @param onPhoneChange 手机号变更回调
 * @param onVerificationCodeChange 验证码变更回调
 * @param onSendVerificationCode 发送验证码按钮点击回调
 * @param onImageCodeConfirm 图形验证码确认回调
 * @param onRefreshCaptcha 刷新验证码回调
 * @param onLoginClick 登录按钮点击回调
 * @param onBackClick 返回上一页回调
 * @param onUserAgreementClick 用户协议点击回调
 * @param onPrivacyPolicyClick 隐私政策点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SmsLoginScreen(
    phone: String = "",
    verificationCode: String = "",
    showImageCodePopup: Boolean = false,
    captcha: Captcha = Captcha(),
    isLoadingCaptcha: Boolean = false,
    isPhoneValid: Boolean = false,
    isLoginEnabled: Boolean = false,
    onHideImageCodePopup: () -> Unit = {},
    onPhoneChange: (String) -> Unit = {},
    onVerificationCodeChange: (String) -> Unit = {},
    onSendVerificationCode: () -> Unit = {},
    onImageCodeConfirm: (String) -> Unit = {},
    onRefreshCaptcha: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {}
) {
    AnimatedAuthPage(
        title = stringResource(id = R.string.welcome_login),
        onBackClick = onBackClick
    ) {
        SmsLoginContentView(
            phone = phone,
            verificationCode = verificationCode,
            isPhoneValid = isPhoneValid,
            isLoginEnabled = isLoginEnabled,
            onPhoneChange = onPhoneChange,
            onVerificationCodeChange = onVerificationCodeChange,
            onSendVerificationCode = onSendVerificationCode,
            onLoginClick = onLoginClick,
            onBackClick = onBackClick,
            onUserAgreementClick = onUserAgreementClick,
            onPrivacyPolicyClick = onPrivacyPolicyClick
        )
    }

    // 使用新封装的图片验证码对话框组件
    ImageCaptchaDialog(
        visible = showImageCodePopup,
        captcha = captcha,
        onDismiss = onHideImageCodePopup,
        onConfirm = onImageCodeConfirm,
        onRefreshCaptcha = onRefreshCaptcha
    )
}

/**
 * 短信登录内容视图
 *
 * @param phone 手机号
 * @param verificationCode 验证码
 * @param isPhoneValid 手机号是否有效
 * @param isLoginEnabled 登录按钮是否可用
 * @param onPhoneChange 手机号变更回调
 * @param onVerificationCodeChange 验证码变更回调
 * @param onSendVerificationCode 发送验证码回调
 * @param onLoginClick 登录按钮点击回调
 * @param onBackClick 返回上一页回调
 * @param onUserAgreementClick 用户协议点击回调
 * @param onPrivacyPolicyClick 隐私政策点击回调
 * @author Joker.X
 */
@Composable
private fun SmsLoginContentView(
    phone: String,
    verificationCode: String,
    isPhoneValid: Boolean,
    isLoginEnabled: Boolean,
    onPhoneChange: (String) -> Unit,
    onVerificationCodeChange: (String) -> Unit,
    onSendVerificationCode: () -> Unit,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit,
    onUserAgreementClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    // 记录输入框焦点状态
    val phoneFieldFocused = remember { mutableStateOf(false) }
    val codeFieldFocused = remember { mutableStateOf(false) }

    // 使用封装的手机号输入组件
    PhoneInputField(
        phone = phone,
        onPhoneChange = onPhoneChange,
        phoneFieldFocused = phoneFieldFocused,
        placeholder = stringResource(id = R.string.phone_hint),
        nextAction = ImeAction.Next
    )

    Spacer(modifier = Modifier.height(42.dp))

    // 使用封装的验证码输入组件
    VerificationCodeField(
        verificationCode = verificationCode,
        onVerificationCodeChange = onVerificationCodeChange,
        codeFieldFocused = codeFieldFocused,
        onSendVerificationCode = onSendVerificationCode,
        placeholder = stringResource(id = R.string.verification_code),
        nextAction = ImeAction.Done,
        isPhoneValid = isPhoneValid
    )

    SpaceVerticalMedium()

    // 使用封装的用户协议组件
    UserAgreement(
        prefix = stringResource(id = R.string.login_agreement_prefix),
        onUserAgreementClick = onUserAgreementClick,
        onPrivacyPolicyClick = onPrivacyPolicyClick
    )

    SpaceVerticalXLarge()

    AppButton(
        text = stringResource(id = R.string.login),
        onClick = onLoginClick,
        shape = ButtonShape.ROUND,
        enabled = isLoginEnabled
    )

    // 使用封装的底部导航组件
    BottomNavigationRow(
        messageText = stringResource(id = R.string.sms_not_available),
        actionText = stringResource(id = R.string.use_third_party_login),
        onActionClick = onBackClick
    )
}

/**
 * 短信登录界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun SmsLoginScreenPreview() {
    AppTheme {
        SmsLoginScreen()
    }
}
