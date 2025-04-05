package com.joker.coolmall.feature.goods.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.result.Result
import com.joker.coolmall.core.common.result.asResult
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.util.log.LogUtils
import com.joker.coolmall.feature.goods.navigation.GoodsDetailRoutes
import com.joker.coolmall.feature.goods.state.GoodsDetailUiState
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 商品详情页面ViewModel
 */
@HiltViewModel
class GoodsDetailViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val goodsRepository: GoodsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 从SavedStateHandle获取路由参数
    private val goodsId: Long = checkNotNull(savedStateHandle[GoodsDetailRoutes.GOODS_ID_ARG])

    // 商品详情状态
    private val _uiState = MutableStateFlow<GoodsDetailUiState>(GoodsDetailUiState.Loading)
    val uiState: StateFlow<GoodsDetailUiState> = _uiState

    init {
        // 初始化时加载商品数据
        getGoodsDetail()
    }

    /**
     * 获取商品详情数据
     */
    fun getGoodsDetail() {
        viewModelScope.launch {
            goodsRepository.getGoodsInfo(goodsId.toString())
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value = GoodsDetailUiState.Loading
                        }

                        is Result.Success -> {
                            if (result.data.isSucceeded && result.data.data != null) {
                                val goodsData = result.data
                                LogUtils.d("Goods detail data: $goodsData")
                                _uiState.value = GoodsDetailUiState.Success(goodsData.data!!)
                            } else {
                                _uiState.value =
                                    GoodsDetailUiState.Error(result.data.message ?: "未知错误")
                            }
                        }

                        is Result.Error -> {
                            _uiState.value =
                                GoodsDetailUiState.Error(result.exception.message ?: "未知错误")
                        }
                    }
                }
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