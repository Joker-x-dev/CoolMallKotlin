package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.coupon.CouponNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 优惠券相关仓库
 */
class CouponRepository @Inject constructor(
    private val couponNetworkDataSource: CouponNetworkDataSource
) {
    /**
     * 领取优惠券
     */
    fun receiveCoupon(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(couponNetworkDataSource.receiveCoupon(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询用户优惠券
     */
    fun getUserCouponPage(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(couponNetworkDataSource.getUserCouponPage(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 查询用户优惠券列表
     */
    fun getUserCouponList(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(couponNetworkDataSource.getUserCouponList(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 用户优惠券详情
     */
    fun getUserCouponInfo(id: String): Flow<NetworkResponse<Any>> = flow {
        emit(couponNetworkDataSource.getUserCouponInfo(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询优惠券信息
     */
    fun getCouponInfoPage(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(couponNetworkDataSource.getCouponInfoPage(params))
    }.flowOn(Dispatchers.IO)
} 