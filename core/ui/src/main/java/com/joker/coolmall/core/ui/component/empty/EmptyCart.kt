package com.joker.coolmall.core.ui.component.empty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.R

/**
 * 购物车为空状态视图
 */
@Composable
fun EmptyCart(
    modifier: Modifier = Modifier,
    onRetryClick: (() -> Unit)? = null
) {
    Empty(
        modifier = modifier,
        message = R.string.empty_cart,
        subtitle = R.string.empty_cart_subtitle,
        icon = R.drawable.ic_empty_cart,
        retryButtonText = R.string.empty_cart_btn,
        onRetryClick = onRetryClick
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyCartPreview() {
    AppTheme {
        Empty(
            message = R.string.empty_cart,
            icon = R.drawable.ic_empty_cart,
            retryButtonText = R.string.click_retry,
        )
    }
}
