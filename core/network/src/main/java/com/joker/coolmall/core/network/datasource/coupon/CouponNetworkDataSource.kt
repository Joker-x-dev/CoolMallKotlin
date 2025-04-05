package com.joker.coolmall.core.network.datasource.coupon

import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 优惠券相关数据源接口
 */
interface CouponNetworkDataSource {

    /**
     * 领取优惠券
     */
    suspend fun receiveCoupon(params: Any): NetworkResponse<Any>

    /**
     * 分页查询用户优惠券
     */
    suspend fun getUserCouponPage(params: Any): NetworkResponse<Any>

    /**
     * 查询用户优惠券列表
     */
    suspend fun getUserCouponList(params: Any): NetworkResponse<Any>

    /**
     * 用户优惠券详情
     */
    suspend fun getUserCouponInfo(id: String): NetworkResponse<Any>

    /**
     * 分页查询优惠券信息
     */
    suspend fun getCouponInfoPage(params: Any): NetworkResponse<Any>
} 