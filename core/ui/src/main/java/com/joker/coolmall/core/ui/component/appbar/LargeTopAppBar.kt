package com.joker.coolmall.core.ui.component.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.theme.ArrowLeftIcon

/**
 * 大标题顶部应用栏组件
 *
 * 该组件是对 Material3 MediumTopAppBar 的封装，提供了一个带有动态字体大小的大标题顶部应用栏，
 * 支持滚动时标题字体大小的动态变化。主要用于页面主标题。
 *
 * @param title 顶部应用栏标题的资源ID，如果为null则不显示标题
 * @param titleText 顶部应用栏标题的字符串，如果为null则不显示标题
 * @param actions 顶部应用栏右侧的操作按钮区域
 * @param onBackClick 点击返回按钮时的回调函数
 * @param showBackIcon 是否显示返回按钮，默认为true
 * @param scrollBehavior 滚动行为，用于控制标题动画
 * @param expandedBackgroundColor 展开状态下的背景色，默认为 MaterialTheme.colorScheme.background
 * @param collapsedBackgroundColor 收起状态下的背景色，默认为 MaterialTheme.colorScheme.background
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeTopAppBar(
    title: Int? = null,
    titleText: String? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    onBackClick: () -> Unit = {},
    showBackIcon: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    expandedBackgroundColor: Color = MaterialTheme.colorScheme.background,
    collapsedBackgroundColor: Color = MaterialTheme.colorScheme.background
) {
    val scrollFraction = scrollBehavior?.state?.collapsedFraction ?: 0f
    val titleFontSize = lerp(
        start = 30.sp,
        stop = 16.sp,
        fraction = scrollFraction
    )

    // 根据滚动状态动态计算背景色
    val backgroundColor = lerp(
        start = expandedBackgroundColor,
        stop = collapsedBackgroundColor,
        fraction = scrollFraction
    )

    MediumTopAppBar(
        title = {
            val finalTitle = titleText ?: title?.let { stringResource(it) } ?: ""
            if (finalTitle.isNotBlank()) {
                Text(
                    text = finalTitle,
                    fontSize = titleFontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            if (showBackIcon) {
                IconButton(onClick = onBackClick) {
                    ArrowLeftIcon()
                }
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
        )
    )
}