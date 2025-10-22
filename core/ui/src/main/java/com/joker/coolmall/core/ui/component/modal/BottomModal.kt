package com.joker.coolmall.core.ui.component.modal

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.TitleLarge
import kotlinx.coroutines.launch

/**
 * 底部弹出层Modal组件
 *
 * 基于Material3的ModalBottomSheet封装，提供统一的样式和行为。
 *
 * @param visible 是否显示
 * @param title 标题，如果为null则不显示标题
 * @param onDismiss 关闭回调
 * @param sheetState 底部弹出层状态
 * @param showDragIndicator 是否显示顶部拖动指示器
 * @param horizontalPadding 水平内边距
 * @param containerColor 容器背景色
 * @param shape 容器形状
 * @param titleStyle 标题样式
 * @param indicatorColor 指示条颜色
 * @param content 内容
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomModal(
    visible: Boolean,
    title: String? = null,
    onDismiss: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    showDragIndicator: Boolean = true,
    horizontalPadding: Dp = SpacePaddingLarge,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    shape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    titleStyle: TextStyle = TitleLarge,
    indicatorColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.8f),
    content: @Composable ColumnScope.() -> Unit
) {
    // 添加协程作用域用于动画控制
    val coroutineScope = rememberCoroutineScope()

    // 监听visible变化，处理关闭动画
    LaunchedEffect(visible) {
        if (!visible && sheetState.isVisible) {
            coroutineScope.launch {
                sheetState.hide()
            }
        }
    }

    // 计算安全区域
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    // 获取屏幕高度（dp）
    val screenHeightDp = configuration.screenHeightDp.dp

    // 获取顶部安全区域高度（状态栏和刘海屏）
    val statusBarHeightPx = WindowInsets.statusBars.getTop(density)
    val cutoutHeightPx = WindowInsets.displayCutout.getTop(density)
    val topInsetDp = with(density) {
        maxOf(statusBarHeightPx, cutoutHeightPx).toDp()
    }

    // 获取底部安全区域高度（导航栏）
    val bottomInsetDp = with(density) {
        WindowInsets.navigationBars.getBottom(density).toDp()
    }

    // 计算安全内容区域高度
    // 屏幕高度 - 顶部安全区域 - 底部安全区域 - 额外安全边距(36dp)
    val maxSafeContentHeight = screenHeightDp - topInsetDp - bottomInsetDp - 36.dp

    if (visible || sheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = containerColor,
            shape = shape,
            // 关闭系统默认的拖动指示器
            dragHandle = {
                if (showDragIndicator) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = SpacePaddingLarge),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp, 4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(indicatorColor)
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .wrapContentHeight()
                    // 使用计算出的安全区域高度
                    .heightIn(max = maxSafeContentHeight)
                    .animateContentSize()
            ) {
                // 标题
                title?.let {
                    Text(
                        text = it,
                        style = titleStyle,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    SpaceVerticalLarge()
                }

                // 内容
                content()

                // 底部间距，确保内容不会被系统导航栏遮挡
                SpaceVerticalMedium()
            }
        }
    }
} 