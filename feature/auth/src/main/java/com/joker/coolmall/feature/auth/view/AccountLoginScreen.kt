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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.navigation.auth.AuthNavigator
import com.joker.coolmall.core.navigation.common.CommonNavigator
import com.joker.coolmall.core.navigation.navigateBack
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.feature.auth.R
import com.joker.coolmall.feature.auth.component.AnimatedAuthPage
import com.joker.coolmall.feature.auth.component.BottomNavigationRow
import com.joker.coolmall.feature.auth.component.PasswordInputField
import com.joker.coolmall.feature.auth.component.PhoneInputField
import com.joker.coolmall.feature.auth.component.UserAgreement
import com.joker.coolmall.feature.auth.viewmodel.AccountLoginViewModel

/**
 * 账号密码登录路由
 *
 * @param viewModel 登录ViewModel
 * @author Joker.X
 */
@Composable
internal fun AccountLoginRoute(
    viewModel: AccountLoginViewModel = hiltViewModel()
) {
    // 收集账号输入
    val account by viewModel.account.collectAsState()
    // 收集密码输入
    val password by viewModel.password.collectAsState()
    // 收集登录按钮启用状态
    val isLoginEnabled by viewModel.isLoginEnabled.collectAsState(initial = false)

    AccountLoginScreen(
        account = account,
        password = password,
        isLoginEnabled = isLoginEnabled,
        onAccountChange = viewModel::updateAccount,
        onPasswordChange = viewModel::updatePassword,
        onLoginClick = viewModel::login
    )
}

/**
 * 账号密码登录界面
 *
 * @param account 账号
 * @param password 密码
 * @param isLoginEnabled 登录按钮是否启用
 * @param onAccountChange 账号变更回调
 * @param onPasswordChange 密码变更回调
 * @param onLoginClick 登录按钮点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountLoginScreen(
    account: String = "",
    password: String = "",
    isLoginEnabled: Boolean = false,
    onAccountChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
) {
    AnimatedAuthPage(
        title = stringResource(id = R.string.welcome_login),
        onBackClick = { navigateBack() }
    ) {
        AccountLoginContentView(
            account = account,
            password = password,
            isLoginEnabled = isLoginEnabled,
            onAccountChange = onAccountChange,
            onPasswordChange = onPasswordChange,
            onLoginClick = onLoginClick
        )
    }
}

/**
 * 账号密码登录内容视图
 *
 * @param account 账号
 * @param password 密码
 * @param isLoginEnabled 登录按钮是否启用
 * @param onAccountChange 账号变更回调
 * @param onPasswordChange 密码变更回调
 * @param onLoginClick 登录按钮点击回调
 * @author Joker.X
 */
@Composable
private fun AccountLoginContentView(
    account: String,
    password: String,
    isLoginEnabled: Boolean,
    onAccountChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    // 记录输入框焦点状态
    val accountFieldFocused = remember { mutableStateOf(false) }
    val passwordFieldFocused = remember { mutableStateOf(false) }

    // 使用封装的手机号输入组件
    PhoneInputField(
        phone = account,
        onPhoneChange = onAccountChange,
        phoneFieldFocused = accountFieldFocused,
        placeholder = stringResource(id = R.string.phone_hint),
        nextAction = ImeAction.Next
    )

    Spacer(modifier = Modifier.height(42.dp))

    // 使用封装的密码输入组件
    PasswordInputField(
        password = password,
        onPasswordChange = onPasswordChange,
        passwordFieldFocused = passwordFieldFocused,
        placeholder = stringResource(id = R.string.password_hint),
        nextAction = ImeAction.Done
    )

    SpaceVerticalMedium()

    // 使用封装的用户协议组件
    UserAgreement(
        prefix = stringResource(id = R.string.login_agreement_prefix),
        onUserAgreementClick = CommonNavigator::toUserAgreement,
        onPrivacyPolicyClick = CommonNavigator::toPrivacyPolicy
    )

    SpaceVerticalXLarge()

    AppButton(
        text = stringResource(id = R.string.login),
        onClick = onLoginClick,
        enabled = isLoginEnabled
    )

    // 使用封装的底部导航组件 - 分隔符样式
    BottomNavigationRow(
        messageText = stringResource(id = R.string.go_register),
        actionText = stringResource(id = R.string.forgot_password),
        onCancelClick = { AuthNavigator.toRegister() },
        onActionClick = { AuthNavigator.toResetPassword() },
        divider = true
    )
}

/**
 * 账号密码登录界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun AccountLoginScreenPreview() {
    AppTheme {
        AccountLoginScreen()
    }
}
