package com.joker.coolmall.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall

/**
 * 垂直懒加载列表组件，预设的常用属性
 *
 * @param modifier 额外的修饰符
 * @param listState 列表状态
 * @param contentPadding 内容内边距
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param reverseLayout 是否反向布局
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度（当fillMaxSize为false时生效）
 * @param content 列表内容构建器
 * @author Joker.X
 */
@Composable
fun AppLazyColumn(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    reverseLayout: Boolean = false,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier.let {
            if (fillMaxSize) it.fillMaxSize()
            else if (fillMaxWidth) it.fillMaxWidth()
            else it
        },
        state = listState,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        reverseLayout = reverseLayout,
        content = content
    )
}

/**
 * 带小内边距的垂直懒加载列表
 *
 * @param modifier 额外的修饰符
 * @param listState 列表状态
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param reverseLayout 是否反向布局
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 列表内容构建器
 * @author Joker.X
 */
@Composable
fun SmallPaddingLazyColumn(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    reverseLayout: Boolean = false,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    AppLazyColumn(
        modifier = modifier,
        listState = listState,
        contentPadding = PaddingValues(SpacePaddingSmall),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        reverseLayout = reverseLayout,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        content = content
    )
}

/**
 * 带中等内边距的垂直懒加载列表
 *
 * @param modifier 额外的修饰符
 * @param listState 列表状态
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param reverseLayout 是否反向布局
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 列表内容构建器
 * @author Joker.X
 */
@Composable
fun MediumPaddingLazyColumn(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    reverseLayout: Boolean = false,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    AppLazyColumn(
        modifier = modifier,
        listState = listState,
        contentPadding = PaddingValues(SpacePaddingMedium),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        reverseLayout = reverseLayout,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        content = content
    )
}

/**
 * 带大内边距的垂直懒加载列表
 *
 * @param modifier 额外的修饰符
 * @param listState 列表状态
 * @param verticalArrangement 垂直排列方式
 * @param horizontalAlignment 水平对齐方式
 * @param reverseLayout 是否反向布局
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 列表内容构建器
 * @author Joker.X
 */
@Composable
fun LargePaddingLazyColumn(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    reverseLayout: Boolean = false,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    AppLazyColumn(
        modifier = modifier,
        listState = listState,
        contentPadding = PaddingValues(SpacePaddingLarge),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        reverseLayout = reverseLayout,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        content = content
    )
}

/**
 * 垂直列表项目，预设了间距
 *
 * @param modifier 额外的修饰符
 * @param listState 列表状态
 * @param contentPadding 内容内边距
 * @param itemSpacing 列表项间距
 * @param horizontalAlignment 水平对齐方式
 * @param reverseLayout 是否反向布局
 * @param fillMaxSize 是否填充最大尺寸
 * @param fillMaxWidth 是否填充最大宽度
 * @param content 列表内容构建器
 * @author Joker.X
 */
@Composable
fun VerticalListItems(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(SpacePaddingMedium),
    itemSpacing: Dp = SpaceVerticalSmall,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    reverseLayout: Boolean = false,
    fillMaxSize: Boolean = false,
    fillMaxWidth: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    AppLazyColumn(
        modifier = modifier,
        listState = listState,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(itemSpacing),
        horizontalAlignment = horizontalAlignment,
        reverseLayout = reverseLayout,
        fillMaxSize = fillMaxSize,
        fillMaxWidth = fillMaxWidth,
        content = content
    )
}

/**
 * 水平懒加载列表组件，预设的常用属性
 *
 * @param modifier 额外的修饰符
 * @param listState 列表状态
 * @param contentPadding 内容内边距
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param reverseLayout 是否反向布局
 * @param content 列表内容构建器
 * @author Joker.X
 */
@Composable
fun AppLazyRow(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    reverseLayout: Boolean = false,
    content: LazyListScope.() -> Unit
) {
    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        reverseLayout = reverseLayout,
        content = content
    )
}

/**
 * 带小内边距的水平懒加载列表
 *
 * @param modifier 额外的修饰符
 * @param listState 列表状态
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param reverseLayout 是否反向布局
 * @param content 列表内容构建器
 * @author Joker.X
 */
@Composable
fun SmallPaddingLazyRow(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    reverseLayout: Boolean = false,
    content: LazyListScope.() -> Unit
) {
    AppLazyRow(
        modifier = modifier,
        listState = listState,
        contentPadding = PaddingValues(SpacePaddingSmall),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        reverseLayout = reverseLayout,
        content = content
    )
}

/**
 * 带中等内边距的水平懒加载列表
 *
 * @param modifier 额外的修饰符
 * @param listState 列表状态
 * @param horizontalArrangement 水平排列方式
 * @param verticalAlignment 垂直对齐方式
 * @param reverseLayout 是否反向布局
 * @param content 列表内容构建器
 * @author Joker.X
 */
@Composable
fun MediumPaddingLazyRow(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    reverseLayout: Boolean = false,
    content: LazyListScope.() -> Unit
) {
    AppLazyRow(
        modifier = modifier,
        listState = listState,
        contentPadding = PaddingValues(SpacePaddingMedium),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        reverseLayout = reverseLayout,
        content = content
    )
}
