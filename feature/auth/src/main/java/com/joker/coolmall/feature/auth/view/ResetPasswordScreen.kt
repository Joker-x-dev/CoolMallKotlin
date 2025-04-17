package com.joker.coolmall.feature.auth.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
import com.joker.coolmall.feature.auth.R
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
 * 自定义底部线条密码输入框（不含底部线条，由父容器统一处理）
 */
@Composable
fun ResetPasswordTextField(
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
            ) + fadeIn(
                initialAlpha = 0f,
                animationSpec = tween(durationMillis = 500)
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
                    text = "重置密码",
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

                    BasicTextField(
                        value = phone,
                        onValueChange = onPhoneChange,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { phoneFieldFocused.value = it.isFocused }
                    ) { innerTextField ->
                        Box {
                            if (phone.isEmpty()) {
                                Text(
                                    text = "手机号码",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                }

                SpaceVerticalLarge()

                // 统一的底部线条
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (phoneFieldFocused.value) Primary else MaterialTheme.colorScheme.outline
                )

                Spacer(modifier = Modifier.height(30.dp))

                // 验证码输入框和发送按钮及底部线条
                StartRow {
                    BasicTextField(
                        value = verificationCode,
                        onValueChange = onVerificationCodeChange,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { codeFieldFocused.value = it.isFocused }
                    ) { innerTextField ->
                        Box {
                            if (verificationCode.isEmpty()) {
                                Text(
                                    text = "验证码",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    }

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

                Spacer(modifier = Modifier.height(30.dp))

                // 新密码输入框
                ResetPasswordTextField(
                    value = newPassword,
                    onValueChange = onNewPasswordChange,
                    placeholder = "请设置新密码",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { newPasswordFieldFocused.value = it.isFocused }
                )

                SpaceVerticalLarge()

                // 新密码输入框底部线条
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (newPasswordFieldFocused.value) Primary else MaterialTheme.colorScheme.outline
                )

                Spacer(modifier = Modifier.height(30.dp))

                // 确认密码输入框
                ResetPasswordTextField(
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    placeholder = "请确认新密码",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { confirmPasswordFieldFocused.value = it.isFocused }
                )

                SpaceVerticalLarge()

                // 确认密码输入框底部线条
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (confirmPasswordFieldFocused.value) Primary else MaterialTheme.colorScheme.outline
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

                SpaceVerticalXLarge()

                // 重置密码按钮
                Button(
                    onClick = onResetPasswordClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text(
                        text = "重置密码",
                        style = TitleLarge
                    )
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