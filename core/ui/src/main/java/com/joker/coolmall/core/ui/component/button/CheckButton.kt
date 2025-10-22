package com.joker.coolmall.core.ui.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.ui.R

/**
 * 通用选择按钮组件
 *
 * 用于显示选择状态的圆形按钮，支持自定义大小和形状
 * 广泛应用于购物车选择、订单支付选择等场景
 *
 * @param selected 是否选中
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param size 按钮大小，默认24dp
 * @param shape 按钮形状，默认圆形
 * @param selectedIcon 选中时的图标资源ID (带勾的圆圈)
 * @param unselectedIcon 未选中时的图标资源ID (空圆圈)
 * @author Joker.X
 */
@Composable
fun CheckButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Int = 24,
    shape: Shape = ShapeCircle,
    selectedIcon: Int = R.drawable.ic_success_circle,
    unselectedIcon: Int = R.drawable.ic_circle
) {
    CommonIcon(
        resId = if (selected) selectedIcon else unselectedIcon,
        contentDescription = stringResource(id = if (selected) R.string.selected else R.string.unselected),
        modifier = modifier
            .size(size.dp)
            .clip(shape)
            .clickable(onClick = onClick),
        tint = if (selected) Primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
            alpha = 0.6f
        )
    )
} 