package com.joker.coolmall.core.ui.component.empty

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.designsystem.theme.appTextColors
import com.joker.coolmall.core.ui.R

/**
 * 页面状态视图
 */
@Composable
fun Empty(
    modifier: Modifier = Modifier,
    message: Int = R.string.empty_error,
    subtitle: Int? = null,
    retryButtonText: Int = R.string.click_retry,
    icon: Int = R.drawable.ic_empty_error,
    onRetryClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(SpacePaddingLarge)
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = icon),
            contentDescription = null
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

@Preview(showBackground = true)
@Composable
fun EmptyPreview() {
    AppTheme {
        Empty()
    }
}