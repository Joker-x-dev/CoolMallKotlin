package com.joker.coolmall.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall

/**
 * 封装的Row组件，预设了常用的修饰符
 *
 * @param modifier 额外的修饰符
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun AppRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    val finalModifier = modifier
        .let { if (fillMaxWidth) it.fillMaxWidth() else it }
        .let { if (padding > 0.dp) it.padding(padding) else it }

    Row(
        modifier = finalModifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content
    )
}

/**
 * 水平居中的Row组件
 *
 * @param modifier 额外的修饰符
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun CenterRow(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    AppRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 两端对齐的Row组件
 *
 * @param modifier 额外的修饰符
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun SpaceBetweenRow(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    AppRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 均匀分布的Row组件
 *
 * @param modifier 额外的修饰符
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun SpaceEvenlyRow(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    AppRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 平均分布的Row组件（间距均匀）
 *
 * @param modifier 额外的修饰符
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun SpaceAroundRow(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    AppRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 左对齐的Row组件
 *
 * @param modifier 额外的修饰符
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun StartRow(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    AppRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 右对齐的Row组件
 *
 * @param modifier 额外的修饰符
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun EndRow(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    padding: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    AppRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 内容包裹的Row，不会填充最大宽度
 *
 * @param modifier 额外的修饰符
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun WrapRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    padding: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .let { if (padding > 0.dp) it.padding(padding) else it },
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content
    )
}

/**
 * 水平列表组件，带有预设的水平间距
 *
 * @param modifier 额外的修饰符
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param padding 内边距
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun HorizontalList(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(SpaceHorizontalSmall),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    fillMaxWidth: Boolean = true,
    padding: Dp = SpacePaddingMedium,
    content: @Composable RowScope.() -> Unit
) {
    AppRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = padding,
        content = content
    )
}

/**
 * 带小内边距的Row，常用于紧凑型布局
 *
 * @param modifier 额外的修饰符
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun SmallPaddingRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    fillMaxWidth: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    AppRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = SpacePaddingSmall,
        content = content
    )
}

/**
 * 带中等内边距的Row，常用于普通布局
 *
 * @param modifier 额外的修饰符
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun MediumPaddingRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    fillMaxWidth: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    AppRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = SpacePaddingMedium,
        content = content
    )
}

/**
 * 带大内边距的Row，常用于强调布局
 *
 * @param modifier 额外的修饰符
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun LargePaddingRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    fillMaxWidth: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    AppRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        fillMaxWidth = fillMaxWidth,
        padding = SpacePaddingLarge,
        content = content
    )
} 