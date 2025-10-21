package com.joker.coolmall.feature.order.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.model.entity.LogisticsItem
import com.joker.coolmall.core.model.preview.previewLogisticsItemList
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 物流步骤条组件
 *
 * @param logisticsItems 物流轨迹列表，最新的物流信息应该在列表的第一位
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
fun LogisticsSteps(
    logisticsItems: List<LogisticsItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        logisticsItems.forEachIndexed { index, item ->
            LogisticsStepItem(
                logisticsItem = item,
                isFirst = index == 0,
                isLast = index == logisticsItems.lastIndex
            )
        }
    }
}

/**
 * 单个物流步骤项
 *
 * @param logisticsItem 物流轨迹项数据
 * @param isFirst 是否为第一项（最新的物流信息）
 * @param isLast 是否为最后一项
 * @author Joker.X
 */
@Composable
private fun LogisticsStepItem(
    logisticsItem: LogisticsItem,
    isFirst: Boolean,
    isLast: Boolean
) {
    val activeColor = MaterialTheme.colorScheme.primary
    val defaultColor = MaterialTheme.colorScheme.outline

    // 第一项使用激活颜色，其他项使用默认颜色
    val itemColor = if (isFirst) activeColor else defaultColor

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val offset = 12.dp.toPx()

                // 绘制小圆点
                drawCircle(
                    color = itemColor,
                    radius = if (isFirst) 5.dp.toPx() else 4.dp.toPx(),
                    center = Offset(offset, offset)
                )

                // 绘制连接线（除了最后一项）
                if (!isLast) {
                    drawLine(
                        color = defaultColor,
                        start = Offset(offset, offset * 2),
                        end = Offset(offset, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
            .padding(PaddingValues(start = 36.dp, bottom = if (isLast) 0.dp else 16.dp))
            .sizeIn(minHeight = 40.dp)
    ) {
        Column {
            // 显示物流状态
            logisticsItem.status?.let { status ->
                AppText(
                    text = status,
                    type = if (isFirst) TextType.PRIMARY else TextType.SECONDARY,
                    size = TextSize.BODY_MEDIUM,
                    fontWeight = if (isFirst) FontWeight.Medium else FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // 显示时间
            logisticsItem.time?.let { time ->
                AppText(
                    text = time,
                    type = TextType.TERTIARY,
                    size = TextSize.BODY_SMALL
                )
            }
        }
    }
}

/**
 * 物流步骤条组件预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun LogisticsStepsPreview() {
    AppTheme {
        LogisticsSteps(
            logisticsItems = previewLogisticsItemList,
            modifier = Modifier.padding(16.dp)
        )
    }
}