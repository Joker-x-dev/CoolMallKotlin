package com.joker.coolmall.feature.user.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.AddressRepository
import com.joker.coolmall.core.model.common.Ids
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.popBackStackWithResult
import com.joker.coolmall.core.navigation.user.SelectAddressResultKey
import com.joker.coolmall.core.navigation.user.UserNavigator
import com.joker.coolmall.core.navigation.user.UserRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 收货地址列表 ViewModel
 *
 * @param navKey 路由参数
 * @param addressRepository 地址仓库
 * @author Joker.X
 */
@HiltViewModel(assistedFactory = AddressListViewModel.Factory::class)
class AddressListViewModel @AssistedInject constructor(
    @Assisted navKey: UserRoutes.AddressList,
    private val addressRepository: AddressRepository,
) : BaseNetWorkListViewModel<Address>() {

    /**
     * 是否为选择模式
     */
    private val addressListRoute = navKey

    /**
     * 是否为选择模式
     */
    val isSelectMode = addressListRoute.isSelectMode

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
        observeRefreshState()
        initLoad()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     *
     * @return 网络响应的Flow
     * @author Joker.X
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
     * 显示删除确认弹窗，并记录待删除的地址ID
     *
     * @param id 待删除的地址ID
     * @author Joker.X
     */
    fun showDeleteDialog(id: Long) {
        _deleteId.value = id
        _showDeleteDialog.value = true
    }

    /**
     * 隐藏删除确认弹窗，并清空待删除ID
     *
     * @author Joker.X
     */
    fun hideDeleteDialog() {
        _showDeleteDialog.value = false
        _deleteId.value = null
    }

    /**
     * 执行删除操作，删除当前待删除ID的地址，成功后刷新列表并关闭弹窗
     *
     * @author Joker.X
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
     * 处理地址卡片点击事件
     * 根据当前模式决定是编辑地址还是选择地址
     *
     * @param address 点击的地址
     * @author Joker.X
     */
    fun onAddressClick(address: Address) {
        if (isSelectMode) {
            // 使用类型安全的 NavigationResultKey 返回选中的地址
            popBackStackWithResult(SelectAddressResultKey, address)
        } else {
            UserNavigator.toAddressDetail(isEditMode = true, addressId = address.id)
        }
    }

    /**
     * Assisted Factory
     *
     * @author Joker.X
     */
    @AssistedFactory
    interface Factory {
        /**
         * 创建 ViewModel 实例
         *
         * @param navKey 路由参数
         * @return ViewModel 实例
         * @author Joker.X
         */
        fun create(navKey: UserRoutes.AddressList): AddressListViewModel
    }
}
