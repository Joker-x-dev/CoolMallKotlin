package com.joker.coolmall.core.network.datasource.coupon

import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.request.ReceiveCouponRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 优惠券相关数据源接口
 *
 * @author Joker.X
 */
interface CouponNetworkDataSource {

    /**
     * 领取优惠券
     *
     * @param request 领取优惠券请求参数
     * @return 领取结果响应
     * @author Joker.X
     */
    suspend fun receiveCoupon(request: ReceiveCouponRequest): NetworkResponse<String>

    /**
     * 分页查询用户优惠券
     *
     * @param params 分页请求参数
     * @return 用户优惠券分页数据响应
     * @author Joker.X
     */
    suspend fun getUserCouponPage(params: PageRequest): NetworkResponse<NetworkPageData<Coupon>>

    /**
     * 查询用户优惠券列表
     *
     * @param params 查询参数
     * @return 用户优惠券列表响应
     * @author Joker.X
     */
    suspend fun getUserCouponList(params: Any): NetworkResponse<List<Coupon>>
}
