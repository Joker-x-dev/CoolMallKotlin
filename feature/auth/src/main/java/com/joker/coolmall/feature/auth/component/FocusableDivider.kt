package com.joker.coolmall.feature.auth.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge

/**
 * 可聚焦的分隔线组件，在输入框获取焦点时显示主题色，否则显示普通颜色
 *
 * @param focusState 聚焦状态，true表示已聚焦
 * @param modifier 可选修饰符
 * @param thickness 线条粗细，默认为1dp
 * @param withSpacing 是否在分隔线上方添加间距，默认为true
 * @author Joker.X
 */
@Composable
fun FocusableDivider(
    focusState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    thickness: Float = 1f,
    withSpacing: Boolean = true
) {
    if (withSpacing) {
        SpaceVerticalLarge()
    }

    HorizontalDivider(
        modifier = modifier,
        thickness = thickness.dp,
        color = if (focusState.value) Primary else MaterialTheme.colorScheme.outline
    )
} 