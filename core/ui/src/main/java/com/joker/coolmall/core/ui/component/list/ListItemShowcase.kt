package com.joker.coolmall.core.ui.component.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.core.ui.component.title.TitleWithLine

/**
 * 列表项组件展示页面
 *
 * 用于展示所有列表项组件的使用示例，包括：
 * - 基础列表项（带图标、描述、尾部文本）
 * - 自定义内边距的列表项
 * - 无点击效果的列表项
 * - 标题栏风格列表项
 * - 分组列表
 * - 自定义样式的列表项（无箭头、无分隔线等）
 *
 * @author Joker.X
 */
@Composable
fun AppListItemShowcase() {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(SpacePaddingMedium)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            // 组件标题和说明
            AppText(
                text = "列表项 AppListItem",
                size = TextSize.DISPLAY_MEDIUM,
                fontWeight = FontWeight.Bold
            )

            AppText(
                text = "列表项组件，支持图标、标题、描述文本、右箭头等功能，适用于设置页、菜单、列表等场景。",
                type = TextType.SECONDARY,
                size = TextSize.BODY_LARGE
            )

            SpaceVerticalMedium()

            // 基础列表项
            TitleWithLine(text = "基础列表项")

            SpaceVerticalSmall()

            Card {
                // 带图标的列表项
                AppListItem(
                    title = "标题文字",
                    leadingIcon = android.R.drawable.ic_dialog_info
                )

                // 带图标和描述的列表项
                AppListItem(
                    title = "标题文字",
                    description = "说明文字",
                    leadingIcon = android.R.drawable.ic_dialog_email
                )

                // 带尾部文本的列表项
                AppListItem(
                    title = "标题文字",
                    trailingText = "说明文字",
                    leadingIcon = android.R.drawable.ic_dialog_map
                )
            }

            SpaceVerticalMedium()

            // 自定义内边距的列表项
            TitleWithLine(text = "自定义内边距的列表项")

            SpaceVerticalSmall()

            Card {
                // 标准内边距
                AppListItem(
                    title = "标准内边距",
                    description = "垂直内边距为16dp",
                    leadingIcon = android.R.drawable.ic_dialog_info
                )

                // 中等内边距
                AppListItem(
                    title = "中等内边距",
                    description = "垂直内边距为12dp",
                    leadingIcon = android.R.drawable.ic_dialog_email,
                    verticalPadding = 12.dp
                )

                // 小内边距
                AppListItem(
                    title = "小内边距",
                    description = "垂直内边距为8dp",
                    leadingIcon = android.R.drawable.ic_dialog_map,
                    verticalPadding = 8.dp
                )

                // 最小内边距
                AppListItem(
                    title = "最小内边距",
                    description = "垂直内边距为4dp",
                    leadingIcon = android.R.drawable.ic_dialog_alert,
                    verticalPadding = 4.dp
                )
            }

            SpaceVerticalMedium()

            // 无点击效果的列表项
            TitleWithLine(text = "无点击效果的列表项")

            SpaceVerticalSmall()

            Card {
                StaticAppListItem(
                    title = "静态标题文字",
                    description = "静态描述文字",
                    leadingIcon = android.R.drawable.ic_dialog_info
                )

                StaticAppListItem(
                    title = "静态标题文字",
                    leadingIcon = android.R.drawable.ic_dialog_dialer
                )
            }

            SpaceVerticalMedium()

            // 标题栏风格列表项
            TitleWithLine(text = "标题栏风格列表项")

            SpaceVerticalSmall()

            Card {
                // 标题栏风格 - 紧凑高度
                AppListItem(
                    title = "我的订单",
                    trailingText = "5",
                    leadingIcon = android.R.drawable.ic_dialog_info,
                    leadingIconTint = Color(0xFFF87C7B),
                    verticalPadding = 10.dp
                )

                // 列表内容
                AppListItem(
                    title = "订单1",
                    description = "待付款",
                    leadingIcon = android.R.drawable.ic_dialog_map
                )

                AppListItem(
                    title = "订单2",
                    trailingText = "已完成",
                    leadingIcon = android.R.drawable.ic_dialog_email
                )
            }

            SpaceVerticalMedium()

            // 分组列表
            TitleWithLine(text = "分组列表")

            SpaceVerticalSmall()

            GroupAppListItem(
                title = "带标题的列表组",
                items = listOf(
                    {
                        AppListItem(
                            title = "标题文字1",
                            description = "说明文字",
                            leadingIcon = android.R.drawable.ic_dialog_info
                        )
                    },
                    {
                        AppListItem(
                            title = "标题文字2",
                            trailingText = "说明文字",
                            leadingIcon = android.R.drawable.ic_dialog_email
                        )
                    },
                    {
                        AppListItem(
                            title = "标题文字3",
                            description = "说明文字",
                            leadingIcon = android.R.drawable.ic_dialog_map,
                            showDivider = false
                        )
                    }
                )
            )

            SpaceVerticalMedium()

            // 自定义样式的列表项
            TitleWithLine(text = "自定义样式的列表项")

            SpaceVerticalSmall()

            Card {
                // 无箭头的列表项
                AppListItem(
                    title = "不显示箭头",
                    description = "适用于信息展示场景",
                    leadingIcon = android.R.drawable.ic_dialog_info,
                    showArrow = false
                )

                // 不显示分隔线的列表项
                AppListItem(
                    title = "不显示分隔线",
                    trailingText = "最后一项",
                    leadingIcon = android.R.drawable.ic_dialog_email,
                    showDivider = false
                )

                // 自定义水平内边距
                AppListItem(
                    title = "自定义水平内边距",
                    description = "水平内边距为24dp",
                    leadingIcon = android.R.drawable.ic_dialog_info,
                    horizontalPadding = 24.dp
                )
            }

            SpaceVerticalMedium()
        }
    }
}

/**
 * 列表项组件展示页面预览 - 浅色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun AppListItemShowcasePreviewLight() {
    AppTheme(darkTheme = false) {
        AppListItemShowcase()
    }
}

/**
 * 列表项组件展示页面预览 - 深色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun AppListItemShowcasePreviewDark() {
    AppTheme(darkTheme = true) {
        AppListItemShowcase()
    }
} 