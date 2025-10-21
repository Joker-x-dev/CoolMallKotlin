package com.joker.coolmall.core.ui.component.text

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme

/**
 * 价格文本组件
 * @param price 价格数值，支持小数点后两位，例如 499.99
 * @param type 文本类型，控制颜色
 * @param modifier 修饰符
 * @param color 自定义颜色，优先级高于type
 * @param integerTextSize 整数部分文本大小，默认为DISPLAY_MEDIUM
 * @param decimalTextSize 小数部分文本大小，默认为BODY_MEDIUM
 * @param symbolTextSize 人民币符号文本大小，默认为BODY_MEDIUM
 * @author Joker.X
 */
@Composable
fun PriceText(
    price: Int,
    modifier: Modifier = Modifier,
    type: TextType = TextType.ERROR,
    integerTextSize: TextSize = TextSize.DISPLAY_MEDIUM,
    decimalTextSize: TextSize = TextSize.BODY_MEDIUM,
    symbolTextSize: TextSize = TextSize.BODY_MEDIUM
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        // 人民币符号
        AppText(
            text = "¥",
            type = type,
            size = symbolTextSize,
            modifier = Modifier
                .alignByBaseline()
                .padding(end = 1.dp)
        )

        // 整数部分
        AppText(
            text = price.toString(),
            type = type,
            size = integerTextSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.alignByBaseline()
        )

        // 小数部分
        AppText(
            text = ".00",
            type = type,
            size = decimalTextSize,
            modifier = Modifier.alignByBaseline()
        )
    }
}

/**
 * 价格文本组件预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun PriceTextPreview() {
    AppTheme {
        PriceText(price = 4999)
    }
}