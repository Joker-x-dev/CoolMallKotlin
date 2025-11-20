package com.joker.coolmall.feature.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.PrimaryDefault
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.feature.main.model.TopLevelDestination

/**
 * 底部导航栏
 *
 * @param destinations 底部导航栏目的地列表
 * @param onNavigateToDestination 点击导航项的回调
 * @param currentPageIndex 当前选中的页面索引
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
fun BottomNavigationBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (Int) -> Unit,
    currentPageIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {
        destinations.forEachIndexed { index, destination ->
            val selected = index == currentPageIndex

            var isPressed by remember { mutableStateOf(false) }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPressed = true
                                tryAwaitRelease()
                                isPressed = false
                            },
                            onTap = {
                                onNavigateToDestination(index)
                            }
                        )
                    }
                    .padding(vertical = SpaceVerticalXSmall)
            ) {
                // Lottie动画部分
                TabLottieAnimation(
                    animRes = destination.animationResId,
                    isSelected = selected
                )

                Text(
                    text = stringResource(id = destination.titleTextId),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (selected) PrimaryDefault else MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.5f
                    )
                )
            }
        }
    }
}

/**
 * 底部导航栏Lottie动画
 *
 * @param animRes 动画资源ID
 * @param isSelected 是否选中
 * @author Joker.X
 */
@Composable
private fun TabLottieAnimation(
    animRes: Int,
    isSelected: Boolean,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animRes))

    // 如果选中，播放动画；如果未选中，不播放动画但保持在初始帧
    val progress by animateLottieCompositionAsState(
        composition = composition,
        // 只有选中时才播放
        isPlaying = isSelected,
    )

    LottieAnimation(
        composition = composition,
        // 如果未选中，强制显示第一帧
        progress = { if (!isSelected) 0f else progress },
        modifier = Modifier.size(30.dp)
    )
}

/**
 * 底部导航栏预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun MyNavigationBarPreview() {
    AppTheme {
        BottomNavigationBar(
            destinations = TopLevelDestination.entries,
            onNavigateToDestination = {},
            currentPageIndex = 0
        )
    }
}
