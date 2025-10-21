package com.joker.coolmall.core.ui.component.stepper

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.WrapRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.ui.R

/**
 * 数量控制器组件
 *
 * 该组件提供一个数量选择的UI，包含减少按钮、数量显示和增加按钮
 * 可在购物车、商品详情、订单等多个场景中复用
 *
 * @param quantity 当前数量值
 * @param minValue 最小允许值，默认为1
 * @param maxValue 最大允许值，默认为Int.MAX_VALUE
 * @param onQuantityChanged 数量变更回调，参数为新的数量值
 * @param buttonSize 按钮尺寸，默认为24.dp
 * @param quantityWidth 数量显示区域的最小宽度，默认为24.dp
 * @author Joker.X
 */
@Composable
fun QuantityStepper(
    quantity: Int,
    minValue: Int = 1,
    maxValue: Int = Int.MAX_VALUE,
    onQuantityChanged: (Int) -> Unit,
    buttonSize: Int = 24,
    quantityWidth: Int = 24
) {
    WrapRow {
        // 减少按钮
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(buttonSize.dp)
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                )
                .clickable(enabled = quantity > minValue) {
                    if (quantity > minValue) onQuantityChanged(quantity - 1)
                }
        ) {
            CommonIcon(
                resId = R.drawable.ic_reduce,
                tint = if (quantity > minValue)
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                size = 16.dp
            )

        }

        // 数量
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = SpacePaddingMedium)
                .widthIn(min = quantityWidth.dp),
            textAlign = TextAlign.Center
        )

        // 增加按钮
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(buttonSize.dp)
                .clip(CircleShape)
                .background(
                    color = Primary,
                    shape = CircleShape
                )
                .clickable(enabled = quantity < maxValue) {
                    if (quantity < maxValue) onQuantityChanged(quantity + 1)
                }
        ) {
            CommonIcon(
                resId = R.drawable.ic_add,
                tint = Color.White,
                size = 16.dp
            )
        }
    }
}

/**
 * 数量控制器组件预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun QuantityStepperPreview() {
    AppTheme {
        QuantityStepper(
            quantity = 1,
            onQuantityChanged = { }
        )
    }
} 