package com.joker.coolmall.core.ui.component.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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

            Spacer(modifier = Modifier.height(SpaceVerticalMedium))

            // 按钮类型
            TitleWithLine(text = "按钮类型")

            Spacer(modifier = Modifier.height(SpaceVerticalSmall))

            // 不同类型的按钮
            AppButton(
                text = "默认按钮",
                onClick = { },
                type = ButtonType.DEFAULT
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppButton(
                text = "success",
                onClick = { },
                type = ButtonType.SUCCESS
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppButton(
                text = "warning",
                onClick = { },
                type = ButtonType.WARNING
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppButton(
                text = "danger",
                onClick = { },
                type = ButtonType.DANGER
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppButton(
                text = "purple",
                onClick = { },
                type = ButtonType.PURPLE
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppButtonBordered(
                text = "链接 link",
                onClick = { },
                type = ButtonType.LINK,
                shape = ButtonShape.ROUND
            )

            Spacer(modifier = Modifier.height(SpaceVerticalMedium))

            // 禁用状态
            TitleWithLine(text = "禁用状态")

            Spacer(modifier = Modifier.height(SpaceVerticalSmall))

            // 禁用状态展示
            AppButton(
                text = "禁用按钮",
                onClick = { },
                enabled = false
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 带加载图标的按钮
            AppButton(
                text = "加载中",
                onClick = { },
                loading = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 禁用透明按钮
            AppButton(
                text = "禁用按钮",
                onClick = { },
                enabled = false,
                style = ButtonStyle.OUTLINED
            )

            Spacer(modifier = Modifier.height(SpaceVerticalMedium))

            // 按钮形状
            TitleWithLine(text = "按钮形状")

            Spacer(modifier = Modifier.height(SpaceVerticalSmall))

            // 方形按钮
            AppButton(
                text = "方形按钮",
                onClick = { },
                shape = ButtonShape.SQUARE
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 圆形按钮
            AppButton(
                text = "圆形按钮",
                onClick = { },
                shape = ButtonShape.ROUND
            )

            Spacer(modifier = Modifier.height(SpaceVerticalMedium))

            // 按钮大小
            TitleWithLine(text = "按钮大小")

            Spacer(modifier = Modifier.height(SpaceVerticalSmall))

            // 中型按钮
            AppButton(
                text = "medium",
                onClick = { },
                size = ButtonSize.MEDIUM,
                type = ButtonType.DEFAULT,
                shape = ButtonShape.ROUND,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 小型按钮
            AppButtonFixed(
                text = "small",
                onClick = { },
                size = ButtonSize.SMALL,
                type = ButtonType.DANGER,
                shape = ButtonShape.ROUND,
                modifier = Modifier.width(122.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 迷你型按钮
            AppButtonFixed(
                text = "mini",
                onClick = { },
                size = ButtonSize.MINI,
                type = ButtonType.SUCCESS,
                shape = ButtonShape.ROUND,
                modifier = Modifier.width(86.dp)
            )

            Spacer(modifier = Modifier.height(SpaceVerticalMedium))

            // 自定义颜色
            TitleWithLine(text = "自定义颜色")

            Spacer(modifier = Modifier.height(SpaceVerticalSmall))

            // 渐变按钮
            AppButton(
                text = "渐变按钮",
                onClick = { },
                style = ButtonStyle.GRADIENT
            )
            Spacer(modifier = Modifier.height(32.dp))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonShowcasePreviewLight() {
    AppTheme(darkTheme = false) {
        ButtonShowcase()
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonShowcasePreviewDark() {
    AppTheme(darkTheme = true) {
        ButtonShowcase()
    }
} 