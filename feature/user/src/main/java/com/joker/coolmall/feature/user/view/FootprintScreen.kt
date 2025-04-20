package com.joker.coolmall.feature.user.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.viewmodel.FootprintViewModel

/**
 * 用户足迹路由
 *
 * @param viewModel 用户足迹ViewModel
 */
@Composable
internal fun FootprintRoute(
    viewModel: FootprintViewModel = hiltViewModel()
) {
    FootprintScreen(
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 用户足迹界面
 *
 * @param onBackClick 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FootprintScreen(
    onBackClick: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.footprint_title,
        onBackClick = onBackClick
    ) {
        Text(text = stringResource(id = R.string.footprint_title))
    }
} 