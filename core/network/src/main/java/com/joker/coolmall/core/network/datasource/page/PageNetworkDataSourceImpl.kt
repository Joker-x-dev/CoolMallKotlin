package com.joker.coolmall.core.network.datasource.page

import com.joker.coolmall.core.model.Home
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.PageService
import javax.inject.Inject

/**
 * 页面相关数据源实现类
 */
class PageNetworkDataSourceImpl @Inject constructor(
    private val pageService: PageService
) : BaseNetworkDataSource(), PageNetworkDataSource {

    override suspend fun getHomeData(): NetworkResponse<Home> {
        return pageService.getHomeData()
    }
}