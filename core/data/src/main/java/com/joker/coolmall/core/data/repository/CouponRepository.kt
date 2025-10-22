package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.request.ReceiveCouponRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.coupon.CouponNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 优惠券相关仓库
 *
 * @param couponNetworkDataSource 优惠券网络数据源
 * @author Joker.X
 */
class CouponRepository @Inject constructor(
    private val couponNetworkDataSource: CouponNetworkDataSource
) {
    /**
     * 领取优惠券
     *
     * @param request 领取优惠券请求参数
     * @return 领取结果Flow
     * @author Joker.X
     */
    fun receiveCoupon(request: ReceiveCouponRequest): Flow<NetworkResponse<String>> = flow {
        emit(couponNetworkDataSource.receiveCoupon(request))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询用户优惠券
     *
     * @param params 分页查询参数
     * @return 用户优惠券分页数据Flow
     * @author Joker.X
     */
    fun getUserCouponPage(params: PageRequest): Flow<NetworkResponse<NetworkPageData<Coupon>>> =
        flow {
            emit(couponNetworkDataSource.getUserCouponPage(params))
        }.flowOn(Dispatchers.IO)

    /**
     * 查询用户优惠券列表
     *
     * @param params 查询参数
     * @return 用户优惠券列表Flow
     * @author Joker.X
     */
    fun getUserCouponList(params: Any): Flow<NetworkResponse<List<Coupon>>> = flow {
        emit(couponNetworkDataSource.getUserCouponList(params))
    }.flowOn(Dispatchers.IO)
}
