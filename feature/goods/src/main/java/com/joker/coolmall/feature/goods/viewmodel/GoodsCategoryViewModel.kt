package com.joker.coolmall.feature.goods.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.request.GoodsSearchRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 商品分类 ViewModel
 */
@HiltViewModel
class GoodsCategoryViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val goodsRepository: GoodsRepository
) : BaseNetWorkListViewModel<Goods>(navigator, appState) {

    init {
        initLoad()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     */
    override fun requestListData(): Flow<NetworkResponse<NetworkPageData<Goods>>> {
        return goodsRepository.getGoodsPage(
            GoodsSearchRequest(
                page = super.currentPage,
                size = super.pageSize
            )
        )
    }
}