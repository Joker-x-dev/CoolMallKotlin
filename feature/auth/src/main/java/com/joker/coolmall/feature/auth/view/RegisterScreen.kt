package com.joker.coolmall.feature.auth.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.feature.auth.component.AnimatedAuthPage
import com.joker.coolmall.feature.auth.component.BottomNavigationRow
import com.joker.coolmall.feature.auth.component.PasswordInputField
import com.joker.coolmall.feature.auth.component.PhoneInputField
import com.joker.coolmall.feature.auth.component.PrimaryButton
import com.joker.coolmall.feature.auth.component.UserAgreement
import com.joker.coolmall.feature.auth.component.VerificationCodeField
import com.joker.coolmall.feature.auth.viewmodel.RegisterViewModel

/**
 * 注册路由
 *
 * @param viewModel 注册ViewModel
 * @param navController 导航控制器
 */
@Composable
fun RegisterRoute(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavController
) {
    val phone by viewModel.phone.collectAsState()
    val verificationCode by viewModel.verificationCode.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()

    RegisterScreen(
        phone = phone,
        verificationCode = verificationCode,
        password = password,
        confirmPassword = confirmPassword,
        onPhoneChange = viewModel::updatePhone,
        onVerificationCodeChange = viewModel::updateVerificationCode,
        onPasswordChange = viewModel::updatePassword,
        onConfirmPasswordChange = viewModel::updateConfirmPassword,
        onSendVerificationCode = viewModel::sendVerificationCode,
        onRegisterClick = viewModel::register,
        onNavigateBack = { navController.navigateUp() },
        onNavigateToLogin = { navController.popBackStack() }
    )
}

/**
 * 注册界面
 *
 * @param phone 手机号
 * @param verificationCode 验证码
 * @param password 密码
 * @param confirmPassword 确认密码
 * @param uiState 当前UI状态
 * @param onPhoneChange 手机号变更回调
 * @param onVerificationCodeChange 验证码变更回调
 * @param onPasswordChange 密码变更回调
 * @param onConfirmPasswordChange 确认密码变更回调
 * @param onSendVerificationCode 发送验证码回调
 * @param onRegisterClick 注册按钮点击回调
 * @param onNavigateBack 返回上一页回调
 * @param onNavigateToLogin 导航到登录页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    phone: String = "",
    verificationCode: String = "",
    password: String = "",
    confirmPassword: String = "",
    onPhoneChange: (String) -> Unit = {},
    onVerificationCodeChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onSendVerificationCode: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    // 是否同意协议
    var agreedToTerms by remember { mutableStateOf(false) }

    // 记录输入框焦点状态
    val phoneFieldFocused = remember { mutableStateOf(false) }
    val codeFieldFocused = remember { mutableStateOf(false) }
    val passwordFieldFocused = remember { mutableStateOf(false) }
    val confirmPasswordFieldFocused = remember { mutableStateOf(false) }

    AnimatedAuthPage(
        title = "你好，\n欢迎注册青商城",
        withFadeIn = true
    ) {
        // 使用封装的手机号输入组件
        PhoneInputField(
            phone = phone,
            onPhoneChange = onPhoneChange,
            phoneFieldFocused = phoneFieldFocused,
            placeholder = "手机号码",
            nextAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 使用封装的验证码输入组件
        VerificationCodeField(
            verificationCode = verificationCode,
            onVerificationCodeChange = onVerificationCodeChange,
            codeFieldFocused = codeFieldFocused,
            onSendVerificationCode = onSendVerificationCode,
            placeholder = "验证码",
            nextAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 使用封装的密码输入组件 - 设置密码
        PasswordInputField(
            password = password,
            onPasswordChange = onPasswordChange,
            passwordFieldFocused = passwordFieldFocused,
            placeholder = "请设置密码",
            nextAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 使用封装的密码输入组件 - 确认密码
        PasswordInputField(
            password = confirmPassword,
            onPasswordChange = onConfirmPasswordChange,
            passwordFieldFocused = confirmPasswordFieldFocused,
            placeholder = "请确认密码",
            nextAction = ImeAction.Done
        )

        SpaceVerticalMedium()

        // 使用封装的用户协议组件
        UserAgreement(prefix = "注册即代表您已阅读并同意")

        // 使用封装的主题色按钮
        PrimaryButton(
            text = "注册",
            onClick = onRegisterClick
        )

        // 使用封装的底部导航组件
        BottomNavigationRow(
            messageText = "已有账号？",
            actionText = "去登录",
            onActionClick = onNavigateToLogin
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen()
    }
} 