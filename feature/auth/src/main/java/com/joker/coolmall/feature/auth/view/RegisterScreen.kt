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
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.model.entity.Captcha
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.util.permission.PermissionUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.auth.R
import com.joker.coolmall.feature.auth.component.AnimatedAuthPage
import com.joker.coolmall.feature.auth.component.BottomNavigationRow
import com.joker.coolmall.feature.auth.component.ImageCaptchaDialog
import com.joker.coolmall.feature.auth.component.PasswordInputField
import com.joker.coolmall.feature.auth.component.PhoneInputField
import com.joker.coolmall.feature.auth.component.UserAgreement
import com.joker.coolmall.feature.auth.component.VerificationCodeField
import com.joker.coolmall.feature.auth.viewmodel.RegisterViewModel

/**
 * 注册路由
 *
 * @param viewModel 注册ViewModel
 */
@Composable
internal fun RegisterRoute(
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val phone by viewModel.phone.collectAsState()
    val verificationCode by viewModel.verificationCode.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val showImageCodePopup by viewModel.showImageCodePopup.collectAsState()
    val captcha by viewModel.captcha.collectAsState()
    val isLoadingCaptcha by viewModel.isLoadingCaptcha.collectAsState()
    val isPhoneValid by viewModel.isPhoneValid.collectAsState(initial = false)
    val isRegisterEnabled by viewModel.isRegisterEnabled.collectAsState(initial = false)

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
                    ToastUtils.showError("需要通知权限才能接收验证码")
                }
            }
        } else {
            // 如果不是 Activity Context，直接发送验证码
            viewModel.onSendCodeButtonClick()
        }
    }

    RegisterScreen(
        phone = phone,
        verificationCode = verificationCode,
        password = password,
        confirmPassword = confirmPassword,
        showImageCodePopup = showImageCodePopup,
        captcha = captcha,
        isLoadingCaptcha = isLoadingCaptcha,
        isPhoneValid = isPhoneValid,
        isRegisterEnabled = isRegisterEnabled,
        onHideImageCodePopup = viewModel::onHideImageCodePopup,
        onPhoneChange = viewModel::updatePhone,
        onVerificationCodeChange = viewModel::updateVerificationCode,
        onPasswordChange = viewModel::updatePassword,
        onConfirmPasswordChange = viewModel::updateConfirmPassword,
        onSendVerificationCode = onSendVerificationCodeWithPermission,
        onImageCodeConfirm = viewModel::onImageCodeConfirm,
        onRefreshCaptcha = viewModel::getCaptcha,
        onRegisterClick = viewModel::register,
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 注册界面
 *
 * @param phone 手机号
 * @param verificationCode 验证码
 * @param password 密码
 * @param confirmPassword 确认密码
 * @param showImageCodePopup 图片验证码 Popup
 * @param captcha 图片验证码
 * @param isLoadingCaptcha 验证码加载状态
 * @param isPhoneValid 手机号是否有效
 * @param isRegisterEnabled 注册按钮是否可用
 * @param onHideImageCodePopup 图片验证码 Popup 隐藏
 * @param onPhoneChange 手机号变更回调
 * @param onVerificationCodeChange 验证码变更回调
 * @param onPasswordChange 密码变更回调
 * @param onConfirmPasswordChange 确认密码变更回调
 * @param onSendVerificationCode 发送验证码回调
 * @param onImageCodeConfirm 图形验证码确认回调
 * @param onRefreshCaptcha 刷新验证码回调
 * @param onRegisterClick 注册按钮点击回调
 * @param onBackClick 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RegisterScreen(
    phone: String = "",
    verificationCode: String = "",
    password: String = "",
    confirmPassword: String = "",
    showImageCodePopup: Boolean = false,
    captcha: Captcha = Captcha(),
    isLoadingCaptcha: Boolean = false,
    isPhoneValid: Boolean = false,
    isRegisterEnabled: Boolean = false,
    onHideImageCodePopup: () -> Unit = {},
    onPhoneChange: (String) -> Unit = {},
    onVerificationCodeChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onSendVerificationCode: () -> Unit = {},
    onImageCodeConfirm: (String) -> Unit = {},
    onRefreshCaptcha: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {

    // 记录输入框焦点状态
    val phoneFieldFocused = remember { mutableStateOf(false) }
    val codeFieldFocused = remember { mutableStateOf(false) }
    val passwordFieldFocused = remember { mutableStateOf(false) }
    val confirmPasswordFieldFocused = remember { mutableStateOf(false) }

    AnimatedAuthPage(
        title = stringResource(id = R.string.welcome_register),
        withFadeIn = true,
        onBackClick = onBackClick
    ) {
        // 使用封装的手机号输入组件
        PhoneInputField(
            phone = phone,
            onPhoneChange = onPhoneChange,
            phoneFieldFocused = phoneFieldFocused,
            placeholder = stringResource(id = R.string.phone_hint),
            nextAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 使用封装的验证码输入组件
        VerificationCodeField(
            verificationCode = verificationCode,
            onVerificationCodeChange = onVerificationCodeChange,
            codeFieldFocused = codeFieldFocused,
            onSendVerificationCode = onSendVerificationCode,
            placeholder = stringResource(id = R.string.verification_code),
            nextAction = ImeAction.Next,
            isPhoneValid = isPhoneValid
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 使用封装的密码输入组件 - 设置密码
        PasswordInputField(
            password = password,
            onPasswordChange = onPasswordChange,
            passwordFieldFocused = passwordFieldFocused,
            placeholder = stringResource(id = R.string.set_password),
            nextAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 使用封装的密码输入组件 - 确认密码
        PasswordInputField(
            password = confirmPassword,
            onPasswordChange = onConfirmPasswordChange,
            passwordFieldFocused = confirmPasswordFieldFocused,
            placeholder = stringResource(id = R.string.confirm_password),
            nextAction = ImeAction.Done
        )

        SpaceVerticalMedium()

        // 使用封装的用户协议组件
        UserAgreement(
            prefix = stringResource(id = R.string.register_agreement_prefix)
        )

        SpaceVerticalXLarge()

        AppButton(
            text = stringResource(id = R.string.register),
            onClick = onRegisterClick,
            enabled = isRegisterEnabled
        )

        // 使用封装的底部导航组件
        BottomNavigationRow(
            messageText = stringResource(id = R.string.have_account),
            actionText = stringResource(id = R.string.go_login),
            onActionClick = onBackClick
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

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen()
    }
}