package com.joker.coolmall.core.ui.component.empty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.R

/**
 * 网络连接失败状态视图
 *
 * @param modifier 修饰符
 * @param onRetryClick 重试点击回调
 * @author Joker.X
 */
@Composable
fun EmptyNetwork(
    modifier: Modifier = Modifier,
    onRetryClick: (() -> Unit)? = null
) {
    Empty(
        modifier = modifier,
        message = R.string.empty_network,
        subtitle = R.string.empty_network_subtitle,
        icon = R.drawable.ic_empty_network,
        retryButtonText = R.string.click_retry,
        onRetryClick = onRetryClick
    )
}

/**
 * 网络连接失败状态预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun EmptyNetworkPreview() {
    AppTheme {
        Empty(
            message = R.string.empty_network,
            icon = R.drawable.ic_empty_network,
            retryButtonText = R.string.click_retry,
        )
    }
}