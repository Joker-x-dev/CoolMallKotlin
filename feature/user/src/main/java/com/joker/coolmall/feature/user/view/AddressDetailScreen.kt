package com.joker.coolmall.feature.user.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ArrowRightIcon
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.ui.component.bottombar.AppBottomButton
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.component.RegionPickerModal
import com.joker.coolmall.feature.user.data.RegionData
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

    // 表单状态
    var contactName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var detailAddress by remember { mutableStateOf("") }
    var isDefaultAddress by remember { mutableStateOf(false) }

    // 地区选择器状态
    var showRegionPicker by remember { mutableStateOf(false) }
    // 创建无涟漪效果的交互源
    val interactionSource = remember { MutableInteractionSource() }

    AppScaffold(
        title = titleResId,
        bottomBar = {
            AppBottomButton(
                text = stringResource(id = R.string.save_address),
                onClick = onSaveClick
            )
        },
        onBackClick = onBackClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpacePaddingMedium)
        ) {

            Card {
                Column(
                    modifier = Modifier.padding(SpacePaddingMedium)
                ) {
                    // 联系人输入框
                    OutlinedTextField(
                        value = contactName,
                        onValueChange = { contactName = it },
                        label = { Text(stringResource(id = R.string.contact_person)) },
                        placeholder = { Text(stringResource(id = R.string.please_input_contact)) },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        shape = ShapeMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = SpacePaddingMedium)
                    )

                    // 手机号码输入框
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text(stringResource(id = R.string.phone_number)) },
                        placeholder = { Text(stringResource(id = R.string.please_input_phone)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        shape = ShapeMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = SpacePaddingMedium)
                    )

                    // 省市区选择器
                    Box {
                        OutlinedTextField(
                            value = region,
                            onValueChange = { region = it },
                            label = { Text(stringResource(id = R.string.region)) },
                            placeholder = { Text(stringResource(id = R.string.please_select_region)) },
                            readOnly = true,
                            trailingIcon = { ArrowRightIcon() },
                            shape = ShapeMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = SpacePaddingMedium)
                        )
                        // 添加一个透明覆盖层来捕获点击事件
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    showRegionPicker = true
                                }
                        )
                    }

                    // 详细地址输入框
                    OutlinedTextField(
                        value = detailAddress,
                        onValueChange = { detailAddress = it },
                        label = { Text(stringResource(id = R.string.detail_address)) },
                        placeholder = { Text(stringResource(id = R.string.street_number_etc)) },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        minLines = 3,
                        maxLines = 5,
                        shape = ShapeMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            SpaceVerticalLarge()

            // 设置默认地址开关
            DefaultAddressSwitch(
                isDefault = isDefaultAddress,
                onValueChange = { isDefaultAddress = it }
            )
        }

        // 地区选择对话框
        RegionPickerModal(
            visible = showRegionPicker,
            onDismiss = { showRegionPicker = false },
            regions = RegionData.getProvinces(),
            onRegionSelected = { selectedRegion ->
                region = selectedRegion
            },
            initialRegion = region
        )
    }
}

/**
 * 默认地址开关
 */
@Composable
private fun DefaultAddressSwitch(
    isDefault: Boolean,
    onValueChange: (Boolean) -> Unit,
) {
    Card {
        AppListItem(
            title = stringResource(id = R.string.set_default_address),
            description = stringResource(id = R.string.default_address_tip),
            showArrow = false,
            trailingContent = {
                Switch(
                    checked = isDefault,
                    onCheckedChange = onValueChange
                )
            }
        )
    }
}

@Composable
@Preview
internal fun AddressDetailScreenPreview() {
    AppTheme {
        AddressDetailScreen()
    }
}

@Composable
@Preview
internal fun AddressDetailScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        AddressDetailScreen()
    }
}