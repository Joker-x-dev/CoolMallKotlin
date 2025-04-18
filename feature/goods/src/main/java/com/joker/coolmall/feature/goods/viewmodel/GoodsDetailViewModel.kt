package com.joker.coolmall.feature.goods.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.model.Goods
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.feature.goods.navigation.GoodsDetailRoutes
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    init {
        super.executeRequest()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     */
    override fun requestApiFlow(): Flow<NetworkResponse<Goods>> {
        return goodsRepository.getGoodsInfo(requiredId.toString())
    }
}