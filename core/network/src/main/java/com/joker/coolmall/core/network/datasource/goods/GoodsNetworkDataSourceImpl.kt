package com.joker.coolmall.core.network.datasource.goods

import com.joker.coolmall.core.model.Goods
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.GoodsService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 商品网络数据源实现
 */
@Singleton
class GoodsNetworkDataSourceImpl @Inject constructor(
    private val goodsService: GoodsService
) : BaseNetworkDataSource(), GoodsNetworkDataSource {

    override suspend fun getGoods(page: Int, size: Int): NetworkResponse<NetworkPageData<Goods>> {
        return goodsService.getGoods(page, size)
    }

    override suspend fun saveGoods(goods: Goods): NetworkResponse<Goods> {
        return goodsService.saveGoods(goods)
    }
} 