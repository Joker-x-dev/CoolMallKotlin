package com.joker.coolmall.feature.user.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.FootprintRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Footprint
import com.joker.coolmall.feature.user.state.FootprintUiState
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 用户足迹ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class FootprintViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val footprintRepository: FootprintRepository
) : BaseViewModel(
    navigator = navigator,
    appState = appState
) {

    /**
     * 所有足迹记录
     */
    val footprints: StateFlow<List<Footprint>> = footprintRepository.getAllFootprints()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * 页面 UI 状态
     * 根据足迹数据自动更新状态：
     * - 初始状态为Loading
     * - 数据加载完成后，如果有数据则为Success，无数据则为Empty
     */
    val uiState: StateFlow<FootprintUiState> = footprints
        .map { footprintList ->
            when {
                footprintList.isEmpty() -> FootprintUiState.Empty
                else -> FootprintUiState.Success
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FootprintUiState.Loading
        )

    /**
     * 足迹记录总数量
     */
    val footprintCount: StateFlow<Int> = footprintRepository.getFootprintCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    /**
     * 跳转到商品详情页面
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    fun toGoodsDetail(goodsId: Long) {
        navigate(GoodsRoutes.Detail(goodsId = goodsId))
    }

    /**
     * 删除指定足迹记录
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    fun removeFootprint(goodsId: Long) {
        viewModelScope.launch {
            footprintRepository.removeFootprint(goodsId)
        }
    }

    /**
     * 清空所有足迹记录
     *
     * @author Joker.X
     */
    fun clearAllFootprints() {
        viewModelScope.launch {
            footprintRepository.clearAllFootprints()
        }
    }
}