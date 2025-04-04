package com.joker.coolmall.core.ui.htmltext.elements

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import com.joker.coolmall.core.ui.htmltext.util.validateUrl
import org.jsoup.nodes.Element

/**
 * HTML 链接 (`a`)
 */
@Composable
internal fun HtmlLink(
    node: Element,
    modifier: Modifier = Modifier,
    style: TextStyle,
    uriHandler: UriHandler,
    domain: String? = null
) {
    val href = node.attr("href")
    val url = validateUrl(href, domain)
    val text = buildAnnotatedString {
        append(
            AnnotatedString(
                node.text(),
                SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            )
        )
    }

    ClickableText(
        text = text,
        onClick = { if (url != null) uriHandler.openUri(url) },
        style = style,
        modifier = modifier
    )
} 