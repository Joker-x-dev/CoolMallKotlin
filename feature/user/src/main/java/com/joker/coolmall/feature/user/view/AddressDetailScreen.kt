package com.joker.coolmall.feature.user.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.viewmodel.AddressDetailViewModel

/**
 * 收货地址详情路由
 *
 * @param viewModel 收货地址详情ViewModel
 */
@Composable
internal fun AddressDetailRoute(
    viewModel: AddressDetailViewModel = hiltViewModel()
) {
    AddressDetailScreen(
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 收货地址详情界面
 *
 * @param onBackClick 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddressDetailScreen(
    onBackClick: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.address_detail_title,
        onBackClick = onBackClick
    ) {
        Text(text = stringResource(id = R.string.address_detail_title))
    }
} 