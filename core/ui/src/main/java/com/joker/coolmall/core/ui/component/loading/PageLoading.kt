package com.joker.coolmall.core.ui.component.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall

/**
 * 页面加载中
 */
@Composable
fun PageLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieLoading()
        SpaceVerticalSmall()
        Text(text = "加载中...")
    }
}