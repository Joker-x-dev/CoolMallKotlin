package com.joker.coolmall.core.ui.component.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.ColorSuccess
import com.joker.coolmall.core.designsystem.theme.ColorWarning
import com.joker.coolmall.core.designsystem.theme.Primary

/**
 * 文本类型
 *
 * @author Joker.X
 */
enum class TextType {
    /**
     * 主要文本，用于标题、重要内容
     * 使用Material Theme的onBackground颜色
     */
    PRIMARY,

    /**
     * 次要文本，用于正文内容
     * 使用Material Theme的onSurface颜色带透明度
     */
    SECONDARY,

    /**
     * 辅助文本，用于辅助说明
     * 使用Material Theme的onSurfaceVariant颜色
     */
    TERTIARY,

    /**
     * 白色文本，用于深色背景
     */
    WHITE,

    /**
     * 链接文本，可点击
     */
    LINK,

    /**
     * 成功文本，通常为绿色
     */
    SUCCESS,

    /**
     * 警告文本，通常为黄色
     */
    WARNING,

    /**
     * 错误文本，通常为红色
     */
    ERROR
}

/**
 * 文本大小
 *
 * @author Joker.X
 */
enum class TextSize {
    /**
     * 特大：22sp，用于大标题
     */
    DISPLAY_LARGE,

    /**
     * 大号：18sp，用于页面标题
     */
    DISPLAY_MEDIUM,

    /**
     * 中大：16sp，用于二级标题
     */
    TITLE_LARGE,

    /**
     * 中号：14sp，用于分类标题、强调内容
     */
    TITLE_MEDIUM,

    /**
     * 正常：14sp，用于正文内容
     */
    BODY_LARGE,

    /**
     * 小号：12sp，用于辅助文字、标签
     */
    BODY_MEDIUM,

    /**
     * 超小：10sp，用于极小的辅助文字
     */
    BODY_SMALL
}

/**
 * 通用文本组件
 *
 * @param text 文本内容
 * @param modifier 修饰符
 * @param type 文本类型，默认为PRIMARY
 * @param size 文本大小，默认为BODY_LARGE
 * @param color 自定义颜色，会覆盖type的默认颜色
 * @param fontWeight 字体粗细，默认根据size自动设置
 * @param fontStyle 字体样式，默认为FontStyle.Normal
 * @param fontFamily 字体家族，默认为null
 * @param letterSpacing 字母间距，默认为TextUnit.Unspecified
 * @param textDecoration 文本装饰，如下划线等，默认为null
 * @param textAlign 文本对齐方式，默认为null
 * @param lineHeight 行高，默认为TextUnit.Unspecified
 * @param overflow 文本溢出处理方式，默认为TextOverflow.Clip
 * @param softWrap 是否自动换行，默认为true
 * @param maxLines 最大行数，默认为Int.MAX_VALUE
 * @param minLines 最小行数，默认为1
 * @param onTextLayout 文本布局回调，默认为null
 * @param onClick 点击回调，设置后文本将变为可点击状态
 * @param style 自定义文本样式，会覆盖其他样式设置
 * @param selectable 是否可选择，默认为false
 * @author Joker.X
 */
@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    type: TextType = TextType.PRIMARY,
    size: TextSize = TextSize.BODY_LARGE,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
    style: TextStyle? = null,
    selectable: Boolean = false
) {
    // 根据类型设置颜色
    val textColor = when {
        color != Color.Unspecified -> color
        type == TextType.PRIMARY -> Color.Unspecified // 使用默认Material颜色
        type == TextType.SECONDARY -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        type == TextType.TERTIARY -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        type == TextType.WHITE -> Color.White
        type == TextType.LINK -> Primary
        type == TextType.SUCCESS -> ColorSuccess
        type == TextType.WARNING -> ColorWarning
        type == TextType.ERROR -> ColorDanger
        else -> Color.Unspecified
    }

    // 根据大小设置文本样式
    val textStyle = style ?: when (size) {
        TextSize.DISPLAY_LARGE -> MaterialTheme.typography.displayLarge
        TextSize.DISPLAY_MEDIUM -> MaterialTheme.typography.displayMedium
        TextSize.TITLE_LARGE -> MaterialTheme.typography.titleLarge
        TextSize.TITLE_MEDIUM -> MaterialTheme.typography.titleMedium
        TextSize.BODY_LARGE -> MaterialTheme.typography.bodyLarge
        TextSize.BODY_MEDIUM -> MaterialTheme.typography.bodyMedium
        TextSize.BODY_SMALL -> MaterialTheme.typography.bodySmall
    }

    // 设置字体粗细（如果未指定，使用样式默认值）
    val finalFontWeight = fontWeight ?: textStyle.fontWeight

    // 创建修改后的样式
    val finalStyle = textStyle.copy(
        color = textColor,
        fontWeight = finalFontWeight,
        fontStyle = fontStyle ?: textStyle.fontStyle,
        fontFamily = fontFamily ?: textStyle.fontFamily,
        letterSpacing = if (letterSpacing != TextUnit.Unspecified) letterSpacing else textStyle.letterSpacing,
        textDecoration = textDecoration ?: textStyle.textDecoration,
        textAlign = textAlign ?: textStyle.textAlign,
        lineHeight = if (lineHeight != TextUnit.Unspecified) lineHeight else textStyle.lineHeight
    )

    // 处理可点击状态
    val clickableModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    // 处理可选择状态
    if (selectable) {
        SelectionContainer {
            Text(
                text = text,
                modifier = clickableModifier,
                style = finalStyle,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                minLines = minLines,
                onTextLayout = onTextLayout,
                color = if (textColor == Color.Unspecified) Color.Unspecified else textColor
            )
        }
    } else {
        Text(
            text = text,
            modifier = clickableModifier,
            style = finalStyle,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            color = if (textColor == Color.Unspecified) Color.Unspecified else textColor
        )
    }
}

/**
 * 通用文本组件 - AnnotatedString版本
 *
 * @param text 富文本内容
 * @param modifier 修饰符
 * @param type 文本类型，默认为PRIMARY
 * @param size 文本大小，默认为BODY_LARGE
 * @param color 自定义颜色，会覆盖type的默认颜色
 * @param fontWeight 字体粗细，默认根据size自动设置
 * @param fontStyle 字体样式，默认为FontStyle.Normal
 * @param fontFamily 字体家族，默认为null
 * @param letterSpacing 字母间距，默认为TextUnit.Unspecified
 * @param textDecoration 文本装饰，如下划线等，默认为null
 * @param textAlign 文本对齐方式，默认为null
 * @param lineHeight 行高，默认为TextUnit.Unspecified
 * @param overflow 文本溢出处理方式，默认为TextOverflow.Clip
 * @param softWrap 是否自动换行，默认为true
 * @param maxLines 最大行数，默认为Int.MAX_VALUE
 * @param minLines 最小行数，默认为1
 * @param onTextLayout 文本布局回调，默认为null
 * @param onClick 点击回调，设置后文本将变为可点击状态
 * @param style 自定义文本样式，会覆盖其他样式设置
 * @param selectable 是否可选择，默认为false
 * @author Joker.X
 */
@Composable
fun AppText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    type: TextType = TextType.PRIMARY,
    size: TextSize = TextSize.BODY_LARGE,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
    style: TextStyle? = null,
    selectable: Boolean = false
) {
    // 根据类型设置颜色
    val textColor = when {
        color != Color.Unspecified -> color
        type == TextType.PRIMARY -> Color.Unspecified // 使用默认Material颜色
        type == TextType.SECONDARY -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        type == TextType.TERTIARY -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        type == TextType.WHITE -> Color.White
        type == TextType.LINK -> Primary
        type == TextType.SUCCESS -> ColorSuccess
        type == TextType.WARNING -> ColorWarning
        type == TextType.ERROR -> ColorDanger
        else -> Color.Unspecified
    }

    // 根据大小设置文本样式
    val textStyle = style ?: when (size) {
        TextSize.DISPLAY_LARGE -> MaterialTheme.typography.displayLarge
        TextSize.DISPLAY_MEDIUM -> MaterialTheme.typography.displayMedium
        TextSize.TITLE_LARGE -> MaterialTheme.typography.titleLarge
        TextSize.TITLE_MEDIUM -> MaterialTheme.typography.titleMedium
        TextSize.BODY_LARGE -> MaterialTheme.typography.bodyLarge
        TextSize.BODY_MEDIUM -> MaterialTheme.typography.bodyMedium
        TextSize.BODY_SMALL -> MaterialTheme.typography.bodySmall
    }

    // 设置字体粗细（如果未指定，使用样式默认值）
    val finalFontWeight = fontWeight ?: textStyle.fontWeight

    // 创建修改后的样式
    val finalStyle = textStyle.copy(
        color = textColor,
        fontWeight = finalFontWeight,
        fontStyle = fontStyle ?: textStyle.fontStyle,
        fontFamily = fontFamily ?: textStyle.fontFamily,
        letterSpacing = if (letterSpacing != TextUnit.Unspecified) letterSpacing else textStyle.letterSpacing,
        textDecoration = textDecoration ?: textStyle.textDecoration,
        textAlign = textAlign ?: textStyle.textAlign,
        lineHeight = if (lineHeight != TextUnit.Unspecified) lineHeight else textStyle.lineHeight
    )

    // 处理可点击状态
    val clickableModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    // 处理可选择状态
    if (selectable) {
        SelectionContainer {
            Text(
                text = text,
                modifier = clickableModifier,
                style = finalStyle,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                minLines = minLines,
                onTextLayout = onTextLayout,
                color = if (textColor == Color.Unspecified) Color.Unspecified else textColor
            )
        }
    } else {
        Text(
            text = text,
            modifier = clickableModifier,
            style = finalStyle,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            color = if (textColor == Color.Unspecified) Color.Unspecified else textColor
        )
    }
}

/**
 * 基础文本组件 - 字符串版本
 *
 * 该组件是对 Material3 Text 的轻量封装，主要用于设置ContentColor
 *
 * @param text 文本内容
 * @param modifier 修饰符
 * @param style 文本样式
 * @param overflow 文本溢出处理方式
 * @param softWrap 是否自动换行
 * @param maxLines 最大行数
 * @param minLines 最小行数
 * @param onTextLayout 文本布局回调
 * @author Joker.X
 */
@Composable
private fun BasicText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    val textColor = style.color.takeOrElse { LocalContentColor.current }

    CompositionLocalProvider(LocalContentColor provides textColor) {
        Text(
            text = text,
            modifier = modifier,
            style = style,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout
        )
    }
}

/**
 * 基础文本组件 - AnnotatedString版本
 *
 * 该组件是对 Material3 Text 的轻量封装，主要用于设置ContentColor
 *
 * @param text 富文本内容
 * @param modifier 修饰符
 * @param style 文本样式
 * @param overflow 文本溢出处理方式
 * @param softWrap 是否自动换行
 * @param maxLines 最大行数
 * @param minLines 最小行数
 * @param onTextLayout 文本布局回调
 * @author Joker.X
 */
@Composable
private fun BasicText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    val textColor = style.color.takeOrElse { LocalContentColor.current }

    CompositionLocalProvider(LocalContentColor provides textColor) {
        Text(
            text = text,
            modifier = modifier,
            style = style,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout
        )
    }
} 