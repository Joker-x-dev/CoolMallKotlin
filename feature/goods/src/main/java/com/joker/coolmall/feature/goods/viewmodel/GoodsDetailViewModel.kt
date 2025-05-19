package com.joker.coolmall.feature.goods.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.CartRepository
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.GoodsSpec
import com.joker.coolmall.core.model.entity.SelectedGoods
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.goods.navigation.GoodsDetailRoutes
import com.joker.coolmall.navigation.AppNavigator
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
    savedStateHandle: SavedStateHandle,
    private val goodsRepository: GoodsRepository,
    private val cartRepository: CartRepository
) : BaseNetWorkViewModel<Goods>(
    navigator = navigator,
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
     * 加入购物车结果状态
     */
    private val _addToCartState = MutableStateFlow<AddToCartState>(AddToCartState.Idle)
    val addToCartState: StateFlow<AddToCartState> = _addToCartState.asStateFlow()

    init {
        super.executeRequest()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     */
    override fun requestApiFlow(): Flow<NetworkResponse<Goods>> {
        return goodsRepository.getGoodsInfo(requiredId.toString())
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
     * 加入购物车
     */
    fun addToCart(selectedGoods: SelectedGoods) {
        viewModelScope.launch {
            try {
                _addToCartState.value = AddToCartState.Loading
                Log.d("GoodsDetailViewModel", "开始添加商品到购物车...")
                Log.d("GoodsDetailViewModel", "商品信息: ID=${selectedGoods.goodsId}, 规格ID=${selectedGoods.spec?.id}, 数量=${selectedGoods.count}")
                
                // 检查参数合法性
                if (selectedGoods.goodsId <= 0 || selectedGoods.spec == null || selectedGoods.count <= 0) {
                    val errorMsg = "请选择商品规格和数量"
                    Log.e("GoodsDetailViewModel", "添加购物车失败: $errorMsg")
                    _addToCartState.value = AddToCartState.Error(errorMsg)
                    return@launch
                }
                
                // 获取商品当前数据，构建Cart对象
                val goodsInfo = super.getSuccessData()
                Log.d("GoodsDetailViewModel", "商品详情: 标题=${goodsInfo.title}, 主图=${goodsInfo.mainPic}")

                // 1. 检查购物车中是否已有该商品
                val existingCart = cartRepository.getCartByGoodsId(selectedGoods.goodsId)
                
                if (existingCart != null) {
                    Log.d("GoodsDetailViewModel", "购物车中已有该商品")
                    // 购物车中已有该商品，检查是否有相同规格
                    val existingSpec = existingCart.spec.find { it.id == selectedGoods.spec?.id }
                    
                    if (existingSpec != null) {
                        Log.d("GoodsDetailViewModel", "更新已有规格数量: ${existingSpec.count} -> ${existingSpec.count + selectedGoods.count}")
                        // 更新规格数量
                        cartRepository.updateCartSpecCount(
                            goodsId = selectedGoods.goodsId,
                            specId = existingSpec.id,
                            count = existingSpec.count + selectedGoods.count
                        )
                    } else {
                        Log.d("GoodsDetailViewModel", "添加新规格到已有商品")
                        // 添加新规格
                        val updatedSpecs = existingCart.spec.toMutableList().apply {
                            add(selectedGoods.spec!!.toCartGoodsSpec(selectedGoods.count))
                        }
                        
                        existingCart.spec = updatedSpecs
                        cartRepository.updateCart(existingCart)
                    }
                } else {
                    Log.d("GoodsDetailViewModel", "创建新的购物车项")
                    // 购物车中没有该商品，创建新的购物车项
                    val cart = Cart().apply {
                        goodsId = selectedGoods.goodsId
                        goodsName = goodsInfo.title
                        goodsMainPic = goodsInfo.mainPic
                        spec = listOf(selectedGoods.spec!!.toCartGoodsSpec(selectedGoods.count))
                    }
                    
                    cartRepository.addToCart(cart)
                }
                
                Log.d("GoodsDetailViewModel", "添加购物车成功")
                _addToCartState.value = AddToCartState.Success
                hideSpecModal()
                ToastUtils.showSuccess("添加购物车成功")
            } catch (e: Exception) {
                val errorMsg = e.message ?: "添加购物车失败"
                Log.e("GoodsDetailViewModel", "添加购物车失败: $errorMsg")
                _addToCartState.value = AddToCartState.Error(errorMsg)
            }
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
    
    /**
     * 加入购物车状态
     */
    sealed class AddToCartState {
        object Idle : AddToCartState()
        object Loading : AddToCartState()
        object Success : AddToCartState()
        data class Error(val message: String) : AddToCartState()
    }
}