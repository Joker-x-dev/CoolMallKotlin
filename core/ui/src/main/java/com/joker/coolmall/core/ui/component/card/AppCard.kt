package com.joker.coolmall.core.ui.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.TitleLarge
import com.joker.coolmall.core.ui.component.title.TitleWithLine

/**
 * 通用卡片组件
 *
 * @param modifier 修饰符
 * @param title 普通标题文本，如果为null则不显示
 * @param lineTitle 带装饰线的标题文本，如果为null则不显示
 * @param content 卡片内容
 * @author Joker.X
 */
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    title: Any? = null,
    lineTitle: Any? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium)
        ) {
            when {
                // 带装饰线的标题
                lineTitle != null -> TitleWithLine(
                    text = when (lineTitle) {
                        is Int -> stringResource(id = lineTitle)
                        is String -> lineTitle
                        else -> ""
                    }
                )

                // 普通标题
                title != null -> Text(
                    text = when (title) {
                        is Int -> stringResource(id = title)
                        is String -> title
                        else -> ""
                    },
                    style = TitleLarge
                )
            }
            content()
        }
    }
}

/**
 * 通用卡片组件预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun AppCardPreview() {
    AppTheme {
        AppCard(lineTitle = "标题", content = {
            Text(text = "内容")
        })
    }
}