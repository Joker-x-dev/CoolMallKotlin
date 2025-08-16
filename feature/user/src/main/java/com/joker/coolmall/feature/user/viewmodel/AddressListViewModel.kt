package com.joker.coolmall.feature.user.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.AddressRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.common.Ids
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.feature.user.navigation.AddressDetailRoutes
import com.joker.coolmall.feature.user.navigation.AddressListRoutes
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.UserRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * 收货地址列表 ViewModel
 */
@HiltViewModel
class AddressListViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val addressRepository: AddressRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseNetWorkListViewModel<Address>(navigator, appState) {

    /**
     * 是否为选择模式
     */
    val isSelectMode = savedStateHandle.get<Boolean>(AddressListRoutes.IS_SELECT_MODE_ARG) ?: false

    /**
     * 是否显示删除确认弹窗
     */
    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog.asStateFlow()

    /**
     * 当前待删除的地址ID
     */
    private val _deleteId = MutableStateFlow<Long?>(null)
    val deleteId: StateFlow<Long?> = _deleteId.asStateFlow()

    init {
        initLoad()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     */
    override fun requestListData(): Flow<NetworkResponse<NetworkPageData<Address>>> {
        return addressRepository.getAddressPage(
            PageRequest(
                page = super.currentPage,
                size = super.pageSize
            )
        )
    }

    /**
     * 跳转到收货地址详情 - 新增模式
     */
    fun toAddressDetailPage() {
        val args = mapOf(
            AddressDetailRoutes.IS_EDIT_MODE_ARG to false, AddressDetailRoutes.ADDRESS_ID_ARG to ""
        )
        super.toPage(UserRoutes.ADDRESS_DETAIL, args)
    }

    /**
     * 跳转到收货地址详情 - 编辑模式
     *
     * @param addressId 待编辑的地址ID
     */
    fun toAddressDetailEditPage(addressId: Long) {
        val args = mapOf(
            AddressDetailRoutes.IS_EDIT_MODE_ARG to true,
            AddressDetailRoutes.ADDRESS_ID_ARG to addressId.toString()
        )
        super.toPage(UserRoutes.ADDRESS_DETAIL, args)
    }

    /**
     * 显示删除确认弹窗，并记录待删除的地址ID
     * @param id 待删除的地址ID
     */
    fun showDeleteDialog(id: Long) {
        _deleteId.value = id
        _showDeleteDialog.value = true
    }

    /**
     * 隐藏删除确认弹窗，并清空待删除ID
     */
    fun hideDeleteDialog() {
        _showDeleteDialog.value = false
        _deleteId.value = null
    }

    /**
     * 执行删除操作，删除当前待删除ID的地址，成功后刷新列表并关闭弹窗
     */
    fun deleteAddress() {
        val id = _deleteId.value ?: return
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = addressRepository.deleteAddress(Ids(listOf(id))).asResult(),
            onData = {
                onRefresh()
                hideDeleteDialog()
            }
        )
    }

    /**
     * 选择地址（仅在选择模式下使用）
     * 将选中的地址数据返回给上一个页面
     */
    private fun selectAddress(address: Address, backStackEntry: NavBackStackEntry?) {
        if (isSelectMode && backStackEntry != null) {
            // 使用kotlinx.serialization将Address对象序列化为JSON字符串后传递
            val addressJson = Json.encodeToString(address)
            super.navigateBack(mapOf("selected_address" to addressJson))
        }
    }

    /**
     * 处理地址卡片点击事件
     * 根据当前模式决定是编辑地址还是选择地址
     */
    fun onAddressClick(address: Address, backStackEntry: NavBackStackEntry? = null) {
        if (isSelectMode) {
            selectAddress(address, backStackEntry)
        } else {
            toAddressDetailEditPage(address.id)
        }
    }
}