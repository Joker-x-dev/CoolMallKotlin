package com.joker.coolmall.feature.goods.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.CartRepository
import com.joker.coolmall.core.data.repository.CouponRepository
import com.joker.coolmall.core.data.repository.FootprintRepository
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.data.repository.PageRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.entity.Footprint
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.GoodsDetail
import com.joker.coolmall.core.model.entity.GoodsSpec
import com.joker.coolmall.core.model.entity.SelectedGoods
import com.joker.coolmall.core.model.request.ReceiveCouponRequest
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.goods.navigation.GoodsDetailRoutes
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.AuthRoutes
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 商品详情页面ViewModel
 */
@HiltViewModel
class GoodsDetailViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    savedStateHandle: SavedStateHandle,
    private val goodsRepository: GoodsRepository,
    private val cartRepository: CartRepository,
    private val footprintRepository: FootprintRepository,
    private val pageRepository: PageRepository,
    private val couponRepository: CouponRepository,
) : BaseNetWorkViewModel<GoodsDetail>(
    navigator = navigator,
    appState = appState,
    savedStateHandle = savedStateHandle,
    idKey = GoodsDetailRoutes.GOODS_ID_ARG
) {

    /**
     * 规格选择弹窗的显示状态
     */
    private val _specModalVisible = MutableStateFlow(false)
    val specModalVisible: StateFlow<Boolean> = _specModalVisible.asStateFlow()

    /**
     * 规格弹出层 ui 状态
     */
    private val _specsModalUiState =
        MutableStateFlow<BaseNetWorkUiState<List<GoodsSpec>>>(BaseNetWorkUiState.Loading)
    val specsModalUiState: StateFlow<BaseNetWorkUiState<List<GoodsSpec>>> =
        _specsModalUiState.asStateFlow()

    /**
     * 选中的规格
     */
    private val _selectedSpec = MutableStateFlow<GoodsSpec?>(null)
    val selectedSpec: StateFlow<GoodsSpec?> = _selectedSpec.asStateFlow()

    /**
     * 动画状态管理 - 确保动画只播放一次
     */
    private val _hasAnimated = MutableStateFlow(false)
    val hasAnimated: StateFlow<Boolean> = _hasAnimated.asStateFlow()

    /**
     * 优惠券弹窗的显示状态
     */
    private val _couponModalVisible = MutableStateFlow(false)
    val couponModalVisible: StateFlow<Boolean> = _couponModalVisible.asStateFlow()

    /**
     * 启用最少加载时间
     */
    override val enableMinLoadingTime: Boolean = true

    init {
        super.executeRequest()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     */
    override fun requestApiFlow(): Flow<NetworkResponse<GoodsDetail>> {
        return pageRepository.getGoodsDetail(requiredId)
    }

    /**
     * 处理成功结果，重写此方法添加足迹记录
     */
    override fun onRequestSuccess(data: GoodsDetail) {
        super.onRequestSuccess(data)
        // 添加足迹记录
        addToFootprint(data.goodsInfo)
    }

    /**
     * 添加商品到足迹
     */
    private fun addToFootprint(goods: Goods) {
        viewModelScope.launch {
            try {
                val footprint = Footprint(
                    goodsId = goods.id,
                    goodsName = goods.title,
                    goodsSubTitle = goods.subTitle,
                    goodsMainPic = goods.mainPic,
                    goodsPrice = goods.price,
                    goodsSold = goods.sold,
                    viewTime = System.currentTimeMillis()
                )
                footprintRepository.addFootprint(footprint)
            } catch (e: Exception) {
                // 足迹添加失败不影响主要功能，只记录错误
                e.printStackTrace()
            }
        }
    }

    /**
     * 加载商品规格
     */
    fun loadGoodsSpecs() {
        // 如果 ui 状态为成功，则不重复加载
        if (_specsModalUiState.value is BaseNetWorkUiState.Success) {
            return
        }
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = goodsRepository.getGoodsSpecList(
                mapOf("goodsId" to super.requiredId)
            ).asResult(),
            showToast = false,
            onLoading = { _specsModalUiState.value = BaseNetWorkUiState.Loading },
            onData = { data ->
                _specsModalUiState.value = BaseNetWorkUiState.Success(data)
            },
            onError = { _, _ ->
                _specsModalUiState.value = BaseNetWorkUiState.Error()
            }
        )
    }

    /**
     * 选择规格
     */
    fun selectSpec(spec: GoodsSpec) {
        // 如果当前已选择的规格与传入的规格相同，则表示取消选择
        if (_selectedSpec.value?.id == spec.id) {
            _selectedSpec.value = null
        } else {
            _selectedSpec.value = spec
        }
    }

    /**
     * 显示规格选择弹窗
     */
    fun showSpecModal() {
        _specModalVisible.value = true
        viewModelScope.launch {
            // 延迟加载商品规格，避免阻塞UI线程
            delay(300)
            // 加载商品规格
            loadGoodsSpecs()
        }
    }

    /**
     * 隐藏规格选择弹窗
     */
    fun hideSpecModal() {
        _specModalVisible.value = false
    }

    /**
     * 触发动画
     */
    fun triggerAnimation() {
        if (!_hasAnimated.value) {
            _hasAnimated.value = true
        }
    }

    /**
     * 显示优惠券弹出层
     */
    fun showCouponModal() {
        _couponModalVisible.value = true
    }

    /**
     * 隐藏优惠券领取弹出层
     */
    fun hideCouponModal() {
        _couponModalVisible.value = false
    }

    /**
     * 领取优惠券
     * @param coupon 要领取的优惠券
     */
    fun receiveCoupon(coupon: Coupon) {
        // 检查登录状态
        if (!appState.isLoggedIn.value) {
            hideCouponModal()
            // 未登录，跳转到登录页面
            super.toPage(AuthRoutes.HOME)
            return
        }

        val request = ReceiveCouponRequest(couponId = coupon.id)
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = couponRepository.receiveCoupon(request).asResult(),
            showToast = true,
            onData = { data -> ToastUtils.showSuccess(data) },
        )
    }

    /**
     * 加入购物车
     */
    fun addToCart(selectedGoods: SelectedGoods) {
        viewModelScope.launch {
            // 检查参数合法性
            if (selectedGoods.goodsId <= 0 || selectedGoods.spec == null || selectedGoods.count <= 0) {
                ToastUtils.showError("添加购物车失败")
                return@launch
            }

            // 获取商品当前数据，构建Cart对象
            val goodsInfo = super.getSuccessData().goodsInfo

            // 1. 检查购物车中是否已有该商品
            val existingCart = cartRepository.getCartByGoodsId(selectedGoods.goodsId)

            if (existingCart != null) {
                // 购物车中已有该商品，检查是否有相同规格
                val existingSpec = existingCart.spec.find { it.id == selectedGoods.spec?.id }

                if (existingSpec != null) {
                    // 更新规格数量
                    cartRepository.updateCartSpecCount(
                        goodsId = selectedGoods.goodsId,
                        specId = existingSpec.id,
                        count = existingSpec.count + selectedGoods.count
                    )
                } else {
                    // 添加新规格
                    val updatedSpecs = existingCart.spec.toMutableList().apply {
                        add(selectedGoods.spec!!.toCartGoodsSpec(selectedGoods.count))
                    }

                    existingCart.spec = updatedSpecs
                    cartRepository.updateCart(existingCart)
                }
            } else {
                // 购物车中没有该商品，创建新的购物车项
                val cart = Cart().apply {
                    goodsId = selectedGoods.goodsId
                    goodsName = goodsInfo.title
                    goodsMainPic = goodsInfo.mainPic
                    spec = listOf(selectedGoods.spec!!.toCartGoodsSpec(selectedGoods.count))
                }

                cartRepository.addToCart(cart)
            }

            hideSpecModal()
            ToastUtils.showSuccess("添加购物车成功")
        }
    }

    /**
     * 立即购买
     */
    fun buyNow(selectedGoods: SelectedGoods) {
        viewModelScope.launch {
            MMKVUtils.putObject("selectedGoodsList", listOf(selectedGoods))
            // 隐藏规格选择弹窗
            hideSpecModal()
            super.toPage(OrderRoutes.CONFIRM)
        }
    }

    /**
     * 将商品规格转换为购物车商品规格
     */
    private fun GoodsSpec.toCartGoodsSpec(count: Int): CartGoodsSpec {
        return CartGoodsSpec(
            id = this.id,
            goodsId = this.goodsId,
            name = this.name,
            price = this.price,
            stock = this.stock,
            count = count,
            images = this.images
        )
    }
}