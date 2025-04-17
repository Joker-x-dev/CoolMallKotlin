package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.PageRepository
import com.joker.coolmall.core.model.Home
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 首页ViewModel
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    navigator: AppNavigator,
    private val pageRepository: PageRepository,
    savedStateHandle: SavedStateHandle
) : BaseNetWorkViewModel<Home>(
    navigator = navigator
) {
    init {
        super.loadData()
    }

    override fun fetchFlow(): Flow<NetworkResponse<Home>> {
        return pageRepository.getHomeData()
    }

    /**
     * 导航到商品详情页
     */
    fun toGoodsDetail(goodsId: Long) {
        super.toPge(GoodsRoutes.DETAIL, goodsId)
    }
}
