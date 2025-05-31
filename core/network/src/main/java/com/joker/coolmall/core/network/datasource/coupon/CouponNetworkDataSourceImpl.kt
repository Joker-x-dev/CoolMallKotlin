package com.joker.coolmall.core.network.datasource.coupon

import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.CouponService
import javax.inject.Inject

/**
 * 优惠券相关数据源实现类
 * 负责处理所有与优惠券相关的网络请求
 *
 * @property couponService 优惠券服务接口，用于发起实际的网络请求
 */
class CouponNetworkDataSourceImpl @Inject constructor(
    private val couponService: CouponService
) : BaseNetworkDataSource(), CouponNetworkDataSource {

    /**
     * 领取优惠券
     *
     * @param params 请求参数，包含优惠券ID等信息
     * @return 领取结果响应数据
     */
    override suspend fun receiveCoupon(params: Any): NetworkResponse<Any> {
        return couponService.receiveCoupon(params)
    }

    /**
     * 分页查询用户优惠券
     *
     * @param params 请求参数，包含分页和查询条件
     * @return 用户优惠券分页列表响应数据
     */
    override suspend fun getUserCouponPage(params: Any): NetworkResponse<Any> {
        return couponService.getUserCouponPage(params)
    }

    /**
     * 查询用户优惠券列表
     *
     * @param params 请求参数，包含查询条件
     * @return 用户优惠券列表响应数据
     */
    override suspend fun getUserCouponList(params: Any): NetworkResponse<Any> {
        return couponService.getUserCouponList(params)
    }

    /**
     * 获取用户优惠券详情
     *
     * @param id 用户优惠券ID
     * @return 用户优惠券详情响应数据
     */
    override suspend fun getUserCouponInfo(id: String): NetworkResponse<Any> {
        return couponService.getUserCouponInfo(id)
    }

    /**
     * 分页查询优惠券信息
     *
     * @param params 请求参数，包含分页和查询条件
     * @return 优惠券信息分页列表响应数据
     */
    override suspend fun getCouponInfoPage(params: Any): NetworkResponse<Any> {
        return couponService.getCouponInfoPage(params)
    }
} 