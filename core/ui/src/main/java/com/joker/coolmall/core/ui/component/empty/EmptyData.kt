package com.joker.coolmall.core.ui.component.empty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.R

/**
 * 暂无数据状态视图
 */
@Composable
fun EmptyData(
    modifier: Modifier = Modifier,
    onRetryClick: (() -> Unit)? = null
) {
    Empty(
        modifier = modifier,
        message = R.string.empty_data,
        subtitle = R.string.empty_data_subtitle,
        icon = R.drawable.ic_empty_data,
        retryButtonText = R.string.click_retry,
        onRetryClick = onRetryClick
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyDataPreview() {
    AppTheme {
        Empty(
            message = R.string.empty_data,
            icon = R.drawable.ic_empty_data,
            retryButtonText = R.string.click_retry,
        )
    }
}