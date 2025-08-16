package com.joker.coolmall.core.network.datasource.coupon

import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.request.ReceiveCouponRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 优惠券相关数据源接口
 */
interface CouponNetworkDataSource {

    /**
     * 领取优惠券
     */
    suspend fun receiveCoupon(request: ReceiveCouponRequest): NetworkResponse<String>

    /**
     * 分页查询用户优惠券
     */
    suspend fun getUserCouponPage(params: PageRequest): NetworkResponse<NetworkPageData<Coupon>>

    /**
     * 查询用户优惠券列表
     */
    suspend fun getUserCouponList(params: Any): NetworkResponse<List<Coupon>>
}