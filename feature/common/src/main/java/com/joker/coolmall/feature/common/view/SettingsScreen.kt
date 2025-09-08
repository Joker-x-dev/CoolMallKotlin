package com.joker.coolmall.feature.common.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.common.R
import com.joker.coolmall.feature.common.viewmodel.SettingsViewModel

/**
 * 设置页面路由
 *
 * @param viewModel 设置页面ViewModel
 */
@Composable
internal fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    SettingsScreen(
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 设置页面界面
 *
 * @param onBackClick 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    onBackClick: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.settings_title,
        useLargeTopBar = true,
        onBackClick = onBackClick
    ) {
        SettingsContentView()
    }
}

/**
 * 设置页面内容视图
 */
@Composable
private fun SettingsContentView() {

    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        Card {
            AppListItem(
                title = "账号与安全",
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
            )
        }

        TitleWithLine(
            text = "外观与语言",
            modifier = Modifier.padding(top = SpaceVerticalSmall)
        )

        Card {
            // 夜间模式
            AppListItem(
                title = "夜间模式",
                showArrow = true,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingText = "跟随系统"
            )

            // 动态主题色
            AppListItem(
                title = "动态主题色",
                showArrow = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalSmall,
                description = "开启后应用主题色会同步系统壁纸颜色",
                trailingContent = {
                    var isDynamicColor by remember { mutableStateOf(false) }
                    Switch(
                        checked = isDynamicColor,
                        onCheckedChange = { isDynamicColor = it }
                    )
                }
            )

            AppListItem(
                title = "语言切换",
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingText = "中文"
            )
        }

        TitleWithLine(
            text = "隐私与协议",
            modifier = Modifier.padding(top = SpaceVerticalSmall)
        )

        Card {
            // 隐私政策
            AppListItem(
                title = "隐私政策",
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )

            // 用户协议
            AppListItem(
                title = "用户协议",
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
        }

        TitleWithLine(
            text = "其他",
            modifier = Modifier.padding(top = SpaceVerticalSmall)
        )

        Card {
            // 展示启动页
            AppListItem(
                title = "不展示启动页",
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalSmall,
                showArrow = false,
                description = "打开后进入应用将跳过启动页",
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
                title = "应用引导",
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
        }

        TitleWithLine(
            text = "通用",
            modifier = Modifier.padding(top = SpaceVerticalSmall)
        )

        Card {
            // 清除缓存
            AppListItem(
                title = "清除缓存",
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingText = "13.24MB"
            )

            // 意见反馈
            AppListItem(
                title = "意见反馈",
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )

            // 关于我们
            AppListItem(
                title = "关于应用",
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
        }
    }
}

/**
 * 设置页面浅色主题预览
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
 */
@Composable
@Preview(showBackground = true)
fun SettingsScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        SettingsScreen()
    }
}