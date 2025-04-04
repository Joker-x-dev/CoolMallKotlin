package com.joker.coolmall.core.ui.htmltext.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.ui.htmltext.HtmlText
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node

/**
 * HTML列表类型
 */
internal enum class HtmlListType {
    ORDERED,
    UNORDERED
}

/**
 * 有序列表装饰器
 */
@Composable
private fun HtmlOrderedListDecorator(index: Int, style: TextStyle) {
    Text(text = "${index + 1}.", style = style)
}

/**
 * 无序列表装饰器
 */
@Composable
private fun HtmlUnorderedListDecorator(style: TextStyle) {
    val size = 4.dp

    Box(
        Modifier
            .padding(top = style.lineHeight.value.dp / 2 - size / 2)
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSurface)
    )
}

/**
 * HTML 列表 (`ol`, `ul`)
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun HtmlList(
    node: Element,
    listType: HtmlListType,
    modifier: Modifier = Modifier,
    style: TextStyle,
    uriHandler: UriHandler = LocalUriHandler.current,
    domain: String? = null
) {
    val list: List<Node> = node.childNodes().filterNot { it.toString().trim() == "" }

    HtmlParagraph(modifier) {
        Column(Modifier.padding(start = 8.dp)) {
            list.forEachIndexed { index, listItem ->
                Row {
                    when (listType) {
                        HtmlListType.ORDERED -> HtmlOrderedListDecorator(index, style)
                        HtmlListType.UNORDERED -> HtmlUnorderedListDecorator(style)
                    }

                    Spacer(Modifier.size(4.dp))

                    FlowRow(Modifier.height(IntrinsicSize.Min)) {
                        HtmlText(listItem, Modifier, style, uriHandler, domain)
                    }
                }
            }
        }
    }
} 