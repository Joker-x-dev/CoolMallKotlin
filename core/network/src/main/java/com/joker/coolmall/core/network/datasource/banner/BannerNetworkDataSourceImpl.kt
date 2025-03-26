package com.joker.coolmall.core.network.datasource.banner

import com.joker.coolmall.core.model.Banner
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.BannerService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 轮播图网络数据源实现类
 */
@Singleton
class BannerNetworkDataSourceImpl @Inject constructor(
    private val bannerService: BannerService
) : BaseNetworkDataSource(), BannerNetworkDataSource {

    override suspend fun getBannerList(): NetworkResponse<NetworkPageData<Banner>> {
        return bannerService.getBannerList()
    }
} 