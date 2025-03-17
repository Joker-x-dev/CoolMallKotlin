package com.joker.coolmall.core.network.datasource.goods

import com.joker.coolmall.core.model.Goods
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.base.NetworkDataSource

/**
 * 商品网络数据源接口
 */
interface GoodsNetworkDataSource : NetworkDataSource {
    
    /**
     * 获取商品列表
     */
    suspend fun getGoods(page: Int, size: Int): NetworkResponse<NetworkPageData<Goods>>
    
    /**
     * 添加/更新商品
     */
    suspend fun saveGoods(goods: Goods): NetworkResponse<Goods>
} 