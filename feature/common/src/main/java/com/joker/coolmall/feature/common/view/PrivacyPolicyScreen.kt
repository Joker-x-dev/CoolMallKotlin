package com.joker.coolmall.feature.common.view

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.common.R
import com.joker.coolmall.feature.common.viewmodel.PrivacyPolicyViewModel

/**
 * 隐私政策路由
 *
 * @param viewModel 隐私政策 ViewModel
 * @author Joker.X
 */
@Composable
internal fun PrivacyPolicyRoute(
    viewModel: PrivacyPolicyViewModel = hiltViewModel()
) {
    // 从ViewModel收集UI状态
    val uiState by viewModel.uiState.collectAsState()

    PrivacyPolicyScreen(
        uiState = uiState,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
    )
}

/**
 * 隐私政策界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PrivacyPolicyScreen(
    uiState: BaseNetWorkUiState<String> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.privacy_policy_title,
        onBackClick = onBackClick
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) { html ->
            PrivacyPolicyContentView(html = html)
        }
    }
}

/**
 * 隐私政策内容视图
 *
 * @param html HTML 富文本内容
 * @author Joker.X
 */
@Composable
private fun PrivacyPolicyContentView(html: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    // 基础设置
                    // 不需要 JavaScript
                    javaScriptEnabled = false
                    loadsImagesAutomatically = true
                    useWideViewPort = true
                    loadWithOverviewMode = true
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = false
                    // 不需要存储
                    domStorageEnabled = false
                    safeBrowsingEnabled = true
                }

                // 加载 HTML 内容
                loadDataWithBaseURL(
                    null,
                    html,
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

/**
 * 隐私政策界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun PrivacyPolicyScreenPreview() {
    AppTheme {
        PrivacyPolicyScreen(
            uiState = BaseNetWorkUiState.Success(
                data = "1"
            )
        )
    }
}

/**
 * 隐私政策界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun PrivacyPolicyScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        PrivacyPolicyScreen(
            uiState = BaseNetWorkUiState.Success(
                data = "1"
            )
        )
    }
}