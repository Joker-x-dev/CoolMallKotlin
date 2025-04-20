package com.joker.coolmall.feature.user.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.viewmodel.AddressListViewModel

/**
 * 收货地址列表路由
 *
 * @param viewModel 收货地址列表ViewModel
 */
@Composable
internal fun AddressListRoute(
    viewModel: AddressListViewModel = hiltViewModel()
) {
    AddressListScreen(
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 收货地址列表界面
 *
 * @param onBackClick 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddressListScreen(
    onBackClick: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.address_list_title,
        onBackClick = onBackClick
    ) {
        Text(text = stringResource(id = R.string.address_list_title))
    }
}