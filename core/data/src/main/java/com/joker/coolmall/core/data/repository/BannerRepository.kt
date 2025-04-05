package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.banner.BannerNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 轮播图相关仓库
 */
class BannerRepository @Inject constructor(
    private val bannerNetworkDataSource: BannerNetworkDataSource
) {
    /**
     * 查询轮播图列表
     */
    fun getBannerList(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(bannerNetworkDataSource.getBannerList(params))
    }.flowOn(Dispatchers.IO)
} 