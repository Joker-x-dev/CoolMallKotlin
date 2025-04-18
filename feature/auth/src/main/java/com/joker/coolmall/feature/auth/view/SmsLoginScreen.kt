package com.joker.coolmall.feature.auth.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.joker.coolmall.feature.auth.component.PhoneInputField
import com.joker.coolmall.feature.auth.component.PrimaryButton
import com.joker.coolmall.feature.auth.component.UserAgreement
import com.joker.coolmall.feature.auth.component.VerificationCodeField
import com.joker.coolmall.feature.auth.viewmodel.SmsLoginViewModel
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 短信登录路由
 *
 * @param viewModel 短信登录ViewModel
 * @param navController 导航控制器
 */
@Composable
fun SmsLoginRoute(
    viewModel: SmsLoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val phone by viewModel.phone.collectAsState()
    val verificationCode by viewModel.verificationCode.collectAsState()

    SmsLoginScreen(
        phone = phone,
        verificationCode = verificationCode,
        onPhoneChange = viewModel::updatePhone,
        onVerificationCodeChange = viewModel::updateVerificationCode,
        onSendVerificationCode = viewModel::sendVerificationCode,
        onLoginClick = viewModel::login,
        onNavigateBack = { navController.navigateUp() },
        onNavigateToRegister = { navController.navigate(AuthRoutes.REGISTER) }
    )
}

/**
 * 短信登录界面
 *
 * @param phone 手机号
 * @param verificationCode 验证码
 * @param uiState 当前UI状态
 * @param onPhoneChange 手机号变更回调
 * @param onVerificationCodeChange 验证码变更回调
 * @param onSendVerificationCode 发送验证码回调
 * @param onLoginClick 登录按钮点击回调
 * @param onNavigateBack 返回上一页回调
 * @param onNavigateToRegister 跳转到注册页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsLoginScreen(
    phone: String = "",
    verificationCode: String = "",
    onPhoneChange: (String) -> Unit = {},
    onVerificationCodeChange: (String) -> Unit = {},
    onSendVerificationCode: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    // 记录输入框焦点状态
    val phoneFieldFocused = remember { mutableStateOf(false) }
    val codeFieldFocused = remember { mutableStateOf(false) }

    AnimatedAuthPage(
        title = "你好，\n欢迎登录青商城"
    ) {
        // 使用封装的手机号输入组件
        PhoneInputField(
            phone = phone,
            onPhoneChange = onPhoneChange,
            phoneFieldFocused = phoneFieldFocused,
            placeholder = "手机号码",
            nextAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(42.dp))

        // 使用封装的验证码输入组件
        VerificationCodeField(
            verificationCode = verificationCode,
            onVerificationCodeChange = onVerificationCodeChange,
            codeFieldFocused = codeFieldFocused,
            onSendVerificationCode = onSendVerificationCode,
            placeholder = "验证码",
            nextAction = ImeAction.Done
        )

        SpaceVerticalMedium()

        // 使用封装的用户协议组件
        UserAgreement()

        // 使用封装的主题色按钮
        PrimaryButton(
            text = "登录",
            onClick = onLoginClick
        )

        // 使用封装的底部导航组件
        BottomNavigationRow(
            messageText = "短信不可用？",
            actionText = "使用三方登录",
            onActionClick = onNavigateToRegister
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmsLoginScreenPreview() {
    AppTheme {
        SmsLoginScreen()
    }
} 
