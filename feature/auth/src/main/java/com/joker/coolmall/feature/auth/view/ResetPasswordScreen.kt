package com.joker.coolmall.feature.auth.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.designsystem.component.StartRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.feature.auth.component.AnimatedAuthPage
import com.joker.coolmall.feature.auth.component.PasswordInputField
import com.joker.coolmall.feature.auth.component.PhoneInputField
import com.joker.coolmall.feature.auth.component.PrimaryButton
import com.joker.coolmall.feature.auth.component.VerificationCodeField
import com.joker.coolmall.feature.auth.viewmodel.ResetPasswordViewModel

/**
 * 找回密码路由
 *
 * @param viewModel 重置密码ViewModel
 * @param navController 导航控制器
 */
@Composable
fun ResetPasswordRoute(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    navController: NavController
) {
    val phone by viewModel.phone.collectAsState()
    val newPassword by viewModel.newPassword.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val verificationCode by viewModel.verificationCode.collectAsState()

    ResetPasswordScreen(
        phone = phone,
        newPassword = newPassword,
        confirmPassword = confirmPassword,
        verificationCode = verificationCode,
        onPhoneChange = viewModel::updatePhone,
        onNewPasswordChange = viewModel::updateNewPassword,
        onConfirmPasswordChange = viewModel::updateConfirmPassword,
        onVerificationCodeChange = viewModel::updateVerificationCode,
        onSendVerificationCode = viewModel::sendVerificationCode,
        onResetPasswordClick = viewModel::resetPassword,
        onNavigateBack = { navController.navigateUp() }
    )
}

/**
 * 找回密码界面
 *
 * @param phone 手机号
 * @param newPassword 新密码
 * @param confirmPassword 确认密码
 * @param verificationCode 验证码
 * @param uiState 当前UI状态
 * @param onPhoneChange 手机号变更回调
 * @param onNewPasswordChange 新密码变更回调
 * @param onConfirmPasswordChange 确认密码变更回调
 * @param onVerificationCodeChange 验证码变更回调
 * @param onSendVerificationCode 发送验证码回调
 * @param onResetPasswordClick 重置密码按钮点击回调
 * @param onNavigateBack 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    phone: String = "",
    newPassword: String = "",
    confirmPassword: String = "",
    verificationCode: String = "",
    onPhoneChange: (String) -> Unit = {},
    onNewPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onVerificationCodeChange: (String) -> Unit = {},
    onSendVerificationCode: () -> Unit = {},
    onResetPasswordClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    // 记录输入框焦点状态
    val phoneFieldFocused = remember { mutableStateOf(false) }
    val codeFieldFocused = remember { mutableStateOf(false) }
    val newPasswordFieldFocused = remember { mutableStateOf(false) }
    val confirmPasswordFieldFocused = remember { mutableStateOf(false) }

    AnimatedAuthPage(
        title = "重置密码",
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

        // 使用封装的密码输入组件 - 新密码
        PasswordInputField(
            password = newPassword,
            onPasswordChange = onNewPasswordChange,
            passwordFieldFocused = newPasswordFieldFocused,
            placeholder = "请设置新密码",
            nextAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 使用封装的密码输入组件 - 确认新密码
        PasswordInputField(
            password = confirmPassword,
            onPasswordChange = onConfirmPasswordChange,
            passwordFieldFocused = confirmPasswordFieldFocused,
            placeholder = "请确认新密码",
            nextAction = ImeAction.Done
        )

        SpaceVerticalMedium()

        // 提示信息
        StartRow {
            Text(
                text = "重置密码后，请使用新密码登录",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        // 密码不匹配提示
        if (newPassword.isNotEmpty() && confirmPassword.isNotEmpty() && newPassword != confirmPassword) {
            SpaceVerticalMedium()
            Text(
                text = "两次输入的密码不一致",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        // 使用封装的主题色按钮
        PrimaryButton(
            text = "重置密码",
            onClick = onResetPasswordClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    AppTheme {
        ResetPasswordScreen()
    }
} 