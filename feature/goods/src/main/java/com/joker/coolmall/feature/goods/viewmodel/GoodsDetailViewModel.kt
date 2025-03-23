package com.joker.coolmall.feature.goods.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.feature.goods.navigation.GoodsDetailRoutes
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val navigator: AppNavigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 从SavedStateHandle获取路由参数
    private val goodsId: String = checkNotNull(savedStateHandle[GoodsDetailRoutes.GOODS_ID_ARG])

    // 商品详情状态
    private val _uiState = MutableStateFlow(GoodsDetailUiState())
    val uiState: StateFlow<GoodsDetailUiState> = _uiState.asStateFlow()

    init {
        // 初始化时加载商品数据
        loadGoodsDetails()
    }

    /**
     * 加载商品详情
     */
    private fun loadGoodsDetails() {
        // 更新UI状态中的商品ID
        _uiState.value = GoodsDetailUiState(
            isLoading = true,
            goodsId = goodsId
        )

        // 这里应该有获取商品详情的网络请求
        // 模拟网络请求
        viewModelScope.launch {
            // 模拟延迟
            delay(500)

            // 更新UI状态
            _uiState.value = GoodsDetailUiState(
                isLoading = false,
                goodsId = goodsId,
                goodsName = "商品 $goodsId",
                goodsPrice = "¥99.99"
                // 可以添加更多商品信息
            )
        }
    }

    /**
     * 返回上一页
     */
    fun navigateBack() {
        viewModelScope.launch {
            navigator.navigateBack()
        }
    }
}

/**
 * 商品详情页面UI状态
 */
data class GoodsDetailUiState(
    val isLoading: Boolean = false,
    val goodsId: String = "",
    val goodsName: String = "",
    val goodsPrice: String = "",
    val error: String? = null
)