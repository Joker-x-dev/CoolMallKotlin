package com.joker.coolmall.core.designsystem.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.R

/**
 * 为应用封装的统一图标组件，简化官方图标的使用方式
 *
 * @param painter 图标画笔
 * @param contentDescription 图标的内容描述（辅助功能），可为空
 * @param modifier 修饰符
 * @param size 图标大小，为null时使用默认大小
 * @param tint 图标着色，默认使用当前内容颜色
 * @author Joker.X
 */
@Composable
fun CommonIcon(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    size: Dp? = 24.dp,
    tint: Color = LocalContentColor.current
) {
    val finalModifier = if (size != null) {
        modifier.then(Modifier.size(size))
    } else {
        modifier
    }

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = finalModifier,
        tint = tint
    )
}

/**
 * 使用ImageVector的图标组件
 *
 * @param imageVector 矢量图标
 * @param contentDescription 图标的内容描述（辅助功能），可为空
 * @param modifier 修饰符
 * @param size 图标大小，为null时使用默认大小
 * @param tint 图标着色，默认使用当前内容颜色
 * @author Joker.X
 */
@Composable
fun CommonIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    size: Dp? = 24.dp,
    tint: Color = LocalContentColor.current
) {
    val finalModifier = if (size != null) {
        modifier.then(Modifier.size(size))
    } else {
        modifier
    }

    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = finalModifier,
        tint = tint
    )
}

/**
 * 使用资源ID的图标组件
 *
 * @param resId 图标资源ID
 * @param contentDescription 图标的内容描述（辅助功能），可为空
 * @param modifier 修饰符
 * @param size 图标大小，为null时使用默认大小
 * @param tint 图标着色，默认使用当前内容颜色
 * @author Joker.X
 */
@Composable
fun CommonIcon(
    resId: Int,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    size: Dp? = 24.dp,
    tint: Color = LocalContentColor.current
) {
    CommonIcon(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        modifier = modifier,
        size = size,
        tint = tint
    )
}

//---------------------- 预定义图标组件 ----------------------

/**
 * 应用Logo图标组件
 *
 * @param modifier 修饰符
 * @param size 图标大小，默认24dp
 * @author Joker.X
 */
@Composable
fun LogoIcon(modifier: Modifier = Modifier, size: Dp = 24.dp) {
    Image(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = "Logo",
        modifier = modifier
            .size(size)
    )
}

/**
 * 左箭头图标组件，通常用于返回按钮
 *
 * @param modifier 修饰符
 * @param size 图标大小，默认为28dp
 * @param tint 图标着色，默认使用当前内容颜色
 * @author Joker.X
 */
@Composable
fun ArrowLeftIcon(
    modifier: Modifier = Modifier,
    size: Dp? = 28.dp,
    tint: Color = LocalContentColor.current
) {
    CommonIcon(
        resId = R.drawable.ic_left,
        modifier = modifier,
        size = size,
        tint = tint
    )
}

/**
 * 右箭头图标组件，通常用于更多或下一步按钮
 *
 * @param modifier 修饰符
 * @param size 图标大小，默认为24dp
 * @param tint 图标着色，默认使用当前内容颜色
 * @author Joker.X
 */
@Composable
fun ArrowRightIcon(
    modifier: Modifier = Modifier,
    size: Dp? = 24.dp,
    tint: Color = RightArrowGray
) {
    CommonIcon(
        resId = R.drawable.ic_right,
        modifier = modifier,
        size = size,
        tint = tint
    )
}