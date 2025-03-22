package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.Goods
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.goods.GoodsNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 商品仓库
 */
@Singleton
class GoodsRepository @Inject constructor(
    private val goodsNetworkDataSource: GoodsNetworkDataSource
) {
    /**
     * 获取商品列表
     */
    fun getGoods(page: Int = 1, size: Int = 10): Flow<NetworkResponse<NetworkPageData<Goods>>> = flow {
        emit(goodsNetworkDataSource.getGoods(page, size))
    }.flowOn(Dispatchers.IO)
    
    /**
     * 添加/更新商品
     */
    fun saveGoods(goods: Goods): Flow<NetworkResponse<Goods>> = flow {
        emit(goodsNetworkDataSource.saveGoods(goods))
    }.flowOn(Dispatchers.IO)
} 