package com.joker.coolmall.feature.common.view

import android.content.Intent
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.FullScreenBox
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.common.model.WebViewData
import com.joker.coolmall.feature.common.viewmodel.WebViewModel

/**
 * 网页路由
 *
 * @param viewModel 网页 ViewModel
 */
@Composable
internal fun WebRoute(
    viewModel: WebViewModel = hiltViewModel()
) {
    val webViewData by viewModel.webViewData.collectAsState()
    val pageTitle by viewModel.pageTitle.collectAsState()
    val currentProgress by viewModel.currentProgress.collectAsState()

    WebScreen(
        webViewData = webViewData,
        pageTitle = pageTitle,
        currentProgress = currentProgress,
        onBackClick = viewModel::navigateBack,
        onTitleChange = viewModel::updatePageTitle,
        onProgressChange = viewModel::updateProgress
    )
}

/**
 * 网页界面
 *
 * @param webViewData WebView 数据
 * @param pageTitle 页面标题
 * @param currentProgress 当前加载进度
 * @param onBackClick 返回按钮回调
 * @param onTitleChange 标题变化回调
 * @param onProgressChange 进度变化回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WebScreen(
    webViewData: WebViewData = WebViewData(),
    pageTitle: String = "网页",
    currentProgress: Int = 0,
    onBackClick: () -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    onProgressChange: (Int) -> Unit = {}
) {
    AppScaffold(
        titleText = pageTitle,
        onBackClick = onBackClick
    ) {
        WebViewContent(
            url = webViewData.url,
            currentProgress = currentProgress,
            onTitleChange = onTitleChange,
            onProgressChange = onProgressChange
        )
    }
}

/**
 * WebView 内容组件
 */
@Composable
private fun WebViewContent(
    url: String,
    currentProgress: Int,
    onTitleChange: (String) -> Unit,
    onProgressChange: (Int) -> Unit
) {
    val context = LocalContext.current

    FullScreenBox {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
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
                                    // 如果无法打开，返回 false 让 WebView 处理
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