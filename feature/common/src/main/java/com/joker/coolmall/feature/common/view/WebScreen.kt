package com.joker.coolmall.feature.common.view

import android.content.Intent
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.FullScreenBox
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.common.R
import com.joker.coolmall.feature.common.model.WebViewData
import com.joker.coolmall.feature.common.viewmodel.WebViewModel

/**
 * 网页路由
 *
 * @param viewModel 网页 ViewModel
 * @author Joker.X
 */
@Composable
internal fun WebRoute(
    viewModel: WebViewModel = hiltViewModel()
) {
    // 收集WebView数据
    val webViewData by viewModel.webViewData.collectAsState()
    // 收集页面标题
    val pageTitle by viewModel.pageTitle.collectAsState()
    // 收集当前加载进度
    val currentProgress by viewModel.currentProgress.collectAsState()
    // 收集页面刷新状态
    val shouldRefresh by viewModel.shouldRefresh.collectAsState()
    // 收集下拉菜单显示状态
    val showDropdownMenu by viewModel.showDropdownMenu.collectAsState()

    WebScreen(
        webViewData = webViewData,
        pageTitle = pageTitle,
        currentProgress = currentProgress,
        shouldRefresh = shouldRefresh,
        showDropdownMenu = showDropdownMenu,
        onBackClick = viewModel::navigateBack,
        onTitleChange = viewModel::updatePageTitle,
        onProgressChange = viewModel::updateProgress,
        onRefreshClick = viewModel::refreshPage,
        onResetRefreshState = viewModel::resetRefreshState,
        onOpenInBrowser = viewModel::openInBrowser,
        onShowDropdownMenu = viewModel::showDropdownMenu,
        onDismissDropdownMenu = viewModel::dismissDropdownMenu
    )
}

/**
 * 网页界面
 *
 * @param webViewData WebView 数据
 * @param pageTitle 页面标题
 * @param currentProgress 当前加载进度
 * @param shouldRefresh 是否应该刷新页面
 * @param showDropdownMenu 是否显示下拉菜单
 * @param onBackClick 返回按钮回调
 * @param onTitleChange 标题变化回调
 * @param onProgressChange 进度变化回调
 * @param onRefreshClick 刷新按钮回调
 * @param onResetRefreshState 重置刷新状态回调
 * @param onOpenInBrowser 用浏览器打开回调
 * @param onShowDropdownMenu 显示下拉菜单回调
 * @param onDismissDropdownMenu 隐藏下拉菜单回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WebScreen(
    webViewData: WebViewData = WebViewData(),
    pageTitle: String = "",
    currentProgress: Int = 0,
    shouldRefresh: Boolean = false,
    showDropdownMenu: Boolean = false,
    onBackClick: () -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    onProgressChange: (Int) -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onResetRefreshState: () -> Unit = {},
    onOpenInBrowser: () -> Unit = {},
    onShowDropdownMenu: () -> Unit = {},
    onDismissDropdownMenu: () -> Unit = {}
) {
    AppScaffold(
        titleText = pageTitle,
        onBackClick = onBackClick,
        topBarActions = {
            WebScreenTopBarActions(
                showDropdownMenu = showDropdownMenu,
                onShowDropdownMenu = onShowDropdownMenu,
                onDismissDropdownMenu = onDismissDropdownMenu,
                onRefreshClick = onRefreshClick,
                onOpenInBrowser = onOpenInBrowser
            )
        }
    ) {
        WebViewContent(
            url = webViewData.url,
            currentProgress = currentProgress,
            shouldRefresh = shouldRefresh,
            onTitleChange = onTitleChange,
            onProgressChange = onProgressChange,
            onResetRefreshState = onResetRefreshState
        )
    }
}

/**
 * WebScreen 顶部栏操作按钮组件
 *
 * @param showDropdownMenu 是否显示下拉菜单
 * @param onShowDropdownMenu 显示下拉菜单回调
 * @param onDismissDropdownMenu 隐藏下拉菜单回调
 * @param onRefreshClick 刷新按钮回调
 * @param onOpenInBrowser 用浏览器打开回调
 * @author Joker.X
 */
@Composable
private fun WebScreenTopBarActions(
    showDropdownMenu: Boolean,
    onShowDropdownMenu: () -> Unit,
    onDismissDropdownMenu: () -> Unit,
    onRefreshClick: () -> Unit,
    onOpenInBrowser: () -> Unit
) {
    // 溢出菜单按钮
    IconButton(onClick = onShowDropdownMenu) {
        CommonIcon(
            resId = R.drawable.ic_more_vertical,
            contentDescription = stringResource(id = R.string.web_more_options)
        )
    }

    // 下拉菜单
    DropdownMenu(
        expanded = showDropdownMenu,
        onDismissRequest = onDismissDropdownMenu
    ) {
        // 刷新选项
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.web_menu_refresh)) },
            onClick = onRefreshClick
        )

        // 用浏览器打开选项
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.web_menu_open_browser)) },
            onClick = onOpenInBrowser
        )
    }
}

/**
 * WebView 内容组件
 *
 * @param url 要加载的网页URL
 * @param currentProgress 当前加载进度(0-100)
 * @param shouldRefresh 是否应该刷新页面
 * @param onTitleChange 标题变化回调
 * @param onProgressChange 进度变化回调
 * @param onResetRefreshState 重置刷新状态回调
 * @author Joker.X
 */
@Composable
private fun WebViewContent(
    url: String,
    currentProgress: Int,
    shouldRefresh: Boolean,
    onTitleChange: (String) -> Unit,
    onProgressChange: (Int) -> Unit,
    onResetRefreshState: () -> Unit
) {
    var webView by remember { mutableStateOf<WebView?>(null) }

    // 处理刷新逻辑
    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            webView?.reload()
            onResetRefreshState()
        }
    }

    FullScreenBox {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webView = this
                    settings.apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        // 安全设置
                        javaScriptEnabled = true
                        loadsImagesAutomatically = true
                        useWideViewPort = true
                        loadWithOverviewMode = true
                        setSupportZoom(true)
                        builtInZoomControls = true
                        displayZoomControls = false
                        setSupportMultipleWindows(false)
                        javaScriptCanOpenWindowsAutomatically = true
                        domStorageEnabled = true
                        safeBrowsingEnabled = true
                        mediaPlaybackRequiresUserGesture = false
                    }

                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                            // 如果是 HTTP 或 HTTPS 链接，在 WebView 中加载
                            if (url.startsWith("http://") || url.startsWith("https://")) {
                                view.loadUrl(url)
                                return false
                            } else {
                                // 对于其他类型的链接（如 tel:, mailto:, geo: 等），使用系统应用打开
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                                    context.startActivity(intent)
                                    return true
                                } catch (_: Exception) {
                                    // 如果无法打开浏览器的情况
                                    return false
                                }
                            }
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onReceivedTitle(view: WebView?, title: String?) {
                            super.onReceivedTitle(view, title)
                            title?.let { onTitleChange(it) }
                        }

                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            onProgressChange(newProgress)
                        }
                    }

                    // 加载 URL
                    loadUrl(url)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // 进度条 - 显示在 WebView 上方
        if (currentProgress < 100) {
            LinearProgressIndicator(
                progress = { currentProgress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp)
            )
        }
    }
}

/**
 * 网页界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun WebScreenPreview() {
    AppTheme {
        WebScreen(
            webViewData = WebViewData(
                url = "https://www.example.com",
                title = "示例网页"
            ),
            pageTitle = "示例网页",
            currentProgress = 50
        )
    }
}

/**
 * 网页界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun WebScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        WebScreen(
            webViewData = WebViewData(
                url = "https://www.example.com",
                title = "示例网页"
            ),
            pageTitle = "示例网页",
            currentProgress = 50
        )
    }
} 