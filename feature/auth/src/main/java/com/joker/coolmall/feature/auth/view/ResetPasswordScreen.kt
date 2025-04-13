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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.auth.state.AuthUiState
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
    val uiState by viewModel.uiState.collectAsState()

    ResetPasswordScreen(
        phone = phone,
        newPassword = newPassword,
        confirmPassword = confirmPassword,
        verificationCode = verificationCode,
        uiState = uiState,
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
    uiState: AuthUiState = AuthUiState.Initial,
    onPhoneChange: (String) -> Unit = {},
    onNewPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onVerificationCodeChange: (String) -> Unit = {},
    onSendVerificationCode: () -> Unit = {},
    onResetPasswordClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    AppScaffold(
        /*topBar = {
            CenterTopAppBar(
                title = "找回密码",
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
                    .padding(top = 40.dp, bottom = 30.dp),
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
                                imeAction = ImeAction.Next
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // 新密码输入框
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = onNewPasswordChange,
                        label = { Text("新密码") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 确认密码输入框
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = onConfirmPasswordChange,
                        label = { Text("确认密码") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // 重置密码按钮
                    Button(
                        onClick = onResetPasswordClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = phone.isNotEmpty() && 
                                newPassword.isNotEmpty() && 
                                confirmPassword.isNotEmpty() && 
                                verificationCode.isNotEmpty() && 
                                newPassword == confirmPassword &&
                                uiState !is AuthUiState.Loading
                    ) {
                        Text(if (uiState is AuthUiState.Loading) "重置中..." else "重置密码")
                    }

                    // 错误信息
                    if (uiState is AuthUiState.Error) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = (uiState as AuthUiState.Error).message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }

                    // 密码不匹配提示
                    if (newPassword.isNotEmpty() && confirmPassword.isNotEmpty() && newPassword != confirmPassword) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "两次输入的密码不一致",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    AppTheme {
        ResetPasswordScreen()
    }
} 