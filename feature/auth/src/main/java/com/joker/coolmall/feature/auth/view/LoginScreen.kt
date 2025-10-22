package com.joker.coolmall.feature.auth.view

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.CenterColumn
import com.joker.coolmall.core.designsystem.component.SpaceBetweenColumn
import com.joker.coolmall.core.designsystem.component.SpaceEvenlyRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.button.ButtonStyle
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.auth.R
import com.joker.coolmall.feature.auth.component.UserAgreement
import com.joker.coolmall.feature.auth.viewmodel.LoginViewModel

/**
 * 登录主页路由
 *
 * @param viewModel 认证ViewModel
 * @author Joker.X
 */
@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LoginScreen(
        toAccountLogin = viewModel::toAccountLoginPage,
        toSmsLogin = viewModel::toSMSLoginPage,
        onBackClick = viewModel::navigateBack,
        onWechatLoginClick = viewModel::onWechatLoginClick,
        onAlipayLoginClick = viewModel::onAlipayLoginClick,
        onQQLogin = {
            // 获取当前 Activity 实例
            val activity = context as? Activity
            if (activity != null) {
                viewModel.startQQLogin(activity)
            }
        },
        onUserAgreementClick = viewModel::onUserAgreementClick,
        onPrivacyPolicyClick = viewModel::onPrivacyPolicyClick
    )
}

/**
 * 登录主页界面
 *
 * @param toAccountLogin 导航到账号密码登录页面回调
 * @param toSmsLogin 导航到短信登录页面回调
 * @param onBackClick 返回按钮回调
 * @param onWechatLoginClick 微信登录点击回调
 * @param onAlipayLoginClick 支付宝登录点击回调
 * @param onQQLogin QQ 登录回调
 * @param onUserAgreementClick 用户协议点击回调
 * @param onPrivacyPolicyClick 隐私政策点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginScreen(
    toAccountLogin: () -> Unit = {},
    toSmsLogin: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onWechatLoginClick: () -> Unit = {},
    onAlipayLoginClick: () -> Unit = {},
    onQQLogin: () -> Unit = {},
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {}
) {
    AppScaffold(
        onBackClick = onBackClick,
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        LoginContentView(
            toAccountLogin = toAccountLogin,
            toSmsLogin = toSmsLogin,
            onWechatLoginClick = onWechatLoginClick,
            onAlipayLoginClick = onAlipayLoginClick,
            onQQLogin = onQQLogin,
            onUserAgreementClick = onUserAgreementClick,
            onPrivacyPolicyClick = onPrivacyPolicyClick
        )
    }
}

/**
 * 登录主页内容视图
 *
 * @param toAccountLogin 导航到账号密码登录页面回调
 * @param toSmsLogin 导航到短信登录页面回调
 * @param onWechatLoginClick 微信登录点击回调
 * @param onAlipayLoginClick 支付宝登录点击回调
 * @param onQQLogin QQ 登录回调
 * @param onUserAgreementClick 用户协议点击回调
 * @param onPrivacyPolicyClick 隐私政策点击回调
 * @author Joker.X
 */
@Composable
private fun LoginContentView(
    toAccountLogin: () -> Unit,
    toSmsLogin: () -> Unit,
    onWechatLoginClick: () -> Unit,
    onAlipayLoginClick: () -> Unit,
    onQQLogin: () -> Unit,
    onUserAgreementClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
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
                AppButton(
                    text = stringResource(id = R.string.sms_login),
                    onClick = toSmsLogin
                )

                SpaceVerticalXLarge()

                // 账号密码登录按钮（次要按钮）
                AppButton(
                    text = stringResource(id = R.string.account_login),
                    onClick = toAccountLogin,
                    style = ButtonStyle.OUTLINED
                )

                Spacer(modifier = Modifier.height(48.dp))

                AppText(
                    text = stringResource(id = R.string.other_login_methods),
                    size = TextSize.BODY_MEDIUM,
                    type = TextType.TERTIARY
                )

                SpaceVerticalXLarge()

                // 第三方登录按钮
                SpaceEvenlyRow {
                    // 微信登录
                    ThirdPartyLoginButton(
                        icon = com.joker.coolmall.core.ui.R.drawable.ic_wechat,
                        name = stringResource(id = R.string.wechat),
                        onClick = onWechatLoginClick
                    )

                    // QQ登录
                    ThirdPartyLoginButton(
                        icon = com.joker.coolmall.core.ui.R.drawable.ic_qq,
                        name = stringResource(id = R.string.qq),
                        onClick = onQQLogin
                    )

                    // 支付宝登录
                    ThirdPartyLoginButton(
                        icon = com.joker.coolmall.core.ui.R.drawable.ic_alipay,
                        name = stringResource(id = R.string.alipay),
                        onClick = onAlipayLoginClick
                    )
                }

                // 用户协议
                UserAgreement(
                    modifier = Modifier.padding(top = 32.dp),
                    centerAlignment = true,
                    onUserAgreementClick = onUserAgreementClick,
                    onPrivacyPolicyClick = onPrivacyPolicyClick
                )
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
 * @author Joker.X
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

        AppText(
            text = name,
            size = TextSize.BODY_MEDIUM,
            type = TextType.TERTIARY
        )
    }
}

/**
 * 登录主页界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen()
    }
}