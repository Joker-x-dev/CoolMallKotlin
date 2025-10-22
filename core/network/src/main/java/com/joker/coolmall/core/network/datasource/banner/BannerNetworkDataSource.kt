package com.joker.coolmall.core.network.datasource.banner

import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 轮播图相关数据源接口
 *
 * @author Joker.X
 */
interface BannerNetworkDataSource {

    /**
     * 查询轮播图列表
     *
     * @param params 请求参数
     * @return 轮播图列表响应
     * @author Joker.X
     */
    suspend fun getBannerList(params: Any): NetworkResponse<Any>
} 