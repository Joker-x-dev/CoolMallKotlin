package com.joker.coolmall.core.network.datasource.banner

import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 轮播图相关数据源接口
 */
interface BannerNetworkDataSource {

    /**
     * 查询轮播图列表
     */
    suspend fun getBannerList(params: Any): NetworkResponse<Any>
} 