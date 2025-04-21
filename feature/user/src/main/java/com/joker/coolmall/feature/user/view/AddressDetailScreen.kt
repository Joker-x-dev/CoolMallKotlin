package com.joker.coolmall.feature.user.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
        isEditMode = viewModel.isEditMode,
        addressId = viewModel.addressId,
        onSaveClick = viewModel::saveAddress,
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 收货地址详情界面
 *
 * @param isEditMode 是否为编辑模式
 * @param addressId 地址ID，仅在编辑模式下使用
 * @param onSaveClick 保存按钮点击回调
 * @param onBackClick 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddressDetailScreen(
    isEditMode: Boolean = false,
    addressId: Long? = null,
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val titleResId = if (isEditMode) R.string.edit_address else R.string.add_address

    AppScaffold(
        title = titleResId,
        onBackClick = onBackClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isEditMode) {
                Text(text = "正在编辑地址，ID: $addressId")
            } else {
                Text(text = "新增地址")
            }

            // 这里添加地址表单内容

            Spacer(modifier = Modifier.weight(1f))

            // 底部保存按钮
            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.save_address))
            }
        }
    }
} 