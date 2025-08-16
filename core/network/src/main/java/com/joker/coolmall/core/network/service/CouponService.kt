package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.request.ReceiveCouponRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 优惠券相关接口
 */
interface CouponService {

    /**
     * 领取优惠券
     */
    @POST("market/coupon/user/receive")
    suspend fun receiveCoupon(@Body request: ReceiveCouponRequest): NetworkResponse<String>

    /**
     * 分页查询用户优惠券
     */
    @POST("market/coupon/user/page")
    suspend fun getUserCouponPage(@Body params: PageRequest): NetworkResponse<NetworkPageData<Coupon>>

    /**
     * 查询用户优惠券列表
     */
    @POST("market/coupon/user/list")
    suspend fun getUserCouponList(@Body params: Any): NetworkResponse<List<Coupon>>
}