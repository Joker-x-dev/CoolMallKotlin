package com.joker.coolmall.core.ui.htmltext.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.ui.htmltext.HtmlText
import org.jsoup.nodes.Element

/**
 * HTML 表格 (`table`)
 */
@Composable
internal fun HtmlTable(
    node: Element,
    modifier: Modifier = Modifier,
    style: TextStyle,
    uriHandler: UriHandler,
    domain: String? = null
) {
    val tableElement = node
    val tableBody = tableElement.getElementsByTag("tbody").firstOrNull() ?: tableElement
    val rows = tableBody.getElementsByTag("tr")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
    ) {
        // 处理表格行
        rows.forEachIndexed { rowIndex, row ->
            if (rowIndex > 0) {
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            }

            Row(Modifier.fillMaxWidth()) {
                // 处理表格单元格
                val cells = row.children()
                val isHeader = cells.firstOrNull()?.tagName() == "th" || rowIndex == 0
                cells.forEachIndexed { cellIndex, cell ->
                    if (cellIndex > 0) {
                        Divider(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .width(1.dp)
                        )
                    }

                    TableCell(
                        isHeader = isHeader,
                        style = style,
                        weight = 1f
                    ) {
                        HtmlText(cell, style = style, uriHandler = uriHandler, domain = domain)
                    }
                }
            }
        }
    }
}

/**
 * 表格单元格
 */
@Composable
private fun RowScope.TableCell(
    isHeader: Boolean,
    style: TextStyle,
    weight: Float,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(weight)
            .padding(8.dp)
    ) {
        if (isHeader) {
            val headerStyle = style.copy(fontWeight = FontWeight.Bold)
            content()
        } else {
            content()
        }
    }
}

/**
 * 表格单元格分隔线
 */
@Composable
private fun RowScope.Divider(
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
) {
    androidx.compose.material3.Divider(
        color = color,
        modifier = modifier.fillMaxWidth(1f)
    )
} 