package com.joker.coolmall.core.designsystem.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 布局间距规范
 * 设计规范中的px值转换为dp值（转换比例为1px = 0.5dp）
 */

/**
 * 超大垂直间距：32dp (64px)
 * 适用场景：页面块与块之间的大间距，如不同功能模块之间的分隔
 */
val SpaceVerticalXXLarge = 32.dp // 64px

/**
 * 特大垂直间距：24dp (48px)
 * 适用场景：大模块内部的分隔，如卡片组之间的间距
 */
val SpaceVerticalXLarge = 24.dp // 48px

/**
 * 大垂直间距：16dp (32px)
 * 适用场景：卡片内部主要内容块之间的间距
 */
val SpaceVerticalLarge = 16.dp // 32px

/**
 * 中等垂直间距：12dp (24px)
 * 适用场景：内容区域的常规间距，如列表项之间的间距
 */
val SpaceVerticalMedium = 12.dp // 24px

/**
 * 小垂直间距：8dp (16px)
 * 适用场景：紧凑布局中的间距，如图标与文字之间
 */
val SpaceVerticalSmall = 8.dp // 16px

/**
 * 超小垂直间距：4dp (8px)
 * 适用场景：最小的垂直间隔，如紧凑排列的元素间距
 */
val SpaceVerticalXSmall = 4.dp // 8px

/**
 * 超大水平间距：32dp (64px)
 * 适用场景：页面左右边距，大型容器的内边距
 */
val SpaceHorizontalXXLarge = 32.dp // 64px

/**
 * 特大水平间距：24dp (48px)
 * 适用场景：大型容器内部的水平分隔
 */
val SpaceHorizontalXLarge = 24.dp // 48px

/**
 * 大水平间距：16dp (32px)
 * 适用场景：常规容器的左右边距，如卡片的内边距
 */
val SpaceHorizontalLarge = 16.dp // 32px

/**
 * 中等水平间距：12dp (24px)
 * 适用场景：中等容器的水平间距，如列表项的左右间距
 */
val SpaceHorizontalMedium = 12.dp // 24px

/**
 * 小水平间距：8dp (16px)
 * 适用场景：紧凑布局的水平间距，如图标与文字之间
 */
val SpaceHorizontalSmall = 8.dp // 16px

/**
 * 超小平间间距：4dp (8px)
 * 适用场景：最小的平间间隔，如紧凑排列的元素间距
 */
val SpaceHorizontalXSmall = 4.dp // 8px

/**
 * 常用于内边距的值
 * 大内边距：16dp (32px)
 * 适用场景：卡片、对话框等容器的内边距
 */
val SpacePaddingLarge = 16.dp // 32px

/**
 * 中等内边距：12dp (24px)
 * 适用场景：按钮、输入框等中型组件的内边距
 */
val SpacePaddingMedium = 12.dp // 24px

/**
 * 小内边距：8dp (16px)
 * 适用场景：紧凑型组件的内边距，如小型按钮
 */
val SpacePaddingSmall = 8.dp // 16px

/**
 * 超小内边距：4dp (8px)
 * 适用场景：最小的内边距，如标签、徽章等小组件
 */
val SpacePaddingXSmall = 4.dp // 8px

/**
 * 其他常用尺寸
 * 分割线高度：0.5dp
 * 适用场景：列表项之间的分割线、边框线等
 */
val SpaceDivider = 0.5.dp // 分割线高度

/**
 * 指示器高度：2dp
 * 适用场景：选中指示器、进度条等
 */
val SpaceIndicator = 2.dp // 指示器高度

//region 垂直间距组件
/**
 * 创建一个超大垂直间距(32dp)的Spacer组件
 * 使用方式：SpaceVerticalXXLarge()
 * @author Joker.X
 */
@Composable
fun SpaceVerticalXXLarge() {
    Spacer(modifier = Modifier.height(SpaceVerticalXXLarge))
}

/**
 * 创建一个特大垂直间距(24dp)的Spacer组件
 * 使用方式：SpaceVerticalXLarge()
 * @author Joker.X
 */
@Composable
fun SpaceVerticalXLarge() {
    Spacer(modifier = Modifier.height(SpaceVerticalXLarge))
}

/**
 * 创建一个大垂直间距(16dp)的Spacer组件
 * 使用方式：SpaceVerticalLarge()
 * @author Joker.X
 */
@Composable
fun SpaceVerticalLarge() {
    Spacer(modifier = Modifier.height(SpaceVerticalLarge))
}

/**
 * 创建一个中等垂直间距(12dp)的Spacer组件
 * 使用方式：SpaceVerticalMedium()
 * @author Joker.X
 */
@Composable
fun SpaceVerticalMedium() {
    Spacer(modifier = Modifier.height(SpaceVerticalMedium))
}

/**
 * 创建一个小垂直间距(8dp)的Spacer组件
 * 使用方式：SpaceVerticalSmall()
 * @author Joker.X
 */
@Composable
fun SpaceVerticalSmall() {
    Spacer(modifier = Modifier.height(SpaceVerticalSmall))
}

/**
 * 创建一个超小垂直间距(4dp)的Spacer组件
 * 使用方式：SpaceVerticalXSmall()
 * @author Joker.X
 */
@Composable
fun SpaceVerticalXSmall() {
    Spacer(modifier = Modifier.height(SpaceVerticalXSmall))
}
//endregion

//region 水平间距组件
/**
 * 创建一个超大水平间距(32dp)的Spacer组件
 * 使用方式：SpaceHorizontalXXLarge()
 * @author Joker.X
 */
@Composable
fun SpaceHorizontalXXLarge() {
    Spacer(modifier = Modifier.width(SpaceHorizontalXXLarge))
}

/**
 * 创建一个特大水平间距(24dp)的Spacer组件
 * 使用方式：SpaceHorizontalXLarge()
 * @author Joker.X
 */
@Composable
fun SpaceHorizontalXLarge() {
    Spacer(modifier = Modifier.width(SpaceHorizontalXLarge))
}

/**
 * 创建一个大水平间距(16dp)的Spacer组件
 * 使用方式：SpaceHorizontalLarge()
 * @author Joker.X
 */
@Composable
fun SpaceHorizontalLarge() {
    Spacer(modifier = Modifier.width(SpaceHorizontalLarge))
}

/**
 * 创建一个中等水平间距(12dp)的Spacer组件
 * 使用方式：SpaceHorizontalMedium()
 * @author Joker.X
 */
@Composable
fun SpaceHorizontalMedium() {
    Spacer(modifier = Modifier.width(SpaceHorizontalMedium))
}

/**
 * 创建一个小水平间距(8dp)的Spacer组件
 * 使用方式：SpaceHorizontalSmall()
 * @author Joker.X
 */
@Composable
fun SpaceHorizontalSmall() {
    Spacer(modifier = Modifier.width(SpaceHorizontalSmall))
}

/**
 * 创建一个超小水平间距(4dp)的Spacer组件
 * 使用方式：SpaceHorizontalXSmall()
 * @author Joker.X
 */
@Composable
fun SpaceHorizontalXSmall() {
    Spacer(modifier = Modifier.width(SpaceHorizontalXSmall))
}
//endregion
