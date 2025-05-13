package com.joker.coolmall.feature.order.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.AddressRepository
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.entity.SelectedGoods
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 订单确认页面ViewModel
 */
@HiltViewModel
class OrderConfirmViewModel @Inject constructor(
    navigator: AppNavigator,
    private val addressRepository: AddressRepository
) : BaseNetWorkViewModel<Address>(navigator) {

    /**
     * 选中的商品
     * 从缓存中获取选中的商品列表
     */
    val selectedGoods: List<SelectedGoods>? =
        MMKVUtils.getObject<List<SelectedGoods>>("selectedGoodsList")

    /*val cartList = selectedGoods
        ?.groupBy { it.goodsId }
        ?.map { (goodsId, group) ->
            Cart().apply {
                this.goodsId = goodsId
                this.goodsName = group.first().goodsInfo?.title
                this.goodsMainPic = group.first().goodsInfo?.mainPic
                // 把所有规格都放进 spec 列表
                this.spec = group.mapNotNull { it.spec }
            }
        } ?: emptyList()*/

    init {
        executeRequest()
//        MMKVUtils.remove("selectedGoodsList")
    }

    override fun requestApiFlow(): Flow<NetworkResponse<Address>> {
        return addressRepository.getDefaultAddress()
    }
}