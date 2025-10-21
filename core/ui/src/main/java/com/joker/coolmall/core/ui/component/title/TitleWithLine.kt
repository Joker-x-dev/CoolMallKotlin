package com.joker.coolmall.core.ui.component.title

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.TitleLarge
import com.joker.coolmall.core.ui.component.text.AppText

/**
 * 带有垂直装饰线的标题组件
 *
 * @param text 标题文本
 * @param textColor 文本颜色
 * @param modifier 修饰符
 * @param lineColor 装饰线颜色
 * @author Joker.X
 */
@Composable
fun TitleWithLine(
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.primary,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 垂直装饰线
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(lineColor)

        )
        SpaceHorizontalSmall()
        // 标题文本
        AppText(
            text = text,
            fontWeight = TitleLarge.fontWeight,
            color = textColor
        )
    }
}

/**
 * 带装饰线标题组件预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun TitleWithLinePreview() {
    AppTheme {
        TitleWithLine(text = "标题预览")
    }
}