package com.joker.coolmall.core.ui.component.text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.title.TitleWithLine

/**
 * 文本组件展示页面
 *
 * 用于展示所有文本组件的使用示例，包括：
 * - 不同类型的文本（默认、次要、辅助、成功、警告、危险、链接）
 * - 不同大小的文本（特大、大号、中大、中号、正常、小号）
 * - 不同样式的文本（粗体、可选择）
 * - 组合使用（大号粗体、小号次要、粗体链接）
 *
 * @author Joker.X
 */
@Composable
fun TextShowcase() {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(SpacePaddingMedium)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "文本 Text",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "文本组件，支持不同颜色和大小的文本显示。",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            SpaceVerticalMedium()

            // 文本类型
            TitleWithLine(text = "文本类型")

            SpaceVerticalSmall()

            // 使用Text组件直接显示默认文本，不指定颜色
            Text(
                text = "默认文本"
            )

            SpaceVerticalSmall()

            AppText(
                text = "次要文本",
                type = TextType.SECONDARY
            )

            SpaceVerticalSmall()

            AppText(
                text = "辅助文本",
                type = TextType.TERTIARY
            )

            SpaceVerticalSmall()

            AppText(
                text = "成功文本",
                type = TextType.SUCCESS
            )

            SpaceVerticalSmall()

            AppText(
                text = "警告文本",
                type = TextType.WARNING
            )

            SpaceVerticalSmall()

            AppText(
                text = "危险文本",
                type = TextType.ERROR
            )

            SpaceVerticalSmall()

            AppText(
                text = "链接文本",
                type = TextType.LINK,
                onClick = { }
            )

            SpaceVerticalMedium()

            // 文本大小
            TitleWithLine(text = "文本大小")

            SpaceVerticalSmall()

            // 不同大小的文本
            AppText(
                text = "特大文本",
                size = TextSize.DISPLAY_LARGE
            )

            SpaceVerticalSmall()

            AppText(
                text = "大号文本",
                size = TextSize.DISPLAY_MEDIUM
            )

            SpaceVerticalSmall()

            AppText(
                text = "中大文本",
                size = TextSize.TITLE_LARGE
            )

            SpaceVerticalSmall()

            AppText(
                text = "中号文本",
                size = TextSize.TITLE_MEDIUM
            )

            SpaceVerticalSmall()

            AppText(
                text = "正常文本",
                size = TextSize.BODY_LARGE
            )

            SpaceVerticalSmall()

            AppText(
                text = "小号文本",
                size = TextSize.BODY_MEDIUM
            )

            SpaceVerticalMedium()

            // 文本样式
            TitleWithLine(text = "文本样式")

            SpaceVerticalSmall()

            // 粗体文本
            AppText(
                text = "粗体文本",
                fontWeight = FontWeight.Bold
            )

            SpaceVerticalSmall()

            // 可选择文本
            AppText(
                text = "可选择文本（长按可选择）",
                selectable = true
            )

            SpaceVerticalMedium()

            // 组合使用
            TitleWithLine(text = "组合使用")

            SpaceVerticalSmall()

            // 组合使用文本样式
            AppText(
                text = "大号粗体主要文本",
                size = TextSize.DISPLAY_MEDIUM,
                fontWeight = FontWeight.Bold
            )

            SpaceVerticalSmall()

            AppText(
                text = "小号次要文本",
                type = TextType.SECONDARY,
                size = TextSize.BODY_MEDIUM
            )

            SpaceVerticalSmall()

            AppText(
                text = "粗体链接文本",
                type = TextType.LINK,
                fontWeight = FontWeight.Bold,
                onClick = { }
            )

            SpaceVerticalMedium()
        }
    }
}

/**
 * 文本组件展示页面预览 - 浅色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun TextShowcasePreviewLight() {
    AppTheme(darkTheme = false) {
        TextShowcase()
    }
}

/**
 * 文本组件展示页面预览 - 深色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun TextShowcasePreviewDark() {
    AppTheme(darkTheme = true) {
        TextShowcase()
    }
} 