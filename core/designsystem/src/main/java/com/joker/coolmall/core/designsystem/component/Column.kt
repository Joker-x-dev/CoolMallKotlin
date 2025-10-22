package com.joker.coolmall.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium

/**
 * 封装的Column组件，预设了常用的修饰符
 *
 * @param modifier 额外的修饰符
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun AppColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val finalModifier = modifier
        .let { if (fillMaxWidth) it.fillMaxWidth() else it }
        .let { if (padding > 0.dp) it.padding(padding) else it }

    Column(
        modifier = finalModifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

/**
 * 填充整个屏幕的Column，便于创建全屏页面布局
 *
 * @param modifier 额外的修饰符
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun FullScreenColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .let { if (padding > 0.dp) it.padding(padding) else it },
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

/**
 * 垂直居中的Column组件
 *
 * @param modifier 额外的修饰符
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun CenterColumn(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 顶部对齐的Column组件
 *
 * @param modifier 额外的修饰符
 * @param horizontalAlignment 水平对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun TopColumn(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = horizontalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 底部对齐的Column组件
 *
 * @param modifier 额外的修饰符
 * @param horizontalAlignment 水平对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun BottomColumn(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = horizontalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 两端对齐的Column组件
 *
 * @param modifier 额外的修饰符
 * @param horizontalAlignment 水平对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun SpaceBetweenColumn(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = horizontalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 均匀分布的Column组件
 *
 * @param modifier 额外的修饰符
 * @param horizontalAlignment 水平对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun SpaceEvenlyColumn(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = horizontalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 居左对齐的Column组件
 *
 * @param modifier 额外的修饰符
 * @param verticalArrangement 垂直排列方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun StartAlignColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.Start,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 居右对齐的Column组件
 *
 * @param modifier 额外的修饰符
 * @param verticalArrangement 垂直排列方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun EndAlignColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.End,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 内容包裹的Column，不会填充最大宽度
 *
 * @param modifier 额外的修饰符
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun WrapColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .let { if (padding > 0.dp) it.padding(padding) else it },
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

/**
 * 垂直列表组件，带有预设的垂直间距
 *
 * @param modifier 额外的修饰符
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun VerticalList(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(SpaceVerticalMedium),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    fillMaxWidth: Boolean = true,
    padding: Dp = SpacePaddingMedium,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 卡片内容列表，适用于卡片内部的内容排列
 *
 * @param modifier 额外的修饰符
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun CardContentList(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(SpaceVerticalMedium),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    padding: Dp = SpacePaddingMedium,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

/**
 * 带小内边距的Column，常用于紧凑型布局
 *
 * @param modifier 额外的修饰符
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun SmallPaddingColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    fillMaxWidth: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = SpacePaddingSmall,
        content = content
    )
}

/**
 * 带中等内边距的Column，常用于普通布局
 *
 * @param modifier 额外的修饰符
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun MediumPaddingColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    fillMaxWidth: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = SpacePaddingMedium,
        content = content
    )
}

/**
 * 带大内边距的Column，常用于强调布局
 *
 * @param modifier 额外的修饰符
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun LargePaddingColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    fillMaxWidth: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    AppColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = SpacePaddingLarge,
        content = content
    )
} 