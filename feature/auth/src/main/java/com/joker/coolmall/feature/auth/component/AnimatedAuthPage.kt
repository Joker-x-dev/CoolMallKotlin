package com.joker.coolmall.feature.auth.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.component.TopColumn
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXXLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXXLarge
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold

/**
 * 带动画效果的认证页面框架组件，包含AppScaffold、AnimatedVisibility、TopColumn和标题文字
 *
 * @param title 页面标题
 * @param modifier 可选修饰符
 * @param withFadeIn 是否使用淡入效果，默认为false
 * @param onBackClick 返回按钮点击回调，默认为null（不显示返回按钮）
 * @param content 页面内容
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedAuthPage(
    title: String,
    modifier: Modifier = Modifier,
    withFadeIn: Boolean = false,
    onBackClick: (() -> Unit) = {},
    content: @Composable () -> Unit
) {
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
        backgroundColor = MaterialTheme.colorScheme.surface,
        onBackClick = onBackClick
    ) {
        AnimatedVisibility(
            visibleState = animationState,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 400)
            )
        ) {
            TopColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = SpaceHorizontalXXLarge)
                    .padding(top = SpaceVerticalXXLarge),
            ) {
                // 标题部分
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Spacer(modifier = Modifier.height(64.dp))

                // 内容部分
                content()
            }
        }
    }
} 