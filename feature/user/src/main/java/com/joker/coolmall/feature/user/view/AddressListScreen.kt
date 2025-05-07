package com.joker.coolmall.feature.user.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.model.Address
import com.joker.coolmall.core.ui.component.bottombar.AppBottomButton
import com.joker.coolmall.core.ui.component.dialog.WeDialog
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.tag.Tag
import com.joker.coolmall.core.ui.component.tag.TagSize
import com.joker.coolmall.core.ui.component.tag.TagType
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.viewmodel.AddressListViewModel
import com.joker.coolmall.core.ui.component.address.AddressCard
import com.joker.coolmall.core.ui.component.address.AddressActionButton

/**
 * 收货地址列表路由
 *
 * @param viewModel 收货地址列表ViewModel
 */
@Composable
internal fun AddressListRoute(
    viewModel: AddressListViewModel = hiltViewModel(),
    navController: NavController
) {
    // 注册页面刷新监听
    val backStackEntry = navController.currentBackStackEntry
    val uiState by viewModel.uiState.collectAsState()
    val showDeleteDialog by viewModel.showDeleteDialog.collectAsState()
    val deleteId by viewModel.deleteId.collectAsState()

    AddressListScreen(
        uiState = uiState,
        toAddressDetail = viewModel::toAddressDetailPage,
        toAddressDetailEdit = viewModel::toAddressDetailEditPage,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
        onDeleteClick = { viewModel.showDeleteDialog(it) }
    )

    // 删除确认对话框
    if (showDeleteDialog && deleteId != null) {
        WeDialog(
            title = stringResource(id = R.string.delete_address),
            content = stringResource(id = R.string.delete_address_confirm),
            okText = stringResource(android.R.string.ok),
            cancelText = stringResource(android.R.string.cancel),
            onOk = { viewModel.deleteAddress() },
            onCancel = { viewModel.hideDeleteDialog() },
            onDismiss = { viewModel.hideDeleteDialog() }
        )
    }

    // 只要backStackEntry不为null就注册监听
    LaunchedEffect(backStackEntry) {
        viewModel.observeRefreshState(backStackEntry)
    }
}

/**
 * 收货地址列表界面
 *
 * @param uiState 收货地址列表UI状态
 * @param toAddressDetail 导航到收货地址详情（新增模式）
 * @param toAddressDetailEdit 导航到收货地址详情（编辑模式）
 * @param onBackClick 返回上一页回调
 * @param onRetry 重试请求回调
 * @param onDeleteClick 删除地址回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddressListScreen(
    uiState: BaseNetWorkUiState<List<Address>> = BaseNetWorkUiState.Loading,
    toAddressDetail: () -> Unit = {},
    toAddressDetailEdit: (Long) -> Unit = {},
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onDeleteClick: (Long) -> Unit = {}
) {
    AppScaffold(
        title = R.string.address_list_title, onBackClick = onBackClick, bottomBar = {
            if (uiState is BaseNetWorkUiState.Success && uiState.data.isNotEmpty()) {
                AppBottomButton(
                    text = stringResource(id = R.string.address_add_new), onClick = toAddressDetail
                )
            }
        }) {
        BaseNetWorkView(
            uiState = uiState, onRetry = onRetry
        ) { data ->
            AddressListContentView(
                data = data,
                toAddressDetailEdit = toAddressDetailEdit,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

/**
 * 地址列表内容视图
 *
 * @param data 地址列表数据
 */
@Composable
private fun AddressListContentView(
    data: List<Address>,
    toAddressDetailEdit: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(SpacePaddingMedium),
        verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium)
    ) {
        // 地址项
        data.forEach { address ->
            AddressCard(
                address = address,
                onClick = { toAddressDetailEdit(address.id) },
                actionSlot = {
                    // 自定义操作区域 - 编辑和删除按钮
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 编辑按钮
                        AddressActionButton(
                            onClick = { toAddressDetailEdit(address.id) },
                            iconResId = R.drawable.ic_edit_fill
                        )

                        // 删除按钮
                        AddressActionButton(
                            onClick = { onDeleteClick(address.id) },
                            iconResId = R.drawable.ic_delete_fill
                        )
                    }
                }
            )
        }
    }
}

@Composable
@Preview
internal fun AddressListScreenPreview() {
    AppTheme {
        AddressListScreen(
            uiState = BaseNetWorkUiState.Success(
                listOf(
                    Address(
                        id = 1,
                        province = "广东省",
                        city = "深圳市",
                        district = "南山区",
                        address = "科技园南区T3栋 8楼",
                        contact = "张三",
                        phone = "13800138000",
                        isDefault = true
                    ), Address(
                        id = 2,
                        province = "广东省",
                        city = "广州市",
                        district = "天河区",
                        address = "天河路299号 天河商务大厦 12楼",
                        contact = "李四",
                        phone = "13900139000",
                        isDefault = false
                    )
                )
            )
        )
    }
}

@Composable
@Preview
internal fun AddressListScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        AddressListScreen(
            uiState = BaseNetWorkUiState.Success(
                listOf(
                    Address(
                        id = 1,
                        province = "广东省",
                        city = "深圳市",
                        district = "南山区",
                        address = "科技园南区T3栋 8楼",
                        contact = "张三",
                        phone = "13800138000",
                        isDefault = true
                    ), Address(
                        id = 2,
                        province = "广东省",
                        city = "广州市",
                        district = "天河区",
                        address = "天河路299号 天河商务大厦 12楼",
                        contact = "李四",
                        phone = "13900139000",
                        isDefault = false
                    )
                )
            )
        )
    }
}