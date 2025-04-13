package com.joker.coolmall.feature.auth.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.auth.state.AuthUiState
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
    val uiState by viewModel.uiState.collectAsState()

    LoginScreen(
        account = account,
        password = password,
        uiState = uiState,
        onAccountChange = viewModel::updateAccount,
        onPasswordChange = viewModel::updatePassword,
        onLoginClick = viewModel::login,
        onNavigateBack = { navController.navigateUp() },
        onNavigateToResetPassword = { navController.navigate(AuthRoutes.RESET_PASSWORD) }
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
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    account: String = "",
    password: String = "",
    uiState: AuthUiState = AuthUiState.Initial,
    onAccountChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateToResetPassword: () -> Unit = {}
) {
    AppScaffold(
        /*topBar = {
            CenterTopAppBar(
                title = "账号密码登录",
                onBackClick = onNavigateBack
            )
        },*/
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo部分
            Box(
                modifier = Modifier
                    .padding(top = 80.dp, bottom = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                LogoIcon(size = 80.dp)
            }

            // 表单部分
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 账号输入框
                    OutlinedTextField(
                        value = account,
                        onValueChange = onAccountChange,
                        label = { Text("账号") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 密码输入框
                    OutlinedTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = { Text("密码") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 忘记密码
                    TextButton(
                        onClick = onNavigateToResetPassword,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("忘记密码?")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 登录按钮
                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = account.isNotEmpty() && password.isNotEmpty() && uiState !is AuthUiState.Loading
                    ) {
                        Text(if (uiState is AuthUiState.Loading) "登录中..." else "登录")
                    }

                    // 错误信息
                    if (uiState is AuthUiState.Error) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = (uiState as AuthUiState.Error).message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen()
    }
} 