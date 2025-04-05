package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 优惠券相关接口
 */
interface CouponService {

    /**
     * 领取优惠券
     */
    @POST("market/coupon/user/receive")
    suspend fun receiveCoupon(@Body params: Any): NetworkResponse<Any>

    /**
     * 分页查询用户优惠券
     */
    @POST("market/coupon/user/page")
    suspend fun getUserCouponPage(@Body params: Any): NetworkResponse<Any>

    /**
     * 查询用户优惠券列表
     */
    @POST("market/coupon/user/list")
    suspend fun getUserCouponList(@Body params: Any): NetworkResponse<Any>

    /**
     * 用户优惠券详情
     */
    @GET("market/coupon/user/info")
    suspend fun getUserCouponInfo(@Query("id") id: String): NetworkResponse<Any>

    /**
     * 分页查询优惠券信息
     */
    @POST("market/coupon/info/page")
    suspend fun getCouponInfoPage(@Body params: Any): NetworkResponse<Any>
} 