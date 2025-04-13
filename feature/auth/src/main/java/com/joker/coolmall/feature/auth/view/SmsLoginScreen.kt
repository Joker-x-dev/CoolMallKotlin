package com.joker.coolmall.feature.auth.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.auth.state.AuthUiState
import com.joker.coolmall.feature.auth.viewmodel.SmsLoginViewModel

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
    val uiState by viewModel.uiState.collectAsState()

    SmsLoginScreen(
        phone = phone,
        verificationCode = verificationCode,
        uiState = uiState,
        onPhoneChange = viewModel::updatePhone,
        onVerificationCodeChange = viewModel::updateVerificationCode,
        onSendVerificationCode = viewModel::sendVerificationCode,
        onLoginClick = viewModel::login,
        onNavigateBack = { navController.navigateUp() }
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
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsLoginScreen(
    phone: String = "",
    verificationCode: String = "",
    uiState: AuthUiState = AuthUiState.Initial,
    onPhoneChange: (String) -> Unit = {},
    onVerificationCodeChange: (String) -> Unit = {},
    onSendVerificationCode: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    AppScaffold(
        /*topBar = {
            CenterTopAppBar(
                title = "验证码登录",
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
                    // 手机号输入框
                    OutlinedTextField(
                        value = phone,
                        onValueChange = onPhoneChange,
                        label = { Text("手机号") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 验证码输入框和发送按钮
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = verificationCode,
                            onValueChange = onVerificationCodeChange,
                            label = { Text("验证码") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )

                        OutlinedButton(
                            onClick = onSendVerificationCode,
                            modifier = Modifier.padding(start = 8.dp),
                            enabled = phone.length >= 11 && uiState !is AuthUiState.Loading
                        ) {
                            Text("发送验证码")
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // 登录按钮
                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = phone.isNotEmpty() && verificationCode.isNotEmpty() && uiState !is AuthUiState.Loading
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
fun SmsLoginScreenPreview() {
    AppTheme {
        SmsLoginScreen()
    }
} 