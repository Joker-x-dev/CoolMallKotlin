package com.joker.coolmall.feature.goods.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.model.Goods
import com.joker.coolmall.core.model.GoodsSpec
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.feature.goods.navigation.GoodsDetailRoutes
import com.joker.coolmall.navigation.AppNavigator
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

    init {
        super.executeRequest()
//        loadGoodsSpecs()
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
    fun addToCart(spec: GoodsSpec) {
        viewModelScope.launch {
            // TODO: 实际项目中应调用Repository的加入购物车方法
            hideSpecModal()
        }
    }

    /**
     * 立即购买
     */
    fun buyNow(spec: GoodsSpec) {
        viewModelScope.launch {
            // TODO: 实际项目中应导航到订单确认页面
            hideSpecModal()
        }
    }

}