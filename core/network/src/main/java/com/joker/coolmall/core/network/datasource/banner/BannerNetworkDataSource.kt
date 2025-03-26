package com.joker.coolmall.core.network.datasource.banner

import com.joker.coolmall.core.model.Banner
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.base.NetworkDataSource

/**
 * 轮播图网络数据源接口
 */
interface BannerNetworkDataSource : NetworkDataSource {

    /**
     * 获取商品列表
     */
    suspend fun getBannerList(): NetworkResponse<NetworkPageData<Banner>>
} 