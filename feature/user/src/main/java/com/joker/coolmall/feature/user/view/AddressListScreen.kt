package com.joker.coolmall.feature.user.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.preview.previewAddressList
import com.joker.coolmall.core.navigation.navigateBack
import com.joker.coolmall.core.navigation.user.UserNavigator
import com.joker.coolmall.core.navigation.user.UserRoutes
import com.joker.coolmall.core.ui.component.address.AddressActionButton
import com.joker.coolmall.core.ui.component.address.AddressCard
import com.joker.coolmall.core.ui.component.bottombar.AppBottomButton
import com.joker.coolmall.core.ui.component.dialog.WeDialog
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.viewmodel.AddressListViewModel

/**
 * 收货地址列表路由
 *
 * @param navKey 路由参数
 * @param viewModel 收货地址列表ViewModel
 * @author Joker.X
 */
@Composable
internal fun AddressListRoute(
    navKey: UserRoutes.AddressList,
    viewModel: AddressListViewModel = hiltViewModel<AddressListViewModel, AddressListViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(navKey)
        }
    ),
) {
    val uiState by viewModel.uiState.collectAsState()
    val listData by viewModel.listData.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val loadMoreState by viewModel.loadMoreState.collectAsState()
    val showDeleteDialog by viewModel.showDeleteDialog.collectAsState()
    val deleteId by viewModel.deleteId.collectAsState()

    AddressListScreen(
        uiState = uiState,
        listData = listData,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = viewModel::onRefresh,
        onLoadMore = viewModel::onLoadMore,
        shouldTriggerLoadMore = viewModel::shouldTriggerLoadMore,
        onRetry = viewModel::retryRequest,
        onDeleteClick = { viewModel.showDeleteDialog(it) },
        isSelectMode = viewModel.isSelectMode,
        onAddressClick = viewModel::onAddressClick
    )

    // 删除确认对话框
    if (showDeleteDialog && deleteId != null) {
        WeDialog(
            title = stringResource(id = R.string.delete_address),
            content = stringResource(id = R.string.delete_address_confirm),
            okText = stringResource(R.string.ok),
            cancelText = stringResource(R.string.cancel),
            onOk = { viewModel.deleteAddress() },
            onCancel = { viewModel.hideDeleteDialog() },
            onDismiss = { viewModel.hideDeleteDialog() }
        )
    }
}

/**
 * 收货地址列表界面
 *
 * @param uiState 收货地址列表UI状态
 * @param listData 收货地址列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 * @param onRetry 重试请求回调
 * @param onDeleteClick 删除地址回调
 * @param isSelectMode 是否为选择模式
 * @param onAddressClick 地址点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddressListScreen(
    uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
    listData: List<Address> = emptyList(),
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
    onRetry: () -> Unit = {},
    onDeleteClick: (Long) -> Unit = {},
    isSelectMode: Boolean = false,
    onAddressClick: (Address) -> Unit = {}
) {
    AppScaffold(
        title = R.string.address_list_title,
        onBackClick = { navigateBack() },
        bottomBar = {
            if (uiState != BaseNetWorkListUiState.Loading && uiState != BaseNetWorkListUiState.Error) {
                AppBottomButton(
                    text = stringResource(id = R.string.address_add_new),
                    onClick = { UserNavigator.toAddressDetail(isEditMode = false, addressId = 0L) }
                )
            }
        }) {
        BaseNetWorkListView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            AddressListContentView(
                data = listData,
                isRefreshing = isRefreshing,
                loadMoreState = loadMoreState,
                onRefresh = onRefresh,
                onLoadMore = onLoadMore,
                shouldTriggerLoadMore = shouldTriggerLoadMore,
                onDeleteClick = onDeleteClick,
                isSelectMode = isSelectMode,
                onAddressClick = onAddressClick
            )
        }
    }
}

/**
 * 地址列表内容视图
 *
 * @param data 地址列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 刷新回调
 * @param onLoadMore 加载更多回调
 * @param onRetry 重试请求回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 * @param onDeleteClick 删除地址回调
 * @param isSelectMode 是否为选择模式
 * @param onAddressClick 地址点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddressListContentView(
    data: List<Address>,
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    onDeleteClick: (Long) -> Unit,
    isSelectMode: Boolean = false,
    onAddressClick: (Address) -> Unit = {}
) {
    RefreshLayout(
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore,
    ) {
        // 地址列表项
        items(data.size) { index ->
            AddressCard(
                address = data[index],
                onClick = {
                    if (isSelectMode) {
                        onAddressClick(data[index])
                    } else {
                        UserNavigator.toAddressDetail(isEditMode = true, addressId = data[index].id)
                    }
                },
                actionSlot = {
                    // 自定义操作区域 - 编辑和删除按钮
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 编辑按钮
                        AddressActionButton(
                            onClick = {
                                UserNavigator.toAddressDetail(
                                    isEditMode = true,
                                    addressId = data[index].id
                                )
                            },
                            iconResId = R.drawable.ic_edit_fill
                        )

                        // 删除按钮
                        AddressActionButton(
                            onClick = { onDeleteClick(data[index].id) },
                            iconResId = R.drawable.ic_delete_fill
                        )
                    }
                }
            )
        }
    }
}

/**
 * 收货地址列表界面浅色主题预览
 *
 * @author Joker.X
 */
@Composable
@Preview
internal fun AddressListScreenPreview() {
    AppTheme {
        AddressListScreen(
            uiState = BaseNetWorkListUiState.Success,
            listData = previewAddressList
        )
    }
}

/**
 * 收货地址列表界面深色主题预览
 *
 * @author Joker.X
 */
@Composable
@Preview
internal fun AddressListScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        AddressListScreen(
            uiState = BaseNetWorkListUiState.Success,
            listData = previewAddressList
        )
    }
}
