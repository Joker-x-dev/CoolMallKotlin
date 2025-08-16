package com.joker.coolmall.feature.market.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.CouponRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 我的优惠券 ViewModel
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
     */
    override fun requestListData(): Flow<NetworkResponse<NetworkPageData<Coupon>>> {
        return couponRepository.getUserCouponPage(
            PageRequest(
                page = super.currentPage,
                size = super.pageSize
            )
        )
    }
}