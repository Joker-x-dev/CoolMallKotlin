package com.joker.coolmall.feature.auth.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.designsystem.component.CenterRow
import com.joker.coolmall.core.designsystem.component.StartRow
import com.joker.coolmall.core.designsystem.component.TopColumn
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ColorWarning
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXXLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXXLarge
import com.joker.coolmall.core.designsystem.theme.TitleLarge
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.auth.state.AuthUiState
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
    val uiState by viewModel.uiState.collectAsState()

    SmsLoginScreen(
        phone = phone,
        verificationCode = verificationCode,
        uiState = uiState,
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
    uiState: AuthUiState = AuthUiState.Initial,
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

    // 使用rememberSaveable来保持状态，即使在配置更改时也不会重置
    // isAnimationPlayed标志用于跟踪动画是否已经播放过
    val isAnimationPlayed = rememberSaveable { mutableStateOf(false) }

    // 创建动画状态，根据isAnimationPlayed确定初始状态
    // 如果已经播放过动画，则直接设置为true(可见)，否则设置为false(不可见)
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
        // 整个页面内容用AnimatedVisibility包裹，实现从底部向上飞升的动画
        AnimatedVisibility(
            visibleState = animationState,
            enter = slideInVertically(
                initialOffsetY = { it }, // 从下方开始
                animationSpec = tween(
                    durationMillis = 400
                )
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

                // 手机号输入框及底部线条
                StartRow {
                    Text(
                        text = "+86",
                        color = Primary,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = SpaceHorizontalXLarge)
                    )

                    SimpleTextField(
                        value = phone,
                        onValueChange = onPhoneChange,
                        placeholder = "手机号码",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { phoneFieldFocused.value = it.isFocused }
                    )
                }

                SpaceVerticalLarge()

                // 统一的底部线条
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (phoneFieldFocused.value) Primary else MaterialTheme.colorScheme.outline
                )

                Spacer(modifier = Modifier.height(42.dp))

                // 验证码输入框和发送按钮及底部线条
                StartRow {
                    SimpleTextField(
                        value = verificationCode,
                        onValueChange = onVerificationCodeChange,
                        placeholder = "验证码",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { codeFieldFocused.value = it.isFocused }
                    )

                    Text(
                        text = "获取验证码",
                        color = ColorWarning,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(start = SpaceHorizontalXLarge)
                            .clickable(onClick = onSendVerificationCode)
                    )

                }

                SpaceVerticalLarge()

                // 统一的底部线条
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (codeFieldFocused.value) Primary else MaterialTheme.colorScheme.outline
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
                    Text(text = "登录", style = TitleLarge)
                }

                // 底部导航
                CenterRow(
                    modifier = Modifier
                        .padding(top = 32.dp),
                ) {
                    Text(
                        text = "短信不可用？",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    TextButton(onClick = onNavigateToRegister) {
                        Text(
                            text = "使用三方登录",
                            color = Primary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

/**
 * 自定义底部线条输入框（不含底部线条，由父容器统一处理）
 */
@Composable
fun SimpleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true
) {
    val placeholderVisible = value.isEmpty()

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        ),
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        modifier = modifier
    ) { innerTextField ->
        Box {
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
}

@Preview(showBackground = true)
@Composable
fun SmsLoginScreenPreview() {
    AppTheme {
        SmsLoginScreen()
    }
} 
