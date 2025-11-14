package com.joker.coolmall.feature.user.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ArrowRightIcon
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.ui.component.bottombar.AppBottomButton
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.component.RegionPickerModal
import com.joker.coolmall.feature.user.data.RegionData
import com.joker.coolmall.feature.user.viewmodel.AddressDetailViewModel

/**
 * 收货地址详情路由
 *
 * @param viewModel 收货地址详情ViewModel
 * @author Joker.X
 */
@Composable
internal fun AddressDetailRoute(
    viewModel: AddressDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val contactName by viewModel.contactName.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val province by viewModel.province.collectAsState()
    val city by viewModel.city.collectAsState()
    val district by viewModel.district.collectAsState()
    val detailAddress by viewModel.detailAddress.collectAsState()
    val isDefaultAddress by viewModel.isDefaultAddress.collectAsState()

    AddressDetailScreen(
        isEditMode = viewModel.isEditMode,
        uiState = uiState,
        contactName = contactName,
        phone = phone,
        province = province,
        city = city,
        district = district,
        detailAddress = detailAddress,
        isDefaultAddress = isDefaultAddress,
        onContactNameChange = viewModel::updateContactName,
        onPhoneChange = viewModel::updatePhone,
        onRegionChange = viewModel::updateRegion,
        onDetailAddressChange = viewModel::updateDetailAddress,
        onDefaultAddressChange = viewModel::updateIsDefaultAddress,
        onSaveClick = viewModel::saveAddress,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest
    )
}

/**
 * 收货地址详情界面
 *
 * @param isEditMode 是否为编辑模式
 * @param uiState 界面状态
 * @param contactName 联系人
 * @param phone 手机号
 * @param province 省
 * @param city 市
 * @param district 区
 * @param detailAddress 详细地址
 * @param isDefaultAddress 是否为默认地址
 * @param onContactNameChange 联系人变更回调
 * @param onPhoneChange 手机号变更回调
 * @param onRegionChange 地区变更回调
 * @param onDetailAddressChange 详细地址变更回调
 * @param onDefaultAddressChange 默认地址变更回调
 * @param onSaveClick 保存按钮点击回调
 * @param onBackClick 返回上一页回调
 * @param onRetry 重试请求回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddressDetailScreen(
    isEditMode: Boolean = false,
    uiState: BaseNetWorkUiState<Address> = BaseNetWorkUiState.Loading,
    contactName: String = "",
    phone: String = "",
    province: String = "",
    city: String = "",
    district: String = "",
    detailAddress: String = "",
    isDefaultAddress: Boolean = false,
    onContactNameChange: (String) -> Unit = {},
    onPhoneChange: (String) -> Unit = {},
    onRegionChange: (String, String, String) -> Unit = { _, _, _ -> },
    onDetailAddressChange: (String) -> Unit = {},
    onDefaultAddressChange: (Boolean) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    val titleResId = if (isEditMode) R.string.edit_address else R.string.add_address

    AppScaffold(
        title = titleResId,
        useLargeTopBar = true,
        bottomBar = {
            if (uiState is BaseNetWorkUiState.Success) {
                AppBottomButton(
                    text = stringResource(id = R.string.save_address),
                    onClick = onSaveClick
                )
            }
        },
        onBackClick = onBackClick
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            AddressDetailContentView(
                contactName = contactName,
                phone = phone,
                province = province,
                city = city,
                district = district,
                detailAddress = detailAddress,
                isDefaultAddress = isDefaultAddress,
                onContactNameChange = onContactNameChange,
                onPhoneChange = onPhoneChange,
                onRegionChange = onRegionChange,
                onDetailAddressChange = onDetailAddressChange,
                onDefaultAddressChange = onDefaultAddressChange
            )
        }
    }
}

/**
 * 收货地址详情内容视图
 *
 * @param contactName 联系人
 * @param phone 手机号
 * @param province 省
 * @param city 市
 * @param district 区
 * @param detailAddress 详细地址
 * @param isDefaultAddress 是否为默认地址
 * @param onContactNameChange 联系人变更回调
 * @param onPhoneChange 手机号变更回调
 * @param onRegionChange 地区变更回调
 * @param onDetailAddressChange 详细地址变更回调
 * @param onDefaultAddressChange 默认地址变更回调
 * @author Joker.X
 */
@Composable
private fun AddressDetailContentView(
    contactName: String,
    phone: String,
    province: String,
    city: String,
    district: String,
    detailAddress: String,
    isDefaultAddress: Boolean,
    onContactNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onRegionChange: (String, String, String) -> Unit,
    onDetailAddressChange: (String) -> Unit,
    onDefaultAddressChange: (Boolean) -> Unit
) {
    // 地区选择器状态
    var showRegionPicker by remember { mutableStateOf(false) }
    // 创建无涟漪效果的交互源
    val interactionSource = remember { MutableInteractionSource() }

    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        // 基本信息卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = ShapeMedium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacePaddingMedium),
                // 设置每项之间的间隔
                verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium)
            ) {
                // 联系人
                OutlinedTextField(
                    value = contactName,
                    onValueChange = onContactNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.contact_person)) },
                    placeholder = { Text(text = stringResource(id = R.string.please_input_contact)) },
                    singleLine = true,
                    shape = ShapeMedium,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                // 手机号
                OutlinedTextField(
                    value = phone,
                    onValueChange = onPhoneChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.phone_number)) },
                    placeholder = { Text(text = stringResource(id = R.string.please_input_phone)) },
                    singleLine = true,
                    shape = ShapeMedium,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    )
                )

                // 省市区
                Box {
                    OutlinedTextField(
                        value = if (province.isNotEmpty() && city.isNotEmpty() && district.isNotEmpty()) {
                            "$province $city $district"
                        } else "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = { ArrowRightIcon() },
                        shape = ShapeMedium,
                        label = { Text(text = stringResource(id = R.string.region)) },
                        placeholder = { Text(text = stringResource(id = R.string.please_select_region)) },
                        singleLine = true,
                        enabled = true
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

                // 详细地址
                OutlinedTextField(
                    value = detailAddress,
                    onValueChange = onDetailAddressChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.detail_address)) },
                    placeholder = { Text(text = stringResource(id = R.string.street_number_etc)) },
//                    singleLine = true,
                    shape = ShapeMedium,
                    minLines = 3,
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
            }
        }

        // 设置默认地址开关
        DefaultAddressSwitch(
            isDefault = isDefaultAddress,
            onValueChange = onDefaultAddressChange
        )
    }

    // 地区选择对话框
    RegionPickerModal(
        visible = showRegionPicker,
        onDismiss = { showRegionPicker = false },
        regions = RegionData.getProvinces(),
        onRegionSelected = { selectedRegion ->
            // 解析选中的地区
            val parts = selectedRegion.split(" ")
            if (parts.size == 3) {
                onRegionChange(parts[0], parts[1], parts[2])
            }
        },
        initialRegion = if (province.isNotEmpty() && city.isNotEmpty() && district.isNotEmpty()) {
            "$province $city $district"
        } else ""
    )
}

/**
 * 默认地址开关
 *
 * @param isDefault 是否为默认地址
 * @param onValueChange 开关变更回调
 * @author Joker.X
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

/**
 * 收货地址详情界面浅色主题预览
 *
 * @author Joker.X
 */
@Composable
@Preview
internal fun AddressDetailScreenPreview() {
    AppTheme {
        AddressDetailScreen()
    }
}

/**
 * 收货地址详情界面深色主题预览
 *
 * @author Joker.X
 */
@Composable
@Preview
internal fun AddressDetailScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        AddressDetailScreen()
    }
}