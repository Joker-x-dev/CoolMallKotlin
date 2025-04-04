package com.joker.coolmall.core.ui.htmltext.elements

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * HTML 换行 (`br`)
 */
@Composable
internal fun HtmlLineBreak(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(16.dp))
} 