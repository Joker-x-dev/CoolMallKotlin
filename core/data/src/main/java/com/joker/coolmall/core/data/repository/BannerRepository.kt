package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.Banner
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.banner.BannerNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BannerRepository @Inject constructor(
    private val bannerNetworkDataSource: BannerNetworkDataSource
) {
    /**
     * 获取商品列表
     */
    fun getBannerList(): Flow<NetworkResponse<NetworkPageData<Banner>>> = flow {
        emit(bannerNetworkDataSource.getBannerList())
    }.flowOn(Dispatchers.IO)
}