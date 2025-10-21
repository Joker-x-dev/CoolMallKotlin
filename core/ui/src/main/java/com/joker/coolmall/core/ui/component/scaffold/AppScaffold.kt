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
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.core.ui.component.appbar.LargeTopAppBar

/**
 * 应用通用Scaffold组件
 *
 * 该组件封装了Material3 Scaffold，使用CenterTopAppBar作为顶部应用栏，
 * 提供了一个统一的页面框架结构，简化常见页面布局的创建。
 * 默认情况下，它会自动处理因顶部和底部栏产生的安全区域padding。
 *
 * @param modifier 应用于Scaffold的修饰符
 * @param title 顶部应用栏标题的资源ID，如果为null则不显示标题
 * @param titleText 顶部应用栏标题的字符串，如果为null则不显示标题
 * @param topBarColors 顶部应用栏的颜色配置
 * @param topBarActions 顶部应用栏右侧的操作按钮区域
 * @param showBackIcon 是否显示返回按钮，默认为true
 * @param onBackClick 点击返回按钮时的回调函数
 * @param snackbarHostState Snackbar宿主状态
 * @param backgroundColor 页面背景颜色，默认为 MaterialTheme 的 surface 颜色
 * @param bottomBar 底部导航栏的内容，默认为null
 * @param floatingActionButton 浮动操作按钮的内容，默认为null
 * @param topBar 自定义顶部应用栏，如果提供则优先使用此应用栏
 * @param useLargeTopBar 是否使用大标题样式的顶部应用栏，如果为true则会自动启用滚动行为
 * @param largeTopBarExpandedBackgroundColor 大标题应用栏展开状态下的背景色，仅在useLargeTopBar为true时生效
 * @param largeTopBarCollapsedBackgroundColor 大标题应用栏收起状态下的背景色，仅在useLargeTopBar为true时生效
 * @param contentShouldConsumePadding 是否由内容区域(content)来消费padding。默认为false，即Scaffold的根Box会消费padding。
 *                                    如果设为true，根Box将不应用padding，而是由content自行处理。
 * @param content 页面主体内容，接收PaddingValues参数以适应顶部应用栏的空间
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    title: Int? = null,
    titleText: String? = null,
    topBarColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    topBarActions: @Composable (RowScope.() -> Unit) = {},
    showBackIcon: Boolean = true,
    onBackClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    topBar: @Composable (() -> Unit)? = null,
    useLargeTopBar: Boolean = false,
    largeTopBarExpandedBackgroundColor: Color = MaterialTheme.colorScheme.background,
    largeTopBarCollapsedBackgroundColor: Color = MaterialTheme.colorScheme.background,
    contentShouldConsumePadding: Boolean = false, // New parameter
    content: @Composable (PaddingValues) -> Unit
) {
    val scrollBehavior = if (useLargeTopBar) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    } else null

    val finalModifier = if (scrollBehavior != null) {
        modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    } else modifier

    Scaffold(
        topBar = {
            if (topBar != null) {
                topBar()
            } else if (useLargeTopBar) {
                LargeTopAppBar(
                    title = title,
                    titleText = titleText,
                    actions = topBarActions,
                    onBackClick = onBackClick,
                    showBackIcon = showBackIcon,
                    scrollBehavior = scrollBehavior,
                    expandedBackgroundColor = largeTopBarExpandedBackgroundColor,
                    collapsedBackgroundColor = largeTopBarCollapsedBackgroundColor
                )
            } else {
                CenterTopAppBar(
                    title = title,
                    titleText = titleText,
                    colors = topBarColors,
                    actions = topBarActions,
                    onBackClick = onBackClick,
                    showBackIcon = showBackIcon
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        modifier = finalModifier,
        content = { paddingValues ->
            val boxModifier = if (contentShouldConsumePadding) {
                Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            } else {
                Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(paddingValues)
            }
            Box(
                modifier = boxModifier
            ) {
                content(paddingValues)
            }
        }
    )
}