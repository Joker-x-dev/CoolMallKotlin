package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.ConfirmOrder
import com.joker.coolmall.core.model.entity.GoodsDetail
import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.page.PageNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 页面相关仓库
 *
 * @param pageNetworkDataSource 页面网络数据源
 * @author Joker.X
 */
class PageRepository @Inject constructor(
    private val pageNetworkDataSource: PageNetworkDataSource
) {
    /**
     * 获取首页数据
     *
     * @return 首页数据Flow
     * @author Joker.X
     */
    fun getHomeData(): Flow<NetworkResponse<Home>> = flow {
        emit(pageNetworkDataSource.getHomeData())
    }.flowOn(Dispatchers.IO)

    /**
     * 获取商品详情
     *
     * @param goodsId 商品ID
     * @return 商品详情Flow，包含商品信息、优惠券列表和评论列表
     * @author Joker.X
     */
    fun getGoodsDetail(goodsId: Long): Flow<NetworkResponse<GoodsDetail>> = flow {
        emit(pageNetworkDataSource.getGoodsDetail(goodsId))
    }.flowOn(Dispatchers.IO)

    /**
     * 获取确认订单页面数据
     *
     * @return 确认订单页面数据Flow，包含默认收货地址和用户优惠券
     * @author Joker.X
     */
    fun getConfirmOrder(): Flow<NetworkResponse<ConfirmOrder>> = flow {
        emit(pageNetworkDataSource.getConfirmOrder())
    }.flowOn(Dispatchers.IO)
}
