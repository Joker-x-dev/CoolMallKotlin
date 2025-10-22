package com.joker.coolmall.core.ui.component.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.ArrowRightIcon
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 列表项组件
 *
 * @param title 标题文本
 * @param modifier 修饰符
 * @param leadingIcon 前置图标资源ID，为null则不显示
 * @param leadingIconTint 前置图标颜色
 * @param leadingContent 自定义前置内容，优先级高于leadingIcon
 * @param description 描述文本，为null则不显示
 * @param trailingText 尾部文本，为null则不显示
 * @param trailingContent 自定义尾部内容，优先级高于trailingText
 * @param showArrow 是否显示右箭头，默认为true
 * @param showDivider 是否显示底部分隔线，默认为true
 * @param verticalPadding 垂直内边距
 * @param horizontalPadding 水平内边距
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
fun AppListItem(
    title: String,
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    leadingIconTint: Color = MaterialTheme.colorScheme.onSurface,
    leadingContent: @Composable (() -> Unit)? = null,
    description: String? = null,
    trailingText: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    showArrow: Boolean = true,
    showDivider: Boolean = true,
    verticalPadding: Dp = SpaceVerticalMedium,
    horizontalPadding: Dp = SpaceHorizontalMedium,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = verticalPadding, horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 前置内容：自定义内容或图标
            if (leadingContent != null) {
                leadingContent()
                SpaceHorizontalMedium()
            } else if (leadingIcon != null) {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = title,
                    modifier = Modifier.size(20.dp),
                    tint = leadingIconTint
                )
                SpaceHorizontalMedium()
            }

            // 标题和描述
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // 标题
                AppText(
                    text = title,
                    size = TextSize.BODY_LARGE,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // 描述文本（如果有）
                if (!description.isNullOrEmpty()) {
                    AppText(
                        text = description,
                        type = TextType.SECONDARY,
                        size = TextSize.BODY_MEDIUM,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // 尾部内容
            if (trailingContent != null) {
                trailingContent()
            } else if (!trailingText.isNullOrEmpty()) {
                AppText(
                    text = trailingText,
                    type = TextType.SECONDARY,
                    size = TextSize.BODY_MEDIUM
                )
                SpaceHorizontalXSmall()
            }

            // 右箭头
            if (showArrow) {
                ArrowRightIcon(size = 16.dp)
            }
        }

        // 底部分隔线
        if (showDivider) {
            WeDivider()
        }
    }
}

/**
 * 带有标题的分组列表项
 *
 * @param title 标题文本
 * @param items 列表项内容
 * @param modifier 修饰符
 * @param showDivider 是否显示底部分隔线，默认为true
 * @author Joker.X
 */
@Composable
fun GroupAppListItem(
    title: String,
    items: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 分组标题
        AppText(
            text = title,
            type = TextType.TERTIARY,
            size = TextSize.TITLE_MEDIUM,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // 列表项
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            items.forEach { item ->
                item()
            }
        }

        if (showDivider) {
            Spacer(modifier = Modifier.padding(top = 8.dp))
        }
    }
}

/**
 * 无点击效果的列表项
 *
 * @param title 标题文本
 * @param modifier 修饰符
 * @param leadingIcon 前置图标资源ID，为null则不显示
 * @param leadingIconTint 前置图标颜色
 * @param leadingContent 自定义前置内容，优先级高于leadingIcon
 * @param description 描述文本，为null则不显示
 * @param showDivider 是否显示底部分隔线，默认为false
 * @param verticalPadding 垂直内边距
 * @param horizontalPadding 水平内边距
 * @author Joker.X
 */
@Composable
fun StaticAppListItem(
    title: String,
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    leadingIconTint: Color = MaterialTheme.colorScheme.onSurface,
    leadingContent: @Composable (() -> Unit)? = null,
    description: String? = null,
    showDivider: Boolean = false,
    verticalPadding: Dp = SpaceVerticalMedium,
    horizontalPadding: Dp = SpaceHorizontalMedium
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = verticalPadding, horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 前置内容：自定义内容或图标
            if (leadingContent != null) {
                leadingContent()
                SpaceHorizontalMedium()
            } else if (leadingIcon != null) {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = title,
                    modifier = Modifier.size(20.dp),
                    tint = leadingIconTint
                )
                SpaceHorizontalMedium()
            }

            // 标题和描述
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // 标题
                AppText(
                    text = title,
                    size = TextSize.BODY_LARGE
                )

                // 描述文本（如果有）
                if (!description.isNullOrEmpty()) {
                    AppText(
                        text = description,
                        type = TextType.SECONDARY,
                        size = TextSize.BODY_MEDIUM,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // 底部分隔线
        if (showDivider) {
            WeDivider()
        }
    }
} 