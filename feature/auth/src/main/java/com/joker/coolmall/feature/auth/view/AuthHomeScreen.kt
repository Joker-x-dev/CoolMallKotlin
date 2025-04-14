package com.joker.coolmall.feature.auth.view

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.designsystem.component.CenterColumn
import com.joker.coolmall.core.designsystem.component.CenterRow
import com.joker.coolmall.core.designsystem.component.SpaceBetweenColumn
import com.joker.coolmall.core.designsystem.component.SpaceEvenlyRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.TextTertiaryLight
import com.joker.coolmall.core.designsystem.theme.TextWhite
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.auth.R
import com.joker.coolmall.feature.auth.viewmodel.AuthViewModel
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 登录主页路由
 *
 * @param viewModel 认证ViewModel
 * @param navController 导航控制器
 */
@Composable
fun AuthHomeRoute(
    viewModel: AuthViewModel = hiltViewModel(), navController: NavController
) {
    AuthHomeScreen(
        onNavigateToLogin = { navController.navigate(AuthRoutes.LOGIN) },
        onNavigateToSmsLogin = { navController.navigate(AuthRoutes.SMS_LOGIN) },
        onNavigateToRegister = { navController.navigate(AuthRoutes.REGISTER) })
}

/**
 * 登录主页界面
 *
 * @param onNavigateToLogin 导航到账号密码登录页面回调
 * @param onNavigateToSmsLogin 导航到短信登录页面回调
 * @param onNavigateToRegister 导航到注册页面回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthHomeScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToSmsLogin: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
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
        SpaceBetweenColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 顶部Logo - 从上到下淡入淡出
            AnimatedVisibility(
                visibleState = animationState,
                enter = slideInVertically(
                    initialOffsetY = { -it }, // 从上方开始
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {

                // 包裹Logo在一个有额外padding的容器中，确保弹跳空间
                Box(
                    modifier = Modifier
                        .padding(top = 80.dp, bottom = 30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LogoIcon(size = 120.dp)
                }
            }


            // 底部容器，包含登录按钮和第三方登录 - 从下向上升起
            AnimatedVisibility(
                visibleState = animationState,
                enter = slideInVertically(
                    initialOffsetY = { it }, // 从下方开始
                    animationSpec = tween(
                        durationMillis = 500,
                        delayMillis = 100 // 稍微延迟，让logo先出现
                    )
                )
            ) {
                CenterColumn(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    // 验证码登录按钮（主按钮）
                    Button(
                        onClick = onNavigateToSmsLogin,
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "验证码登录",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = TextWhite
                            )
                        )
                    }

                    // 账号密码登录按钮（次要按钮）
                    OutlinedButton(
                        onClick = onNavigateToLogin,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .height(48.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "账号密码登录",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(48.dp))

                    Text(
                        text = "其他登录方式",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiaryLight,
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                    )

                    // 第三方登录按钮
                    SpaceEvenlyRow() {
                        // 微信登录
                        ThirdPartyLoginButton(
                            icon = R.drawable.ic_wechat,
                            name = "微信",
                            onClick = { /* 微信登录逻辑 */ })

                        // QQ登录
                        ThirdPartyLoginButton(
                            icon = R.drawable.ic_qq,
                            name = "QQ",
                            onClick = { /* QQ登录逻辑 */ })

                        // 支付宝登录
                        ThirdPartyLoginButton(
                            icon = R.drawable.ic_alipay,
                            name = "支付宝",
                            onClick = { /* 支付宝登录逻辑 */ })
                    }

                    // 用户协议
                    UserAgreement(
                        modifier = Modifier.padding(top = 32.dp)
                    )
                }
            }
        }
    }
}

/**
 * 第三方登录按钮
 *
 * @param icon 图标资源ID
 * @param name 名称
 * @param onClick 点击回调
 */
@Composable
fun ThirdPartyLoginButton(
    @DrawableRes icon: Int, name: String, onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(32.dp)
            )
        }

        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = TextTertiaryLight
        )
    }
}

/**
 * 用户协议文本
 *
 * @param modifier 修饰符
 */
@Composable
fun UserAgreement(
    modifier: Modifier = Modifier
) {
    CenterRow(modifier = modifier) {
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
}

@Preview(showBackground = true)
@Composable
fun AuthHomeScreenPreview() {
    AppTheme {
        AuthHomeScreen()
    }
}