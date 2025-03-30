package com.joker.coolmall.core.ui.component.empty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.designsystem.theme.AppTheme

/**
 * 加载失败状态视图
 */
@Composable
fun EmptyError(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit = {}
) {
    Empty(
        modifier = modifier,
        message =  R.string.empty_error,
        subtitle =  R.string.empty_error_subtitle,
        icon = R.drawable.ic_empty_error,
        retryButtonText = R.string.click_retry,
        onRetryClick = onRetryClick
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyErrorPreview() {
    AppTheme {
        Empty(
            message =  R.string.empty_error,
            icon = R.drawable.ic_empty_error,
            retryButtonText = R.string.click_retry,
        )
    }
}