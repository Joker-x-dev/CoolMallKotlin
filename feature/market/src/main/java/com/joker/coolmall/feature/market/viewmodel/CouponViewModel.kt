package com.joker.coolmall.feature.market.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.CouponRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 我的优惠券 ViewModel
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param couponRepository 优惠券仓库
 * @author Joker.X
 */
@HiltViewModel
class CouponViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val couponRepository: CouponRepository,
) : BaseNetWorkListViewModel<Coupon>(navigator, appState) {

    init {
        initLoad()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     *
     * @return 优惠券分页数据流
     * @author Joker.X
     */
    override fun requestListData(): Flow<NetworkResponse<NetworkPageData<Coupon>>> {
        return couponRepository.getUserCouponPage(
            PageRequest(
                page = super.currentPage,
                size = super.pageSize
            )
        )
    }

    /**
     * 跳转到商品分类页面使用优惠券
     *
     * @param coupon 优惠券信息
     * @author Joker.X
     */
    fun navigateToGoodsCategory(coupon: Coupon) {
        // 获取满减金额作为最小金额
        val minPrice = coupon.condition?.fullAmount?.toString() ?: ""
        navigate(
            GoodsRoutes.Category(
                minPrice = minPrice
            )
        )
    }
}
