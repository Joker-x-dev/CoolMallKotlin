package com.joker.coolmall.core.ui.component.refresh

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.loading.MiLoadingMobile
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 刷新指示器组件
 *
 * 使用 MiLoadingMobile 风格：
 * 1. 下拉时：显示圆形轨道和圆点，圆点随下拉距离旋转。
 * 2. 刷新时：显示 MiLoadingMobile 动画（自动旋转）。
 * 3. 添加文本提示：下拉刷新 / 松开刷新 / 刷新中... / 刷新完成
 *
 * @param modifier 修饰符
 * @param state 刷新状态
 * @author Joker.X
 */
@Composable
fun RefreshHeader(
    modifier: Modifier = Modifier,
    state: RefreshState
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val threshold = state.headerHeight
        val offset = state.indicatorOffset
        val isRefreshing = state.isRefreshing
        val isFinishing = state.isFinishing

        // 文本提示
        val text = when {
            isRefreshing -> stringResource(id = R.string.refresh_refreshing)
            isFinishing -> stringResource(id = R.string.refresh_complete)
            offset > threshold -> stringResource(id = R.string.refresh_release)
            else -> stringResource(id = R.string.refresh_pull_down)
        }

        // 边框颜色
        val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // 图标区域
            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isRefreshing || isFinishing) {
                    // 刷新中或结束延迟中：显示 MiLoadingMobile 动画
                    MiLoadingMobile(borderColor = borderColor)
                } else {
                    // 固定角度，保持在顶部或初始位置 (例如 -90度为顶部，0度为右侧)
                    // MiLoadingMobile 动画是从 0 度开始的
                    val angle = 0f
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                2.dp,
                                borderColor,
                                androidx.compose.foundation.shape.CircleShape
                            )
                    ) {
                        val circleRadius = size.minDimension / 2 - 8.dp.toPx()
                        val dotRadius = 3.dp.toPx()
                        val center = center

                        val dotX =
                            kotlin.math.cos(Math.toRadians(angle.toDouble())) * circleRadius + center.x
                        val dotY =
                            kotlin.math.sin(Math.toRadians(angle.toDouble())) * circleRadius + center.y

                        drawCircle(
                            borderColor,
                            radius = dotRadius,
                            center = Offset(dotX.toFloat(), dotY.toFloat())
                        )
                    }
                }
            }

            SpaceHorizontalSmall()

            AppText(
                text = text,
                type = TextType.SECONDARY,
                size = TextSize.BODY_MEDIUM,
            )
        }
    }
}
