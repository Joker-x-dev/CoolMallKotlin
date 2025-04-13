package com.joker.coolmall.core.ui.component.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar

/**
 * 应用通用Scaffold组件
 *
 * 该组件封装了Material3 Scaffold，使用CenterTopAppBar作为顶部应用栏，
 * 提供了一个统一的页面框架结构，简化常见页面布局的创建。
 * 默认使用Box布局并处理安全区域padding。
 *
 * @param modifier 应用于Scaffold的修饰符
 * @param title 顶部应用栏标题的资源ID，如果为null则不显示标题
 * @param topBarColors 顶部应用栏的颜色配置
 * @param topBarActions 顶部应用栏右侧的操作按钮区域
 * @param showBackIcon 是否显示返回按钮，默认为true
 * @param onBackClick 点击返回按钮时的回调函数
 * @param snackbarHostState Snackbar宿主状态
 * @param backgroundColor 页面背景颜色，默认为 MaterialTheme 的 surface 颜色
 * @param bottomBar 底部导航栏的内容，默认为null
 * @param floatingActionButton 浮动操作按钮的内容，默认为null
 * @param content 页面主体内容，接收PaddingValues参数以适应顶部应用栏的空间
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    title: Int? = null,
    topBarColors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    topBarActions: @Composable (RowScope.() -> Unit) = {},
    showBackIcon: Boolean = true,
    onBackClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterTopAppBar(
                title = title,
                colors = topBarColors,
                actions = topBarActions,
                onBackClick = onBackClick,
                showBackIcon = showBackIcon
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        modifier = modifier,
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(paddingValues)
            ) {
                content(paddingValues)
            }
        }
    )
}