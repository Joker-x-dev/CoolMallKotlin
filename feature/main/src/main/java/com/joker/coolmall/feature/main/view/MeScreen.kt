package com.joker.coolmall.feature.main.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold

@Composable
internal fun MeRoute() {
    MeScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MeScreen() {
    CommonScaffold(
        topBar = {
            CenterTopAppBar(R.string.me, showBackIcon = false)
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.me),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MeScreenPreview() {
    MeScreen()
} 