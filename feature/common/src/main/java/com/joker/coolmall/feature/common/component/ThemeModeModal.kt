package com.joker.coolmall.feature.common.component

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.joker.coolmall.core.common.config.ThemePreference
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.modal.BottomModal
import com.joker.coolmall.feature.common.R

/**
 * 主题模式选择弹窗
 *
 * 封装底部选择框，供设置页及其他页面复用主题切换能力。
 *
 * @param visible 是否显示弹窗
 * @param selectedMode 当前选中的主题模式
 * @param onSelect 选择某个模式的回调
 * @param onDismiss 关闭回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeModeModal(
    visible: Boolean,
    selectedMode: ThemePreference,
    onSelect: (ThemePreference) -> Unit,
    onDismiss: () -> Unit
) {
    val options = listOf(
        ThemePreference.FOLLOW_SYSTEM to stringResource(id = R.string.settings_dark_mode_follow_system),
        ThemePreference.LIGHT to stringResource(id = R.string.settings_dark_mode_light),
        ThemePreference.DARK to stringResource(id = R.string.settings_dark_mode_dark)
    )

    BottomModal(
        visible = visible,
        title = stringResource(id = R.string.settings_dark_mode),
        onDismiss = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        indicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    ) {
        Card {
            options.forEachIndexed { index, (mode, label) ->
                AppListItem(
                    title = label,
                    showArrow = false,
                    horizontalPadding = SpaceHorizontalLarge,
                    verticalPadding = SpaceVerticalSmall,
                    showDivider = index != options.lastIndex,
                    trailingContent = {
                        RadioButton(
                            selected = selectedMode == mode,
                            onClick = { onSelect(mode) }
                        )
                    },
                    onClick = { onSelect(mode) }
                )
            }
        }
    }
}
