package com.joker.coolmall.core.network.datasource.page

import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.model.entity.GoodsDetail
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 页面相关数据源接口
 */
interface PageNetworkDataSource {

    /**
     * 获取首页数据
     */
    suspend fun getHomeData(): NetworkResponse<Home>

    /**
     * 获取商品详情
     * @param goodsId 商品ID
     */
    suspend fun getGoodsDetail(goodsId: Long): NetworkResponse<GoodsDetail>
}