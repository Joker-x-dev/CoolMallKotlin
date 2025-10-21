package com.joker.coolmall.feature.common.view

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.joker.coolmall.feature.common.viewmodel.UserAgreementViewModel

/**
 * 用户协议路由
 *
 * @param viewModel 用户协议 ViewModel
 * @author Joker.X
 */
@Composable
internal fun UserAgreementRoute(
    viewModel: UserAgreementViewModel = hiltViewModel()
) {
    // 从ViewModel收集UI状态
    val uiState by viewModel.uiState.collectAsState()

    UserAgreementScreen(
        uiState = uiState,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
    )
}

/**
 * 用户协议界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserAgreementScreen(
    uiState: BaseNetWorkUiState<String> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.user_agreement_title,
        onBackClick = onBackClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) { html ->
            UserAgreementContentView(html = html)
        }
    }
}

/**
 * 用户协议内容视图
 *
 * @param html HTML内容
 * @author Joker.X
 */
@Composable
private fun UserAgreementContentView(html: String) {
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
 * 用户协议界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun UserAgreementScreenPreview() {
    AppTheme {
        UserAgreementScreen(
            uiState = BaseNetWorkUiState.Success(
                "1"
            )
        )
    }
}

/**
 * 用户协议界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun UserAgreementScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        UserAgreementScreen(
            uiState = BaseNetWorkUiState.Success(
                "1"
            )
        )
    }
}