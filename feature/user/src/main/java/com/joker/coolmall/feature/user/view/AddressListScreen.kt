package com.joker.coolmall.feature.user.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
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
        toAddressDetail = viewModel::toAddressDetailPage,
        toAddressDetailEdit = viewModel::toAddressDetailEditPage,
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 收货地址列表界面
 *
 * @param toAddressDetail 导航到收货地址详情（新增模式）
 * @param toAddressDetailEdit 导航到收货地址详情（编辑模式）
 * @param onBackClick 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddressListScreen(
    toAddressDetail: () -> Unit = {},
    toAddressDetailEdit: (Long) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.address_list_title,
        onBackClick = onBackClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Button(
                onClick = toAddressDetail
            ) {
                Text(text = stringResource(id = R.string.address_add_new))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 模拟一个现有地址，实际应该从列表中获取
            Button(
                onClick = { toAddressDetailEdit(123L) }
            ) {
                Text(text = stringResource(id = R.string.address_edit))
            }
        }
    }
}

@Composable
@Preview
internal fun AddressListScreenPreview() {
    AppTheme {
        AddressListScreen()
    }
}