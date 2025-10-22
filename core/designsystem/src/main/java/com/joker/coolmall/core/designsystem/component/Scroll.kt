package com.joker.coolmall.core.designsystem.component

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall

/**
 * 垂直滚动组件，包含预设的修饰符和属性
 *
 * @param modifier 额外的修饰符
 * @param padding 内边距
 * @param horizontalAlignment 水平对齐方式
 * @param verticalArrangement 垂直排列方式
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度 (仅当fillMaxSize为false时生效)
 * @param flingBehavior 滑动行为
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun VerticalScroll(
    modifier: Modifier = Modifier,
    padding: Dp = 0.dp,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .let { if (fillMaxSize) it.fillMaxSize() else if (fillMaxWidth) it.fillMaxWidth() else it }
            .verticalScroll(scrollState, flingBehavior = flingBehavior)
            .let { if (padding > 0.dp) it.padding(padding) else it },
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        content = content
    )
}

/**
 * 带小内边距的垂直滚动组件
 *
 * @param modifier 额外的修饰符
 * @param horizontalAlignment 水平对齐方式
 * @param verticalArrangement 垂直排列方式
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun SmallPaddingVerticalScroll(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    VerticalScroll(
        modifier = modifier,
        padding = SpacePaddingSmall,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        content = content
    )
}

/**
 * 带中等内边距的垂直滚动组件
 *
 * @param modifier 额外的修饰符
 * @param horizontalAlignment 水平对齐方式
 * @param verticalArrangement 垂直排列方式
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun MediumPaddingVerticalScroll(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    VerticalScroll(
        modifier = modifier,
        padding = SpacePaddingMedium,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        content = content
    )
}

/**
 * 带大内边距的垂直滚动组件
 *
 * @param modifier 额外的修饰符
 * @param horizontalAlignment 水平对齐方式
 * @param verticalArrangement 垂直排列方式
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun LargePaddingVerticalScroll(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    VerticalScroll(
        modifier = modifier,
        padding = SpacePaddingLarge,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        content = content
    )
}

/**
 * 水平滚动组件，包含预设的修饰符和属性
 *
 * @param modifier 额外的修饰符
 * @param padding 内边距
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param flingBehavior 滑动行为
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun HorizontalScroll(
    modifier: Modifier = Modifier,
    padding: Dp = 0.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    content: @Composable RowScope.() -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scrollState, flingBehavior = flingBehavior)
            .let { if (padding > 0.dp) it.padding(padding) else it },
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content
    )
}

/**
 * 带小内边距的水平滚动组件
 *
 * @param modifier 额外的修饰符
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun SmallPaddingHorizontalScroll(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable RowScope.() -> Unit
) {
    HorizontalScroll(
        modifier = modifier,
        padding = SpacePaddingSmall,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content
    )
}

/**
 * 带中等内边距的水平滚动组件
 *
 * @param modifier 额外的修饰符
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param content 内容
 * @author Joker.X
 */
@Composable
fun MediumPaddingHorizontalScroll(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable RowScope.() -> Unit
) {
    HorizontalScroll(
        modifier = modifier,
        padding = SpacePaddingMedium,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content
    )
}