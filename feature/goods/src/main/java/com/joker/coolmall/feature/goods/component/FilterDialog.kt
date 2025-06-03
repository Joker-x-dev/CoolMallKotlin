package com.joker.coolmall.feature.goods.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeLarge
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize

/**
 * 筛选对话框
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedVisibilityScope 动画可见性作用域
 * @param onDismiss 关闭对话框回调
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilterDialog(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {},
    ) {
        // 背景遮罩
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    onDismiss()
                },
        )

        with(sharedTransitionScope) {
            Column(
                modifier = Modifier
                    .padding(SpacePaddingLarge)
                    .align(Alignment.Center)
                    .clip(ShapeLarge)
                    .sharedBounds(
                        rememberSharedContentState("filter_button"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                        clipInOverlayDuringTransition = OverlayClip(ShapeLarge),
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ) { /* 阻止点击穿透 */ },
            ) {
                // 标题栏
                SpaceBetweenRow {
                    AppText(
                        text = "筛选",
                        size = TextSize.TITLE_LARGE
                    )

                    Box(
                        modifier = Modifier
                            .clip(ShapeMedium)
                            .clickable { onDismiss() }
                            .padding(8.dp)
                    ) {
                        CommonIcon(
                            imageVector = Icons.Default.Close,
                            size = 24.dp,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                SpaceVerticalLarge()

                // 筛选内容
                Column {
                    AppText("内容")
                }
            }
        }
    }
}