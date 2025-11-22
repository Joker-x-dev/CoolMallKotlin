package com.joker.coolmall.feature.order.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.CartRepository
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.data.repository.PageRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec
import com.joker.coolmall.core.model.entity.ConfirmOrder
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.entity.SelectedGoods
import com.joker.coolmall.core.model.request.CreateOrderRequest
import com.joker.coolmall.core.model.request.CreateOrderRequest.CreateOrder
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.navigation.routes.UserRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 订单确认页面ViewModel
 *
 * @param context 应用上下文
 * @param navigator 导航器
 * @param appState 应用状态
 * @param orderRepository 订单仓库
 * @param cartRepository 购物车仓库
 * @param pageRepository 页面仓库
 * @author Joker.X
 */
@HiltViewModel
class OrderConfirmViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    navigator: AppNavigator,
    appState: AppState,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val pageRepository: PageRepository
) : BaseNetWorkViewModel<ConfirmOrder>(navigator, appState) {

    /**
     * 订单备注状态
     */
    private val _remark = MutableStateFlow("")
    val remark: StateFlow<String> = _remark.asStateFlow()

    /**
     * 优惠券弹出层的显示状态
     */
    private val _couponModalVisible = MutableStateFlow(false)
    val couponModalVisible: StateFlow<Boolean> = _couponModalVisible.asStateFlow()

    /**
     * 选中的优惠券
     */
    private val _selectedCoupon = MutableStateFlow<Coupon?>(null)
    val selectedCoupon: StateFlow<Coupon?> = _selectedCoupon.asStateFlow()

    /**
     * 商品原价（元）
     */
    private val _originalPrice = MutableStateFlow(0.0)
    val originalPrice: StateFlow<Double> = _originalPrice.asStateFlow()

    /**
     * 优惠券折扣金额（元）
     */
    private val _discountAmount = MutableStateFlow(0.0)
    val discountAmount: StateFlow<Double> = _discountAmount.asStateFlow()

    /**
     * 最终价格（元）
     */
    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

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

        // 计算商品原价
        calculateOriginalPrice()

        // 监听选中优惠券变化，重新计算价格
        viewModelScope.launch {
            _selectedCoupon.collect { coupon ->
                calculatePrices(coupon)
            }
        }
    }

    /**
     * 请求API流
     *
     * @return 确认订单网络响应流
     * @author Joker.X
     */
    override fun requestApiFlow(): Flow<NetworkResponse<ConfirmOrder>> {
        return pageRepository.getConfirmOrder()
    }

    /**
     * 提交订单点击事件
     *
     * @author Joker.X
     */
    fun onSubmitOrderClick() {
        val addressId = super.getSuccessData().defaultAddress?.id
        if (addressId == null) {
            ToastUtils.showError(R.string.select_address)
            return
        }

        // 创建订单请求参数
        val params = CreateOrderRequest(
            data = CreateOrder(
                addressId = addressId,
                goodsList = selectedGoodsList ?: emptyList(),
                title = context.getString(R.string.purchase_goods),
                remark = _remark.value,
                couponId = _selectedCoupon.value?.id
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
     *
     * @param order 订单对象
     * @author Joker.X
     */
    fun navigateToPayment(order: Order) {
        val orderId = order.id
        val paymentPrice = order.price - order.discountPrice // 实付金额

        // 跳转到支付页面并关闭当前页面
        navigateAndCloseCurrent(
            OrderRoutes.Pay(orderId = orderId, price = paymentPrice, from = "confirm"),
            OrderRoutes.Confirm
        )
    }

    /**
     * 删除购物车中已下单的商品
     *
     * @author Joker.X
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
     *
     * @param newRemark 新的备注内容
     * @author Joker.X
     */
    fun updateRemark(newRemark: String) {
        _remark.value = newRemark
    }

    /**
     * 显示优惠券弹出层
     *
     * @author Joker.X
     */
    fun showCouponModal() {
        _couponModalVisible.value = true
    }

    /**
     * 隐藏优惠券弹出层
     *
     * @author Joker.X
     */
    fun hideCouponModal() {
        _couponModalVisible.value = false
    }

    /**
     * 选择优惠券
     *
     * @param coupon 选中的优惠券，null表示不使用优惠券
     * @author Joker.X
     */
    fun selectCoupon(coupon: Coupon?) {
        _selectedCoupon.value = coupon
        hideCouponModal()
    }

    /**
     * 计算商品原价
     *
     * @author Joker.X
     */
    private fun calculateOriginalPrice() {
        val price = cartList.sumOf { cart ->
            cart.spec.sumOf { spec ->
                spec.price.toDouble() * spec.count
            }
        }
        _originalPrice.value = price
    }

    /**
     * 计算价格（包括优惠券折扣）
     *
     * @param coupon 选中的优惠券
     * @author Joker.X
     */
    private fun calculatePrices(coupon: Coupon?) {
        val originalPriceValue = _originalPrice.value

        val discountValue = coupon?.let { c ->
            // 检查是否满足使用条件
            c.condition?.let { condition ->
                if (originalPriceValue >= condition.fullAmount) {
                    c.amount
                } else {
                    0.0
                }
            } ?: 0.0
        } ?: 0.0

        _discountAmount.value = discountValue
        _totalPrice.value = (originalPriceValue - discountValue).coerceAtLeast(0.0)
    }

    /**
     * 跳转到地址选择页面
     *
     * @author Joker.X
     */
    fun navigateToAddressSelection() {
        navigate(UserRoutes.AddressList(isSelectMode = true))
    }

    /**
     * 处理地址选择结果
     * 使用类型安全的 NavigationResultKey 接收地址选择结果
     *
     * @param address 选中的地址
     * @author Joker.X
     */
    fun onAddressSelected(address: Address) {
        val currentData = super.getSuccessData()
        val updatedData = currentData.copy(defaultAddress = address)
        super.setSuccessState(updatedData)
    }
}