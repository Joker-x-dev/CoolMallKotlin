package com.joker.coolmall.core.ui.component.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.title.TitleWithLine

/**
 * 按钮组件展示页面
 *
 * 用于展示所有按钮组件的使用示例，包括：
 * - 不同类型的按钮（默认、成功、警告、危险等）
 * - 不同状态的按钮（禁用、加载中）
 * - 不同形状的按钮（方形、圆形）
 * - 不同大小的按钮（中、小、迷你）
 * - 自定义样式的按钮（渐变）
 *
 * @author Joker.X
 */
@Composable
fun ButtonShowcase() {

    Scaffold {

        Column(
            modifier = Modifier
                .padding(SpacePaddingMedium)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Button",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Button 按钮，支持自定义大小、颜色等。",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            SpaceVerticalMedium()

            // 按钮类型
            TitleWithLine(text = "按钮类型")

            SpaceVerticalSmall()

            // 不同类型的按钮
            AppButton(
                text = "默认按钮",
                onClick = { },
                type = ButtonType.DEFAULT
            )

            SpaceVerticalSmall()

            AppButton(
                text = "success",
                onClick = { },
                type = ButtonType.SUCCESS
            )

            SpaceVerticalSmall()

            AppButton(
                text = "warning",
                onClick = { },
                type = ButtonType.WARNING
            )

            SpaceVerticalSmall()

            AppButton(
                text = "danger",
                onClick = { },
                type = ButtonType.DANGER
            )

            SpaceVerticalSmall()

            AppButton(
                text = "purple",
                onClick = { },
                type = ButtonType.PURPLE
            )

            SpaceVerticalSmall()

            AppButtonBordered(
                text = "链接 link",
                onClick = { },
                type = ButtonType.LINK,
                shape = ButtonShape.ROUND
            )

            SpaceVerticalMedium()

            // 禁用状态
            TitleWithLine(text = "禁用状态")

            SpaceVerticalSmall()

            // 禁用状态展示
            AppButton(
                text = "禁用按钮",
                onClick = { },
                enabled = false
            )

            SpaceVerticalSmall()

            // 带加载图标的按钮
            AppButton(
                text = "加载中",
                onClick = { },
                loading = true
            )

            SpaceVerticalSmall()

            // 禁用透明按钮
            AppButton(
                text = "禁用按钮",
                onClick = { },
                enabled = false,
                style = ButtonStyle.OUTLINED
            )

            SpaceVerticalMedium()

            // 按钮形状
            TitleWithLine(text = "按钮形状")

            SpaceVerticalSmall()

            // 方形按钮
            AppButton(
                text = "方形按钮",
                onClick = { },
                shape = ButtonShape.SQUARE
            )

            SpaceVerticalSmall()

            // 圆形按钮
            AppButton(
                text = "圆形按钮",
                onClick = { },
                shape = ButtonShape.ROUND
            )

            SpaceVerticalMedium()

            // 按钮大小
            TitleWithLine(text = "按钮大小")

            SpaceVerticalSmall()

            // 中型按钮
            AppButton(
                text = "medium",
                onClick = { },
                size = ButtonSize.MEDIUM,
                type = ButtonType.DEFAULT,
                shape = ButtonShape.ROUND,
                fullWidth = true
            )

            SpaceVerticalSmall()

            // 小型按钮
            AppButtonFixed(
                text = "small",
                onClick = { },
                size = ButtonSize.SMALL,
                type = ButtonType.DANGER,
                shape = ButtonShape.ROUND,
                modifier = Modifier.width(122.dp)
            )

            SpaceVerticalSmall()

            // 迷你型按钮
            AppButtonFixed(
                text = "mini",
                onClick = { },
                size = ButtonSize.MINI,
                type = ButtonType.SUCCESS,
                shape = ButtonShape.ROUND,
                modifier = Modifier.width(86.dp)
            )

            SpaceVerticalMedium()

            // 自定义颜色
            TitleWithLine(text = "自定义颜色")

            SpaceVerticalSmall()

            // 渐变按钮
            AppButton(
                text = "渐变按钮",
                onClick = { },
                style = ButtonStyle.GRADIENT
            )

            SpaceVerticalMedium()
        }
    }
}

/**
 * 按钮组件展示页面预览 - 浅色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun ButtonShowcasePreviewLight() {
    AppTheme(darkTheme = false) {
        ButtonShowcase()
    }
}

/**
 * 按钮组件展示页面预览 - 深色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun ButtonShowcasePreviewDark() {
    AppTheme(darkTheme = true) {
        ButtonShowcase()
    }
} 