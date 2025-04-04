package com.joker.coolmall.core.ui.htmltext.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * HTML 段落 (`p`)
 */
@Composable
internal fun HtmlParagraph(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        content()
    }
} 