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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.auth.state.AuthUiState
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
    val uiState by viewModel.uiState.collectAsState()

    RegisterScreen(
        phone = phone,
        verificationCode = verificationCode,
        password = password,
        confirmPassword = confirmPassword,
        uiState = uiState,
        onPhoneChange = viewModel::updatePhone,
        onVerificationCodeChange = viewModel::updateVerificationCode,
        onPasswordChange = viewModel::updatePassword,
        onConfirmPasswordChange = viewModel::updateConfirmPassword,
        onSendVerificationCode = viewModel::sendVerificationCode,
        onRegisterClick = viewModel::register,
        onNavigateBack = { navController.navigateUp() },
        onAgreementClick = { /* 处理用户协议点击 */ },
        onPrivacyPolicyClick = { /* 处理隐私政策点击 */ }
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
 * @param onAgreementClick 用户协议点击回调
 * @param onPrivacyPolicyClick 隐私政策点击回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    phone: String = "",
    verificationCode: String = "",
    password: String = "",
    confirmPassword: String = "",
    uiState: AuthUiState = AuthUiState.Initial,
    onPhoneChange: (String) -> Unit = {},
    onVerificationCodeChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onSendVerificationCode: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {}
) {
    // 是否同意协议
    var agreedToTerms by remember { mutableStateOf(false) }

    AppScaffold(
        /*topBar = {
            CenterTopAppBar(
                title = "注册账号",
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

                    // 密码输入框
                    OutlinedTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = { Text("密码") },
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // 协议同意选项
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = agreedToTerms,
                            onCheckedChange = { agreedToTerms = it }
                        )
                        
                        Text(
                            text = "我已阅读并同意 ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        
                        TextButton(
                            onClick = onAgreementClick,
                            modifier = Modifier.padding(horizontal = 0.dp)
                        ) {
                            Text(
                                text = "《用户协议》",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                        
                        Text(
                            text = "和",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        
                        TextButton(
                            onClick = onPrivacyPolicyClick,
                            modifier = Modifier.padding(horizontal = 0.dp)
                        ) {
                            Text(
                                text = "《隐私政策》",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // 注册按钮
                    Button(
                        onClick = onRegisterClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = phone.isNotEmpty() && 
                                verificationCode.isNotEmpty() && 
                                password.isNotEmpty() && 
                                confirmPassword.isNotEmpty() && 
                                agreedToTerms &&
                                uiState !is AuthUiState.Loading
                    ) {
                        Text(if (uiState is AuthUiState.Loading) "注册中..." else "注册")
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
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen()
    }
} 