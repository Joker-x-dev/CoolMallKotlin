package com.joker.coolmall.core.ui.component.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize

/**
 * 页面加载中
 *
 * @param modifier 可选修饰符
 * @author Joker.X
 */
@Composable
fun PageLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MiLoadingMobile(
            borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        SpaceVerticalSmall()
        AppText(text = stringResource(R.string.loading), size = TextSize.BODY_MEDIUM)
    }
}

@Preview(showBackground = true)
@Composable
fun PageLoadingPreview() {
    AppTheme{
        PageLoading()
    }
}
