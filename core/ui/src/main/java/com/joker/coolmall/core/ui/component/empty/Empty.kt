package com.joker.coolmall.core.ui.component.empty

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.designsystem.theme.appTextColors
import com.joker.coolmall.core.ui.R

/**
 * 页面状态视图
 *
 * @param modifier 修饰符
 * @param message 消息文本资源ID
 * @param subtitle 副标题文本资源ID
 * @param retryButtonText 重试按钮文本资源ID
 * @param icon 图标资源ID
 * @param onRetryClick 重试点击回调
 * @author Joker.X
 */
@Composable
fun Empty(
    modifier: Modifier = Modifier,
    message: Int = R.string.empty_error,
    subtitle: Int? = null,
    retryButtonText: Int = R.string.click_retry,
    icon: Int = R.drawable.ic_empty_error,
    onRetryClick: (() -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(SpacePaddingLarge)
    ) {
        CommonIcon(
            painter = painterResource(id = icon),
            size = 120.dp,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2F)
        )

        SpaceVerticalXLarge()

        Text(
            text = stringResource(id = message),
            style = MaterialTheme.typography.titleLarge
        )

        subtitle?.let {
            SpaceVerticalSmall()
            Text(
                text = stringResource(id = it),
                style = MaterialTheme.typography.bodyMedium,
                color = appTextColors().tertiary
            )
        }

        // 如果没有传递重试方法，则不显示重试按钮
        if (onRetryClick != null) {
            SpaceVerticalXLarge()
            OutlinedButton(
                onClick = onRetryClick,
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .widthIn(200.dp)
            ) {
                Text(
                    text = stringResource(id = retryButtonText)
                )
            }
        }
    }
}

/**
 * 页面状态视图浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun EmptyPreview() {
    AppTheme {
        Empty()
    }
}

/**
 * 页面状态视图深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun EmptyPreviewDark() {
    AppTheme(darkTheme = true) {
        Empty()
    }
}