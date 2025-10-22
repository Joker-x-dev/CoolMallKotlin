package com.joker.coolmall.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall

/**
 * 封装的Box组件，预设了常用的修饰符
 *
 * @param modifier 额外的修饰符
 * @param contentAlignment 内容对齐方式
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度（当fillMaxSize为false时生效）
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun AppBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val finalModifier = modifier
        .let { if (fillMaxSize) it.fillMaxSize() else if (fillMaxWidth) it.fillMaxWidth() else it }
        .let { if (padding > 0.dp) it.padding(padding) else it }

    Box(
        modifier = finalModifier,
        contentAlignment = contentAlignment,
        content = content
    )
}

/**
 * 填充整个屏幕的Box，便于创建全屏布局
 *
 * @param modifier 额外的修饰符
 * @param contentAlignment 内容对齐方式
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun FullScreenBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    padding: PaddingValues = PaddingValues(0.dp),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = contentAlignment,
        content = content
    )
}

/**
 * 居中Box组件，内容居中对齐
 *
 * @param modifier 额外的修饰符
 * @param fillMaxSize 是否填充最大尺寸
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun CenterBox(
    modifier: Modifier = Modifier,
    fillMaxSize: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    AppBox(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        fillMaxSize = fillMaxSize,
        padding = padding,
        content = content
    )
}

/**
 * 顶部居中对齐的Box组件
 *
 * @param modifier 额外的修饰符
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun TopCenterBox(
    modifier: Modifier = Modifier,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    AppBox(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 底部居中对齐的Box组件
 *
 * @param modifier 额外的修饰符
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun BottomCenterBox(
    modifier: Modifier = Modifier,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    AppBox(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 左侧居中对齐的Box组件
 *
 * @param modifier 额外的修饰符
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun CenterStartBox(
    modifier: Modifier = Modifier,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    AppBox(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 右侧居中对齐的Box组件
 *
 * @param modifier 额外的修饰符
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun CenterEndBox(
    modifier: Modifier = Modifier,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    AppBox(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 固定尺寸的Box组件
 *
 * @param modifier 额外的修饰符
 * @param width 宽度
 * @param height 高度
 * @param contentAlignment 内容对齐方式
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun FixedSizeBox(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    contentAlignment: Alignment = Alignment.Center,
    padding: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .let { if (padding > 0.dp) it.padding(padding) else it },
        contentAlignment = contentAlignment,
        content = content
    )
}

/**
 * 圆角Box组件，常用于卡片或容器
 *
 * @param modifier 额外的修饰符
 * @param contentAlignment 内容对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param cornerRadius 圆角半径
 * @param padding 内边距
 * @param backgroundColor 背景颜色
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun RoundedBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    fillMaxWidth: Boolean = true,
    cornerRadius: Dp = 8.dp,
    padding: Dp = SpacePaddingMedium,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .let { if (fillMaxWidth) it.fillMaxWidth() else it }
            .clip(RoundedCornerShape(cornerRadius))
            .padding(padding),
        contentAlignment = contentAlignment,
        content = content
    )
}

/**
 * 带边框的Box组件，常用于强调区域
 *
 * @param modifier 额外的修饰符
 * @param contentAlignment 内容对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param cornerRadius 圆角半径
 * @param padding 内边距
 * @param borderWidth 边框宽度
 * @param borderColor 边框颜色
 * @param shape 形状
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun BorderBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    fillMaxWidth: Boolean = true,
    cornerRadius: Dp = 8.dp,
    padding: Dp = SpacePaddingMedium,
    borderWidth: Dp = 1.dp,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    shape: Shape = RoundedCornerShape(cornerRadius),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .let { if (fillMaxWidth) it.fillMaxWidth() else it }
            .clip(shape)
            .border(borderWidth, borderColor, shape)
            .padding(padding),
        contentAlignment = contentAlignment,
        content = content
    )
}

/**
 * 带小内边距的Box，常用于紧凑型布局
 *
 * @param modifier 额外的修饰符
 * @param contentAlignment 内容对齐方式
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun SmallPaddingBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    AppBox(
        modifier = modifier,
        contentAlignment = contentAlignment,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        padding = SpacePaddingSmall,
        content = content
    )
}

/**
 * 带中等内边距的Box，常用于普通布局
 *
 * @param modifier 额外的修饰符
 * @param contentAlignment 内容对齐方式
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun MediumPaddingBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    AppBox(
        modifier = modifier,
        contentAlignment = contentAlignment,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        padding = SpacePaddingMedium,
        content = content
    )
}

/**
 * 带大内边距的Box，常用于强调布局
 *
 * @param modifier 额外的修饰符
 * @param contentAlignment 内容对齐方式
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun LargePaddingBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    AppBox(
        modifier = modifier,
        contentAlignment = contentAlignment,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        padding = SpacePaddingLarge,
        content = content
    )
} 