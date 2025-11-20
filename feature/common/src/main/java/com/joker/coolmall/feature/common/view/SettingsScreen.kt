package com.joker.coolmall.feature.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joker.coolmall.core.common.config.ThemeColorOption
import com.joker.coolmall.core.common.config.ThemePreference
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.common.R
import com.joker.coolmall.feature.common.component.ThemeColorModal
import com.joker.coolmall.feature.common.component.ThemeModeModal
import com.joker.coolmall.feature.common.viewmodel.SettingsViewModel

/**
 * 设置页面路由
 *
 * @param viewModel 设置页面ViewModel
 * @author Joker.X
 */
@Composable
internal fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    // 主题模式
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    // 是否显示主题选择弹窗
    val showThemeModal by viewModel.showThemeModal.collectAsStateWithLifecycle()
    // 主题颜色
    val themeColor by viewModel.themeColor.collectAsStateWithLifecycle()
    // 是否显示主题颜色选择弹窗
    val showThemeColorModal by viewModel.showThemeColorModal.collectAsStateWithLifecycle()

    SettingsScreen(
        themeMode = themeMode,
        themeColor = themeColor,
        showThemeModal = showThemeModal,
        showThemeColorModal = showThemeColorModal,
        onBackClick = viewModel::navigateBack,
        onUserAgreementClick = viewModel::onUserAgreementClick,
        onPrivacyPolicyClick = viewModel::onPrivacyPolicyClick,
        onAccountSecurityClick = viewModel::onAccountSecurityClick,
        onFeedbackClick = viewModel::onFeedbackClick,
        onAboutAppClick = viewModel::onAboutAppClick,
        onAppGuideClick = viewModel::onAppGuideClick,
        onDarkModeClick = viewModel::onDarkModeClick,
        onThemeModeSelected = viewModel::onThemeModeSelected,
        onThemeModalDismiss = viewModel::dismissThemeModal,
        onThemeColorClick = viewModel::onThemeColorClick,
        onThemeColorSelected = viewModel::onThemeColorSelected,
        onThemeColorModalDismiss = viewModel::dismissThemeColorModal
    )
}

/**
 * 设置页面界面
 *
 * @param themeMode 当前主题模式
 * @param themeColor 当前主题颜色
 * @param showThemeModal 是否显示主题选择弹窗
 * @param showThemeColorModal 是否显示主题颜色选择弹窗
 * @param onBackClick 返回上一页回调
 * @param onUserAgreementClick 用户协议点击回调
 * @param onPrivacyPolicyClick 隐私政策点击回调
 * @param onAccountSecurityClick 账号与安全点击回调
 * @param onFeedbackClick 意见反馈点击回调
 * @param onAboutAppClick 关于应用点击回调
 * @param onAppGuideClick 应用引导点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    themeMode: ThemePreference = ThemePreference.FOLLOW_SYSTEM,
    themeColor: ThemeColorOption = ThemeColorOption.DEFAULT,
    showThemeModal: Boolean = false,
    showThemeColorModal: Boolean = false,
    onBackClick: () -> Unit = {},
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onAccountSecurityClick: () -> Unit = {},
    onFeedbackClick: () -> Unit = {},
    onAboutAppClick: () -> Unit = {},
    onAppGuideClick: () -> Unit = {},
    onDarkModeClick: () -> Unit = {},
    onThemeModeSelected: (ThemePreference) -> Unit = {},
    onThemeModalDismiss: () -> Unit = {},
    onThemeColorClick: () -> Unit = {},
    onThemeColorSelected: (ThemeColorOption) -> Unit = {},
    onThemeColorModalDismiss: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.settings_title,
        useLargeTopBar = true,
        onBackClick = onBackClick
    ) {
        SettingsContentView(
            themeMode = themeMode,
            themeColor = themeColor,
            onUserAgreementClick = onUserAgreementClick,
            onPrivacyPolicyClick = onPrivacyPolicyClick,
            onAccountSecurityClick = onAccountSecurityClick,
            onFeedbackClick = onFeedbackClick,
            onAboutAppClick = onAboutAppClick,
            onAppGuideClick = onAppGuideClick,
            onDarkModeClick = onDarkModeClick,
            onThemeColorClick = onThemeColorClick
        )
    }

    ThemeModeModal(
        visible = showThemeModal,
        selectedMode = themeMode,
        onDismiss = onThemeModalDismiss,
        onSelect = onThemeModeSelected
    )

    ThemeColorModal(
        visible = showThemeColorModal,
        selectedColor = themeColor,
        onDismiss = onThemeColorModalDismiss,
        onSelect = onThemeColorSelected
    )
}

/**
 * 设置页面内容视图
 *
 * @param themeMode 当前主题模式
 * @param themeColor 当前主题颜色
 * @param onUserAgreementClick 用户协议点击回调
 * @param onPrivacyPolicyClick 隐私政策点击回调
 * @param onAccountSecurityClick 账号与安全点击回调
 * @param onFeedbackClick 意见反馈点击回调
 * @param onAboutAppClick 关于应用点击回调
 * @param onAppGuideClick 应用引导点击回调
 * @author Joker.X
 */
@Composable
private fun SettingsContentView(
    themeMode: ThemePreference = ThemePreference.FOLLOW_SYSTEM,
    themeColor: ThemeColorOption = ThemeColorOption.DEFAULT,
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onAccountSecurityClick: () -> Unit = {},
    onFeedbackClick: () -> Unit = {},
    onAboutAppClick: () -> Unit = {},
    onAppGuideClick: () -> Unit = {},
    onDarkModeClick: () -> Unit = {},
    onThemeColorClick: () -> Unit = {}
) {

    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        val themeModeText = when (themeMode) {
            ThemePreference.FOLLOW_SYSTEM -> stringResource(id = R.string.settings_dark_mode_follow_system)
            ThemePreference.LIGHT -> stringResource(id = R.string.settings_dark_mode_light)
            ThemePreference.DARK -> stringResource(id = R.string.settings_dark_mode_dark)
        }

        Card {
            AppListItem(
                title = stringResource(id = R.string.settings_account_security),
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                onClick = onAccountSecurityClick
            )
        }

        TitleWithLine(
            text = stringResource(id = R.string.settings_section_appearance),
            modifier = Modifier.padding(top = SpaceVerticalSmall)
        )

        Card {
            // 夜间模式
            AppListItem(
                title = stringResource(id = R.string.settings_dark_mode),
                showArrow = true,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingText = themeModeText,
                onClick = onDarkModeClick
            )

            // 主题颜色
            AppListItem(
                title = stringResource(id = R.string.settings_theme_color),
                showArrow = true,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingContent = {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(themeColor.colorHex))
                    )
                },
                onClick = onThemeColorClick
            )

            AppListItem(
                title = stringResource(id = R.string.settings_language),
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingText = stringResource(id = R.string.settings_language_chinese)
            )
        }

        TitleWithLine(
            text = stringResource(id = R.string.settings_section_privacy),
            modifier = Modifier.padding(top = SpaceVerticalSmall)
        )

        Card {
            // 隐私政策
            AppListItem(
                title = stringResource(id = R.string.about_privacy_policy),
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                onClick = onPrivacyPolicyClick
            )

            // 用户协议
            AppListItem(
                title = stringResource(id = R.string.about_user_agreement),
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                onClick = onUserAgreementClick
            )
        }

        TitleWithLine(
            text = stringResource(id = R.string.settings_section_other),
            modifier = Modifier.padding(top = SpaceVerticalSmall)
        )

        Card {
            // 展示启动页
            AppListItem(
                title = stringResource(id = R.string.settings_hide_splash),
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalSmall,
                showArrow = false,
                description = stringResource(id = R.string.settings_hide_splash_desc),
                trailingContent = {
                    var showSplash by remember { mutableStateOf(false) }
                    Switch(
                        checked = showSplash,
                        onCheckedChange = { showSplash = it }
                    )
                }
            )

            // 应用引导
            AppListItem(
                title = stringResource(id = R.string.settings_app_guide),
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                onClick = onAppGuideClick
            )
        }

        TitleWithLine(
            text = stringResource(id = R.string.settings_section_general),
            modifier = Modifier.padding(top = SpaceVerticalSmall)
        )

        Card {
            // 清除缓存
            AppListItem(
                title = stringResource(id = R.string.settings_clear_cache),
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingText = stringResource(id = R.string.settings_cache_size)
            )

            // 意见反馈
            AppListItem(
                title = stringResource(id = R.string.settings_feedback),
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                onClick = onFeedbackClick
            )

            // 关于我们
            AppListItem(
                title = stringResource(id = R.string.settings_about_app),
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                onClick = onAboutAppClick
            )
        }
    }
}

/**
 * 设置页面浅色主题预览
 *
 * @author Joker.X
 */
@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview() {
    AppTheme {
        SettingsScreen()
    }
}

/**
 * 设置页面深色主题预览
 *
 * @author Joker.X
 */
@Composable
@Preview(showBackground = true)
fun SettingsScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        SettingsScreen()
    }
}
