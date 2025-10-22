package com.joker.coolmall.core.network.datasource.page

import com.joker.coolmall.core.model.entity.ConfirmOrder
import com.joker.coolmall.core.model.entity.GoodsDetail
import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.PageService
import javax.inject.Inject

/**
 * 页面相关数据源实现类
 * 负责处理所有与页面相关的网络请求
 *
 * @param pageService 页面服务接口，用于发起实际的网络请求
 * @author Joker.X
 */
class PageNetworkDataSourceImpl @Inject constructor(
    private val pageService: PageService
) : BaseNetworkDataSource(), PageNetworkDataSource {

    /**
     * 获取首页数据
     *
     * @return 首页数据响应信息，包含轮播图、分类、商品推荐等
     * @author Joker.X
     */
    override suspend fun getHomeData(): NetworkResponse<Home> {
        return pageService.getHomeData()
    }

    /**
     * 获取商品详情
     *
     * @param goodsId 商品ID
     * @return 商品详情响应信息，包含商品信息、优惠券列表和评论列表
     * @author Joker.X
     */
    override suspend fun getGoodsDetail(goodsId: Long): NetworkResponse<GoodsDetail> {
        return pageService.getGoodsDetail(goodsId)
    }

    /**
     * 获取确认订单页面数据
     *
     * @return 确认订单页面数据响应信息，包含默认收货地址和用户优惠券
     * @author Joker.X
     */
    override suspend fun getConfirmOrder(): NetworkResponse<ConfirmOrder> {
        return pageService.getConfirmOrder()
    }
}
