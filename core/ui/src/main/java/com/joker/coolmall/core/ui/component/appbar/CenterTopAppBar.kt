package com.joker.coolmall.core.ui.component.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview


/**
 * 居中对齐的顶部应用栏组件
 *
 * 该组件是对 Material3 CenterAlignedTopAppBar 的封装，提供了一个居中标题的顶部应用栏，
 * 支持显示返回按钮、自定义操作按钮等功能。主要用于二级页面的导航栏。
 *
 * @param title 顶部应用栏标题的资源ID，如果为null则不显示标题
 * @param colors 顶部应用栏的颜色配置，默认使用 Material3 的居中对齐顶部应用栏颜色
 * @param actions 顶部应用栏右侧的操作按钮区域，默认为空
 * @param onBackClick 点击返回按钮时的回调函数
 * @param showBackIcon 是否显示返回按钮，默认为true
 *
 * @sample CenterTopAppBarPreview
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopAppBar(
    title: Int? = null,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    actions: @Composable (RowScope.() -> Unit) = {},
    onBackClick: () -> Unit = {},
    showBackIcon: Boolean = true
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            if (showBackIcon) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        title = {
            val title = title?.let { stringResource(it) } ?: ""
            if (title.isNotBlank()) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        actions = actions,
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CenterTopAppBarPreview() {
    CenterTopAppBar()
}