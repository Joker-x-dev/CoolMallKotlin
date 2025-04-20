package com.joker.coolmall.feature.user.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.viewmodel.ProfileViewModel

/**
 * 个人中心路由
 *
 * @param viewModel 个人中心ViewModel
 */
@Composable
internal fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 个人中心界面
 *
 * @param onBackClick 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreen(
    onBackClick: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.profile_title,
        onBackClick = onBackClick
    ) {
        Text(text = stringResource(id = R.string.profile_title))
    }
} 