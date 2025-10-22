package com.joker.coolmall.feature.launch.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXXLarge
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.launch.R
import com.joker.coolmall.feature.launch.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

/**
 * 启动页路由
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param viewModel 启动页 ViewModel
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SplashRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: SplashViewModel = hiltViewModel()
) {
    SplashScreen(
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        toHome = viewModel::checkGuideStatusAndNavigate
    )
}

/**
 * 启动页界面
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param toHome 导航到主页的回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun SplashScreen(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    toHome: () -> Unit = {}
) {
    SplashContentView(
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        toHome = toHome
    )
}

/**
 * 启动页内容视图
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param toHome 导航到主页的回调
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SplashContentView(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    toHome: () -> Unit = {}
) {
    // 动画状态
    var startAnimation by rememberSaveable { mutableStateOf(false) }

    // 启动动画和导航逻辑
    LaunchedEffect(Unit) {
        // 立即启动动画
        startAnimation = true
        delay(1200)
        toHome()
    }

    // Logo Y偏移
    val logoOffsetY by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 160.dp,
        animationSpec = tween(durationMillis = 1000),
        label = "logoOffsetY"
    )

    // Logo 旋转
    val logoRotation by animateFloatAsState(
        targetValue = if (startAnimation) 0f else -15f,
        animationSpec = tween(durationMillis = 1000),
        label = "logoRotation"
    )

    // Logo 透明度
    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "logoAlpha"
    )

    // 内容透明度动画
    val textAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800, delayMillis = 400),
        label = "textAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center,
    ) {
        // 背景模糊Logo
        BackgroundLogoSection()

        // 前景内容
        ForegroundContentSection(
            logoOffsetY = logoOffsetY,
            logoRotation = logoRotation,
            logoAlpha = logoAlpha,
            textAlpha = textAlpha,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope
        )
    }
}

/**
 * 背景Logo区域
 *
 * @author Joker.X
 */
@Composable
private fun BackgroundLogoSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .blur(radius = 100.dp)
            .alpha(0.8f)
    ) {
        // 背景大Logo
        LogoIcon(size = 240.dp)
    }
}

/**
 * 前景内容区域
 *
 * @param logoOffsetY Logo Y 轴偏移
 * @param logoRotation Logo 旋转角度
 * @param logoAlpha Logo 透明度
 * @param textAlpha 文本透明度
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ForegroundContentSection(
    logoOffsetY: Dp = 0.dp,
    logoRotation: Float = 0f,
    logoAlpha: Float = 1f,
    textAlpha: Float = 1f,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 上方空白区域，将Logo推向中心
        Spacer(modifier = Modifier.weight(1f))

        // 前景Logo
        LogoIcon(
            modifier = Modifier
                .offset(y = logoOffsetY)
                .rotate(logoRotation)
                .alpha(logoAlpha)
                .let { modifier ->
                    if (sharedTransitionScope != null && animatedContentScope != null) {
                        with(sharedTransitionScope) {
                            modifier.sharedElement(
                                sharedContentState = rememberSharedContentState(key = "app_logo"),
                                animatedVisibilityScope = animatedContentScope
                            )
                        }
                    } else {
                        modifier
                    }
                },
            size = 120.dp
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.alpha(textAlpha),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppText(
                text = stringResource(R.string.app_name_splash),
                size = TextSize.TITLE_LARGE,
                color = MaterialTheme.colorScheme.onSurface
            )

            SpaceVerticalMedium()

            // 底部应用信息
            AppText(
                text = stringResource(R.string.app_copyright),
                size = TextSize.BODY_MEDIUM,
                type = TextType.TERTIARY,
            )
        }

        // 底部边距
        SpaceVerticalXXLarge()
    }
}

/**
 * 启动页界面浅色主题预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
internal fun SplashScreenPreview() {
    AppTheme {
        SplashScreen(
            sharedTransitionScope = null,
            animatedContentScope = null
        )
    }
}

/**
 * 启动页界面深色主题预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
internal fun SplashScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        SplashScreen(
            sharedTransitionScope = null,
            animatedContentScope = null
        )
    }
}