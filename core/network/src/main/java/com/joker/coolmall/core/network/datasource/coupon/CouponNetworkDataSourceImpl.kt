package com.joker.coolmall.core.network.datasource.coupon

import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.request.ReceiveCouponRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.CouponService
import javax.inject.Inject

/**
 * 优惠券相关数据源实现类
 * 负责处理所有与优惠券相关的网络请求
 *
 * @param couponService 优惠券服务接口，用于发起实际的网络请求
 * @author Joker.X
 */
class CouponNetworkDataSourceImpl @Inject constructor(
    private val couponService: CouponService
) : BaseNetworkDataSource(), CouponNetworkDataSource {

    /**
     * 领取优惠券
     *
     * @param request 领取优惠券请求参数
     * @return 领取结果响应数据
     * @author Joker.X
     */
    override suspend fun receiveCoupon(request: ReceiveCouponRequest): NetworkResponse<String> {
        return couponService.receiveCoupon(request)
    }

    /**
     * 分页查询用户优惠券
     *
     * @param params 请求参数，包含分页和查询条件
     * @return 用户优惠券分页列表响应数据
     * @author Joker.X
     */
    override suspend fun getUserCouponPage(params: PageRequest): NetworkResponse<NetworkPageData<Coupon>> {
        return couponService.getUserCouponPage(params)
    }

    /**
     * 查询用户优惠券列表
     *
     * @param params 请求参数，包含查询条件
     * @return 用户优惠券列表响应数据
     * @author Joker.X
     */
    override suspend fun getUserCouponList(params: Any): NetworkResponse<List<Coupon>> {
        return couponService.getUserCouponList(params)
    }
}
