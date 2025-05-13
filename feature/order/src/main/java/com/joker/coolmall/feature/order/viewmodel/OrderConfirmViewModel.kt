package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.AddressRepository
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec
import com.joker.coolmall.core.model.entity.SelectedGoods
import com.joker.coolmall.core.model.request.CreateOrderRequest
import com.joker.coolmall.core.model.request.CreateOrderRequest.CreateOrder
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 订单确认页面ViewModel
 */
@HiltViewModel
class OrderConfirmViewModel @Inject constructor(
    navigator: AppNavigator,
    private val addressRepository: AddressRepository,
    private val orderRepository: OrderRepository
) : BaseNetWorkViewModel<Address>(navigator) {

    /**
     * 选中的商品
     * 从缓存中获取选中的商品列表
     */
    val selectedGoodsList: List<SelectedGoods>? =
        MMKVUtils.getObject<List<SelectedGoods>>("selectedGoodsList")

    /**
     * 购物车列表
     * 从选中的商品列表中获取购物车列表
     */
    val cartList = selectedGoodsList?.let { goods ->
        // 按商品ID分组
        val groupedGoods = goods.groupBy { it.goodsId }

        // 为每个商品ID创建一个Cart对象
        groupedGoods.map { (goodsId, items) ->
            val firstItem = items.first()

            Cart().apply {
                this.goodsId = goodsId
                this.goodsName = firstItem.goodsInfo?.title ?: ""
                this.goodsMainPic = firstItem.goodsInfo?.mainPic ?: ""

                // 收集该商品的所有规格
                val allSpecs = mutableListOf<CartGoodsSpec>()

                // 遍历该商品的所有选中项
                items.forEach { selectedItem ->
                    // 如果有规格信息，转换为CartGoodsSpec并添加
                    selectedItem.spec?.let { spec ->
                        val cartSpec = CartGoodsSpec(
                            id = spec.id,
                            goodsId = spec.goodsId,
                            name = spec.name,
                            price = spec.price,
                            stock = spec.stock,
                            count = selectedItem.count,
                            images = spec.images
                        )
                        allSpecs.add(cartSpec)
                    }
                }

                // 设置规格列表
                this.spec = allSpecs
            }
        }
    } ?: emptyList()

    init {
        executeRequest()
        MMKVUtils.remove("selectedGoodsList")
    }

    override fun requestApiFlow(): Flow<NetworkResponse<Address>> {
        return addressRepository.getDefaultAddress()
    }

    /**
     * 提交订单点击事件
     */
    fun onSubmitOrderClick() {
        val addressId = (uiState.value as? BaseNetWorkUiState.Success)?.data?.id ?: return

        // 创建订单请求参数
        val params = CreateOrderRequest(
            data = CreateOrder(
                addressId = addressId,
                goodsList = selectedGoodsList ?: emptyList(),
                title = "购买商品",
                remark = "顺丰快递"
            )
        )

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.createOrder(params).asResult(),
            showToast = true,
            onData = { data ->
                ToastUtils.showSuccess("订单创建成功")
            }
        )
    }
}