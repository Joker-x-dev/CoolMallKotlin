package com.joker.coolmall.feature.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.common.config.ThemeColorOption
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.modal.BottomModal
import com.joker.coolmall.feature.common.R

/**
 * 主题颜色选择弹窗
 *
 * @param visible 是否显示
 * @param selectedColor 当前选中的颜色
 * @param onSelect 选择颜色回调
 * @param onDismiss 关闭回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeColorModal(
    visible: Boolean,
    selectedColor: ThemeColorOption,
    onSelect: (ThemeColorOption) -> Unit,
    onDismiss: () -> Unit
) {
    val options = ThemeColorOption.entries

    BottomModal(
        visible = visible,
        title = stringResource(id = R.string.settings_theme_color),
        onDismiss = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        indicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    ) {
        Card {
            options.forEachIndexed { index, option ->
                AppListItem(
                    title = stringResource(id = option.labelRes()),
                    showArrow = false,
                    horizontalPadding = SpaceHorizontalLarge,
                    verticalPadding = SpaceVerticalSmall,
                    showDivider = index != options.lastIndex,
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ColorDot(color = Color(option.colorHex))
                            RadioButton(
                                selected = selectedColor == option,
                                onClick = { onSelect(option) }
                            )
                        }
                    },
                    onClick = { onSelect(option) }
                )
            }
        }
    }
}

@Composable
private fun ColorDot(
    color: Color,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.Canvas(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
    ) {
        drawCircle(color = color)
    }
}

private fun ThemeColorOption.labelRes(): Int = when (this) {
    ThemeColorOption.DEFAULT -> R.string.settings_theme_color_default
    ThemeColorOption.PURPLE -> R.string.settings_theme_color_purple
    ThemeColorOption.GREEN -> R.string.settings_theme_color_green
    ThemeColorOption.ORANGE -> R.string.settings_theme_color_orange
    ThemeColorOption.RED -> R.string.settings_theme_color_red
}
