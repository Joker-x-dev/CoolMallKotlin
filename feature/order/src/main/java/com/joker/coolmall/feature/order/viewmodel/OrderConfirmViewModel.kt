package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.data.repository.AddressRepository
import com.joker.coolmall.core.data.repository.CartRepository
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.entity.SelectedGoods
import com.joker.coolmall.core.model.request.CreateOrderRequest
import com.joker.coolmall.core.model.request.CreateOrderRequest.CreateOrder
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.util.log.LogUtils
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.feature.order.navigation.OrderPayRoutes
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 订单确认页面ViewModel
 */
@HiltViewModel
class OrderConfirmViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val addressRepository: AddressRepository,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository
) : BaseNetWorkViewModel<Address>(navigator, appState) {

    /**
     * 订单备注状态
     */
    private val _remark = MutableStateFlow("")
    val remark: StateFlow<String> = _remark.asStateFlow()

    /**
     * 从购物车来的商品项
     */
    val cachedCarts: List<Cart>? = MMKVUtils.getObject<List<Cart>>("carts")

    /**
     * 选中的商品
     * 从缓存中获取选中的商品列表
     */
    val selectedGoodsList: List<SelectedGoods>? =
        MMKVUtils.getObject<List<SelectedGoods>>("selectedGoodsList")

    /**
     * 购物车列表
     * 优先从缓存获取，没有则从选中的商品列表中转换
     */
    val cartList = cachedCarts ?: selectedGoodsList?.let { goods ->
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
        // 清除选中商品缓存，避免重复使用
        MMKVUtils.remove("selectedGoodsList")
        MMKVUtils.remove("carts")
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
                remark = _remark.value // 使用ViewModel中的备注
            )
        )

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.createOrder(params).asResult(),
            showToast = true,
            onData = { data ->
                // 创建订单成功
//                ToastUtils.showSuccess("订单创建成功")

                // 如果是从购物车来的，需要删除对应的购物车项
                if (cachedCarts != null && cachedCarts.isNotEmpty()) {
                    deleteCartItems()
                }

                // 跳转到支付页面
                navigateToPayment(data)
            }
        )
    }

    /**
     * 跳转到支付页面并关闭当前页面
     */
    fun navigateToPayment(order: Order) {
        val orderId = order.id
        val paymentPrice = order.price - order.discountPrice // 实付金额

        // 构建带参数的支付路由：/order/pay/{orderId}/{paymentPrice}?from=confirm
        val paymentRoute = OrderPayRoutes.ORDER_PAY_PATTERN
            .replace("{${OrderPayRoutes.ORDER_ID_ARG}}", orderId.toString())
            .replace("{${OrderPayRoutes.PRICE_ARG}}", paymentPrice.toString()) +
                // 添加来源参数，表示从确认订单页面来
                "?${OrderPayRoutes.FROM_ARG}=${OrderPayRoutes.FROM_ORDER_CONFIRM}"

        // 使用封装方法跳转到支付页面并关闭当前页面
        // 传入当前页面的路由 OrderRoutes.CONFIRM (order/confirm)
        toPageAndCloseCurrent(paymentRoute, OrderRoutes.CONFIRM)
    }

    /**
     * 删除购物车中已下单的商品
     */
    private fun deleteCartItems() {
        viewModelScope.launch {
            cachedCarts?.forEach { cart ->
                val goodsId = cart.goodsId

                // 获取该商品所有的规格ID
                val specIds = cart.spec.map { it.id }.toSet()

                // 判断是否需要删除整个商品
                val fullCart = cartRepository.getCartByGoodsId(goodsId)

                if (fullCart != null) {
                    // 所有规格是否都在订单中
                    val allSpecIds = fullCart.spec.map { it.id }.toSet()

                    if (specIds.size == allSpecIds.size && specIds.containsAll(allSpecIds)) {
                        // 删除整个商品
                        cartRepository.removeFromCart(goodsId)
                    } else {
                        // 逐个删除规格
                        specIds.forEach { specId ->
                            cartRepository.removeSpecFromCart(goodsId, specId)
                        }
                    }
                }
            }
        }
    }

    /**
     * 更新订单备注
     */
    fun updateRemark(newRemark: String) {
        _remark.value = newRemark
    }
}