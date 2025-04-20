package com.joker.coolmall.feature.user.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.viewmodel.SettingsViewModel

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
        onBackClick = onBackClick
    ) {
        Text(text = stringResource(id = R.string.settings_title))
    }
} 