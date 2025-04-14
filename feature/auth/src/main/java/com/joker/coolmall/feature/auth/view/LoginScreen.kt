package com.joker.coolmall.feature.auth.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.designsystem.component.CenterRow
import com.joker.coolmall.core.designsystem.component.EndRow
import com.joker.coolmall.core.designsystem.component.StartRow
import com.joker.coolmall.core.designsystem.component.TopColumn
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXXLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXXLarge
import com.joker.coolmall.core.designsystem.theme.TitleLarge
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.auth.R
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
        onNavigateToResetPassword = { navController.navigate(AuthRoutes.RESET_PASSWORD) },
        onNavigateToSmsLogin = { navController.navigate(AuthRoutes.SMS_LOGIN) },
        onNavigateToRegister = { navController.navigate(AuthRoutes.REGISTER) }
    )
}

/**
 * 自定义底部线条密码输入框（不含底部线条，由父容器统一处理）
 */
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true
) {
    val placeholderVisible = value.isEmpty()
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    
    Box(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        ) { innerTextField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                if (placeholderVisible) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                innerTextField()
            }
        }
        
        // 添加显示/隐藏密码的图标按钮
        IconButton(
            onClick = { passwordVisible = !passwordVisible },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp)
        ) {
            Image(
                painter = painterResource(
                    id = if (passwordVisible) R.drawable.ic_visible else R.drawable.ic_invisible
                ),
                contentDescription = if (passwordVisible) "隐藏密码" else "显示密码",
                modifier = Modifier.size(20.dp)
            )
        }
    }
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
    uiState: AuthUiState = AuthUiState.Initial,
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
    
    // 使用rememberSaveable来保持状态，即使在配置更改时也不会重置
    val isAnimationPlayed = rememberSaveable { mutableStateOf(false) }
    
    // 创建动画状态，根据isAnimationPlayed确定初始状态
    val animationState = remember { 
        MutableTransitionState(isAnimationPlayed.value) 
    }
    
    // 使用LaunchedEffect在组合时只触发一次
    LaunchedEffect(Unit) {
        if (!isAnimationPlayed.value) {
            // 首次进入，触发动画
            animationState.targetState = true
            // 标记动画已播放
            isAnimationPlayed.value = true
        } else {
            // 如果已经播放过，确保目标状态为true
            animationState.targetState = true
        }
    }

    AppScaffold(
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        AnimatedVisibility(
            visibleState = animationState,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 400)
            )
        ) {
            TopColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = SpaceHorizontalXXLarge)
                    .padding(top = SpaceVerticalXXLarge),
            ) {
                // 标题部分
                Text(
                    text = "你好，\n欢迎登录青商城",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Spacer(modifier = Modifier.height(64.dp))

                // 账号输入框
                BasicTextField(
                    value = account,
                    onValueChange = onAccountChange,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { accountFieldFocused.value = it.isFocused }
                ) { innerTextField ->
                    Box {
                        if (account.isEmpty()) {
                            Text(
                                text = "请输入账号",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }

                SpaceVerticalLarge()

                // 账号输入框底部线条
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (accountFieldFocused.value) Primary else MaterialTheme.colorScheme.outline
                )

                Spacer(modifier = Modifier.height(42.dp))

                // 密码输入框
                PasswordTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    placeholder = "请输入密码",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { passwordFieldFocused.value = it.isFocused }
                )

                SpaceVerticalLarge()

                // 密码输入框底部线条
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (passwordFieldFocused.value) Primary else MaterialTheme.colorScheme.outline
                )

                SpaceVerticalMedium()

                StartRow {
                    Text(
                        text = "登录即代表您已阅读并同意",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = "《用户协议》",
                        color = Primary,
                        fontSize = 12.sp,
                        modifier = Modifier.clickable(onClick = {})
                    )

                    Text(
                        text = "和",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = "《隐私政策》",
                        color = Primary,
                        fontSize = 12.sp,
                        modifier = Modifier.clickable(onClick = {})
                    )
                }

                SpaceVerticalXLarge()

                // 登录按钮
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text(
                        text = "登录",
                        style = TitleLarge
                    )
                }

                // 底部按钮区域
                CenterRow(
                    modifier = Modifier
                        .padding(top = 32.dp),
                ) {

                    TextButton(onClick = onNavigateToRegister) {
                        Text(
                            text = "去注册",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    Text(
                        text = "|",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    
                    TextButton(onClick = onNavigateToSmsLogin) {
                        Text(
                            text = "忘记密码",
                            fontSize = 14.sp,
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