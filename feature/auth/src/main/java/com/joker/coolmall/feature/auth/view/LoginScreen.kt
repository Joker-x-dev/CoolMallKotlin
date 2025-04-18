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
import com.joker.coolmall.feature.auth.component.PasswordInputField
import com.joker.coolmall.feature.auth.component.PhoneInputField
import com.joker.coolmall.feature.auth.component.PrimaryButton
import com.joker.coolmall.feature.auth.component.UserAgreement
import com.joker.coolmall.feature.auth.viewmodel.LoginViewModel
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 账号密码登录路由
 *
 * @param viewModel 登录ViewModel
 * @param navController 导航控制器
 */
@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val account by viewModel.account.collectAsState()
    val password by viewModel.password.collectAsState()

    LoginScreen(
        account = account,
        password = password,
        onAccountChange = viewModel::updateAccount,
        onPasswordChange = viewModel::updatePassword,
        onLoginClick = viewModel::login,
        onNavigateBack = { navController.navigateUp() },
        onNavigateToResetPassword = { navController.navigate(AuthRoutes.RESET_PASSWORD) },
        onNavigateToSmsLogin = { navController.navigate(AuthRoutes.SMS_LOGIN) },
        onNavigateToRegister = { navController.navigate(AuthRoutes.REGISTER) }
    )
}

/**
 * 账号密码登录界面
 *
 * @param account 账号
 * @param password 密码
 * @param uiState 当前UI状态
 * @param onAccountChange 账号变更回调
 * @param onPasswordChange 密码变更回调
 * @param onLoginClick 登录按钮点击回调
 * @param onNavigateBack 返回上一页回调
 * @param onNavigateToResetPassword 导航到找回密码页面回调
 * @param onNavigateToSmsLogin 导航到短信登录页面回调
 * @param onNavigateToRegister 导航到注册页面回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    account: String = "",
    password: String = "",
    onAccountChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateToResetPassword: () -> Unit = {},
    onNavigateToSmsLogin: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    // 记录输入框焦点状态
    val accountFieldFocused = remember { mutableStateOf(false) }
    val passwordFieldFocused = remember { mutableStateOf(false) }

    AnimatedAuthPage(
        title = "你好，\n欢迎登录青商城"
    ) {
        // 使用封装的手机号输入组件
        PhoneInputField(
            phone = account,
            onPhoneChange = onAccountChange,
            phoneFieldFocused = accountFieldFocused,
            placeholder = "请输入手机号",
            nextAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(42.dp))

        // 使用封装的密码输入组件
        PasswordInputField(
            password = password,
            onPasswordChange = onPasswordChange,
            passwordFieldFocused = passwordFieldFocused,
            placeholder = "请输入密码",
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

        // 使用封装的底部导航组件 - 分隔符样式
        BottomNavigationRow(
            messageText = "去注册",
            actionText = "忘记密码",
            onActionClick = onNavigateToResetPassword,
            divider = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen()
    }
} 