package com.joker.coolmall.core.ui.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.BgGreenLight
import com.joker.coolmall.core.designsystem.theme.BgPurpleLight
import com.joker.coolmall.core.designsystem.theme.BgRedLight
import com.joker.coolmall.core.designsystem.theme.BgYellowLight
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.ColorSuccess
import com.joker.coolmall.core.designsystem.theme.ColorWarning
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.TextWhite
import com.joker.coolmall.core.ui.R

/**
 * 标签类型
 */
enum class TagType {
    DEFAULT,    // 默认
    PRIMARY,    // 主要
    WARNING,    // 警告
    DANGER,     // 危险
    SUCCESS     // 成功
}

/**
 * 标签样式风格
 */
enum class TagStyle {
    FILLED,     // 基本样式（填充背景）
    LIGHT,      // 浅色样式（浅色背景）
    OUTLINED    // 空心样式（边框）
}

/**
 * 标签大小
 */
enum class TagSize {
    SMALL,      // 小型
    MEDIUM,     // 中型
    LARGE       // 大型
}

/**
 * 获取标签对应的颜色配置
 *
 * @param type 标签类型
 * @param style 标签样式
 * @return 包含文本颜色和背景颜色的Pair
 */
@Composable
private fun getTagColors(type: TagType, style: TagStyle): Pair<Color, Color> {
    return when (style) {
        TagStyle.FILLED -> {
            // 填充样式：背景使用主色，文字为白色
            when (type) {
                TagType.DEFAULT -> Pair(
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.colorScheme.surfaceVariant
                )

                TagType.PRIMARY -> Pair(TextWhite, Primary)
                TagType.WARNING -> Pair(TextWhite, ColorWarning)
                TagType.DANGER -> Pair(TextWhite, ColorDanger)
                TagType.SUCCESS -> Pair(TextWhite, ColorSuccess)
            }
        }

        TagStyle.LIGHT -> {
            // 浅色样式：背景使用透明度降低的主色，文字使用主色
            when (type) {
                TagType.DEFAULT -> Pair(
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.colorScheme.surfaceVariant
                )

                TagType.PRIMARY -> Pair(Primary, BgPurpleLight)
                TagType.WARNING -> Pair(ColorWarning, BgYellowLight)
                TagType.DANGER -> Pair(ColorDanger, BgRedLight)
                TagType.SUCCESS -> Pair(ColorSuccess, BgGreenLight)
            }
        }

        TagStyle.OUTLINED -> {
            // 空心样式：背景透明，使用边框，文字使用主色
            when (type) {
                TagType.DEFAULT -> Pair(
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    Color.Transparent
                )

                TagType.PRIMARY -> Pair(Primary, Color.Transparent)
                TagType.WARNING -> Pair(ColorWarning, Color.Transparent)
                TagType.DANGER -> Pair(ColorDanger, Color.Transparent)
                TagType.SUCCESS -> Pair(ColorSuccess, Color.Transparent)
            }
        }
    }
}

/**
 * 标签组件
 *
 * @param text 标签文本
 * @param type 标签类型，默认为DEFAULT
 * @param style 标签样式风格，默认为FILLED
 * @param size 标签大小，默认为MEDIUM
 * @param shape 标签形状，默认为ShapeSmall（圆角8dp）
 * @param modifier 修饰符
 * @param textStyle 文本样式，默认为Material3的labelSmall
 */
@Composable
fun Tag(
    text: String,
    type: TagType = TagType.DEFAULT,
    style: TagStyle = TagStyle.FILLED,
    size: TagSize = TagSize.MEDIUM,
    shape: Shape = ShapeSmall,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall
) {
    // 获取颜色配置
    val (textColor, backgroundColor) = getTagColors(type, style)

    // 根据大小调整内边距
    val padding = when (size) {
        TagSize.SMALL -> SpacePaddingXSmall
        TagSize.MEDIUM -> SpacePaddingSmall
        TagSize.LARGE -> SpacePaddingSmall.times(1.5f)
    }

    // 修复空心样式异常的问题：先应用clip和border，再应用background和padding
    var tagModifier = if (style == TagStyle.OUTLINED) {
        Modifier
            .clip(shape)
            .border(1.dp, textColor, shape)
            .background(backgroundColor)
            .padding(horizontal = padding.times(1.5f), vertical = padding)
    } else {
        Modifier
            .clip(shape)
            .background(backgroundColor)
            .padding(horizontal = padding.times(1.5f), vertical = padding)
    }

    // 合并传入的修饰符
    tagModifier = modifier.then(tagModifier)

    Box(
        contentAlignment = Alignment.Center,
        modifier = tagModifier
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}

/**
 * 带关闭按钮的标签组件
 *
 * @param text 标签文本
 * @param onClose 关闭按钮点击回调
 * @param type 标签类型，默认为DEFAULT
 * @param style 标签样式风格，默认为FILLED
 * @param size 标签大小，默认为MEDIUM
 * @param shape 标签形状，默认为ShapeSmall（圆角8dp）
 * @param closeIcon 关闭图标，默认为Icons.Default.Close
 * @param modifier 修饰符
 * @param textStyle 文本样式，默认为Material3的labelSmall
 */
@Composable
fun TagClosable(
    text: String,
    onClose: () -> Unit,
    type: TagType = TagType.DEFAULT,
    style: TagStyle = TagStyle.FILLED,
    size: TagSize = TagSize.MEDIUM,
    shape: Shape = ShapeSmall,
    closeIcon: Int = R.drawable.ic_close,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall
) {
    // 获取颜色配置
    val (textColor, backgroundColor) = getTagColors(type, style)

    // 根据大小调整内边距
    val padding = when (size) {
        TagSize.SMALL -> SpacePaddingXSmall
        TagSize.MEDIUM -> SpacePaddingSmall
        TagSize.LARGE -> SpacePaddingSmall.times(1.5f)
    }

    // 图标大小
    val iconSize = when (size) {
        TagSize.SMALL -> 12.dp
        TagSize.MEDIUM -> 16.dp
        TagSize.LARGE -> 18.dp
    }

    // 修复空心样式异常的问题：先应用clip和border，再应用background和padding
    var tagModifier = if (style == TagStyle.OUTLINED) {
        Modifier
            .clip(shape)
            .border(1.dp, textColor, shape)
            .background(backgroundColor)
            .padding(
                start = padding.times(1.5f),
                top = padding,
                bottom = padding,
                end = padding
            )
    } else {
        Modifier
            .clip(shape)
            .background(backgroundColor)
            .padding(
                start = padding.times(1.5f),
                top = padding,
                bottom = padding,
                end = padding
            )
    }

    // 合并传入的修饰符
    tagModifier = modifier.then(tagModifier)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = tagModifier
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )

        Spacer(modifier = Modifier.width(4.dp))

        CommonIcon(
            resId = closeIcon,
            contentDescription = stringResource(id = R.string.modal_close),
            tint = textColor,
            size = iconSize,
            modifier = Modifier
                .clickable { onClose() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilledTagPreview() {
    AppTheme {
        Tag(text = "默认标签", type = TagType.DEFAULT, style = TagStyle.FILLED)
    }
}

@Preview(showBackground = true)
@Composable
private fun LightTagPreview() {
    AppTheme {
        Tag(text = "浅色标签", type = TagType.PRIMARY, style = TagStyle.LIGHT)
    }
}

@Preview(showBackground = true)
@Composable
private fun OutlinedTagPreview() {
    AppTheme {
        Tag(text = "空心标签", type = TagType.DANGER, style = TagStyle.OUTLINED)
    }
}

@Preview(showBackground = true)
@Composable
private fun ClosableTagPreview() {
    AppTheme {
        TagClosable(
            text = "可关闭标签",
            onClose = {},
            type = TagType.PRIMARY,
            style = TagStyle.FILLED
        )
    }
} 