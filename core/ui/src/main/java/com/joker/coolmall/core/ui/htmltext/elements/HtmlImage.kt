package com.joker.coolmall.core.ui.htmltext.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.joker.coolmall.core.ui.htmltext.util.validateUrl
import org.jsoup.nodes.Element

/**
 * HTML 图片 (`img`)
 */
@Composable
internal fun HtmlImage(
    node: Element,
    modifier: Modifier = Modifier,
    domain: String? = null
) {
    val source = node.attr("src")
    if (source.isEmpty()) return

    val url = validateUrl(source, domain) ?: return
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
    )
} 