package com.joker.coolmall.core.ui.component.loading

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.ui.R
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 加载中动画图标
 *
 * @param size 图标大小，默认16.dp
 * @param color 图标颜色，默认使用未指定颜色
 * @param isRotating 是否启用旋转动画，默认为true
 */
@Composable
fun WeLoading(size: Dp = 16.dp, color: Color = Color.Unspecified, isRotating: Boolean = true) {
    val angle by if (isRotating) {
        val transition = rememberInfiniteTransition(label = "")
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 1000, easing = LinearEasing),
                RepeatMode.Restart
            ),
            label = "WeLoadingAnimation"
        )
    } else {
        remember { mutableFloatStateOf(0f) }
    }

    Icon(
        painter = painterResource(id = R.drawable.ic_loading),
        contentDescription = stringResource(id = R.string.loading),
        modifier = Modifier
            .size(size)
            .rotate(angle),
        tint = color
    )
}

/**
 * 多点加载动画 - 3个圆点依次高亮显示
 *
 * @param color 圆点颜色，默认使用outline颜色
 * @param animationSpec 动画规格配置，默认1000ms
 */
@Composable
fun WeLoadingMP(
    color: Color = MaterialTheme.colorScheme.outline,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(durationMillis = 1000)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val currentIndex by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = animationSpec,
            repeatMode = RepeatMode.Restart
        ),
        label = "WeLoadingMPAnimation"
    )

    Canvas(modifier = Modifier.size(width = 44.dp, height = 20.dp)) {
        val dotRadius = 4.dp.toPx()
        val spacing = (size.width - 2 * dotRadius) / 2

        repeat(3) { index ->
            val isActive = index == currentIndex.roundToInt()
            val dotColor = color.copy(alpha = if (isActive) 0.8f else 0.4f)
            val center = Offset(
                x = dotRadius + spacing * index,
                y = size.height / 2
            )

            drawCircle(
                color = dotColor,
                radius = dotRadius,
                center = center
            )
        }
    }
}

/**
 * 小米风格Web加载动画 - 3条竖线交替缩放
 *
 * @param color 竖线颜色，默认使用Primary主题色
 */
@Composable
fun MiLoadingWeb(color: Color = Primary) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animations = List(3) { index ->
        val alpha by infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 400,
                    delayMillis = index * 100,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "MiLoadingWebAlphaAnimation"
        )
        val scaleY by infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 400,
                    delayMillis = index * 100,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "MiLoadingWebScaleAnimation"
        )
        Pair(alpha, scaleY)
    }

    Canvas(modifier = Modifier.size(24.dp)) {
        animations.forEachIndexed { index, item ->
            val strokeWidth = 4.dp.toPx()
            val spacing = (size.width - (3 * strokeWidth)) / 2

            scale(scaleX = 1f, scaleY = item.second) {
                drawLine(
                    color = color.copy(alpha = item.first),
                    start = Offset(
                        x = strokeWidth / 2 + (strokeWidth + spacing) * index,
                        y = 0f
                    ),
                    end = Offset(
                        x = strokeWidth / 2 + (strokeWidth + spacing) * index,
                        y = size.height
                    ),
                    strokeWidth
                )
            }
        }
    }
}

/**
 * 小米风格移动端加载动画 - 圆形轨道上的圆点旋转
 *
 * @param borderColor 圆形轨道边框颜色，默认使用onSurface颜色
 * @param dotColor 旋转圆点的颜色，默认与边框颜色相同
 * @param animationSpec 动画规格配置，默认1200ms线性动画
 */
@Composable
fun MiLoadingMobile(
    borderColor: Color = MaterialTheme.colorScheme.onSurface,
    dotColor: Color = borderColor,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(
        durationMillis = 1200,
        easing = LinearEasing
    )
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = animationSpec,
            repeatMode = RepeatMode.Restart
        ),
        label = "MiLoadingMobileAnimation"
    )

    Canvas(
        modifier = Modifier
            .size(28.dp)
            .border(2.dp, borderColor, CircleShape)
    ) {
        val circleRadius = size.minDimension / 2 - 8.dp.toPx()
        val dotRadius = 3.dp.toPx()
        val center = size.center
        val dotX = cos(Math.toRadians(angle.value.toDouble())) * circleRadius + center.x
        val dotY = sin(Math.toRadians(angle.value.toDouble())) * circleRadius + center.y

        drawCircle(dotColor, radius = dotRadius, center = Offset(dotX.toFloat(), dotY.toFloat()))
    }
}

/**
 * 跳舞圆点加载动画 - 3个圆点依次上下跳动
 *
 * @param color 圆点颜色，默认使用Primary主题色
 */
@Composable
fun DotDanceLoading(color: Color = Primary) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val offsetY = LocalDensity.current.run { 3.dp.toPx() }
    val animations = List(3) { index ->
        val value by infiniteTransition.animateFloat(
            initialValue = -offsetY,
            targetValue = offsetY,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 400,
                    delayMillis = index * 100,
                    easing = FastOutLinearInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "DotDanceLoadingAnimation"
        )
        value
    }

    Canvas(modifier = Modifier.size(width = 44.dp, height = 20.dp)) {
        val dotRadius = 4.dp.toPx()
        val spacing = (size.width - 2 * dotRadius) / 2

        animations.forEachIndexed { index, item ->
            val center = Offset(
                x = dotRadius + spacing * index,
                y = size.height / 2 - item
            )

            drawCircle(
                color,
                radius = dotRadius,
                center = center
            )
        }
    }
}

/**
 * Lottie动画加载组件 - 使用Lottie动画文件
 *
 * @param modifier 修饰符，用于自定义组件样式
 */
/*@Composable
fun LottieLoading(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    LottieAnimation(
        composition,
        // 循环播放
        iterations = LottieConstants.IterateForever,
        modifier = modifier
            .size(80.dp),
    )
}*/

/**
 * WeLoading组件预览
 */
@Preview(showBackground = true)
@Composable
fun WeLoadingPreview() {
    AppTheme {
        WeLoading()
    }
}

/**
 * WeLoadingMP组件预览
 */
@Preview(showBackground = true)
@Composable
fun WeLoadingMPPreview() {
    AppTheme {
        WeLoadingMP()
    }
}

/**
 * MiLoadingWeb组件预览
 */
@Preview(showBackground = true)
@Composable
fun MiLoadingWebPreview() {
    AppTheme {
        MiLoadingWeb()
    }
}

/**
 * 小米风格移动端加载动画
 */
@Preview(showBackground = true)
@Composable
fun MiLoadingMobilePreview() {
    AppTheme {
        MiLoadingMobile()
    }
}

/**
 * DotDanceLoading组件预览
 */
@Preview(showBackground = true)
@Composable
fun DotDanceLoadingPreview() {
    AppTheme {
        DotDanceLoading()
    }
}

/**
 * LottieLoading组件预览
 */
/*
@Preview(showBackground = true)
@Composable
fun LottieLoadingPreview() {
    AppTheme {
        LottieLoading()
    }
}*/
