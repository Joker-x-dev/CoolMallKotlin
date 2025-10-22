package com.joker.coolmall.core.ui.component.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 水平分割线组件
 *
 * @param modifier 修饰符，用于自定义组件样式
 * @param color 分割线颜色，默认使用outline颜色
 */
@Composable
fun WeDivider(modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.outline) {
    HorizontalDivider(modifier, thickness = 0.5.dp, color = color)
}