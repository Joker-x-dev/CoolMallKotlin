package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.FootprintRepository
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Footprint
import com.joker.coolmall.core.model.entity.OrderCount
import com.joker.coolmall.core.model.entity.User
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.CommonRoutes
import com.joker.coolmall.navigation.routes.CsRoutes
import com.joker.coolmall.navigation.routes.FeedbackRoutes
import com.joker.coolmall.navigation.routes.GoodsRoutes
import com.joker.coolmall.navigation.routes.MarketRoutes
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.navigation.routes.UserRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 我的页面视图模型
 */
@HiltViewModel
class MeViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    footprintRepository: FootprintRepository,
    private val orderRepository: OrderRepository
) : BaseViewModel(
    navigator = navigator,
    appState = appState
), DefaultLifecycleObserver {

    // 用户登录状态
    val isLoggedIn: StateFlow<Boolean> = appState.isLoggedIn
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    // 用户信息
    val userInfo: StateFlow<User?> = appState.userInfo
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // 最新的8个足迹记录
    val recentFootprints: StateFlow<List<Footprint>> = footprintRepository.getRecentFootprints(8)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 订单统计数据
    private val _orderCount = MutableStateFlow<OrderCount?>(null)
    val orderCount: StateFlow<OrderCount?> = _orderCount.asStateFlow()

    init {
        // 如果已登录但没有用户信息，则刷新用户信息
        viewModelScope.launch {
            if (isLoggedIn.value && userInfo.value == null) {
                appState.refreshUserInfo()
            }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getUserOrderStatistics()
    }

    /**
     * 用户订单统计
     */
    fun getUserOrderStatistics() {
        // 只有已经登录了才去请求订单统计数据
        if (!appState.isLoggedIn.value) return
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.getUserOrderCount().asResult(),
            onData = { data -> _orderCount.value = data }
        )
    }

    /**
     * 跳转到订单列表
     */
    fun toOrderListPage() {
        super.toPage(OrderRoutes.LIST)
    }

    /**
     * 跳转到指定状态的订单列表
     *
     * @param tabIndex 订单标签索引：0-全部，1-待付款，2-待发货，3-待收货，4-售后，5-待评价，6-已完成
     */
    fun toOrderListPage(tabIndex: Int) {
        val route = "${OrderRoutes.LIST}?tab=$tabIndex"
        super.toPage(route)
    }

    /**
     * 用户资料点击
     */
    fun onHeadClick() {
        toProfilePage()
    }

    /**
     * 跳转到商品详情
     */
    fun toGoodsDetailPage(goodsId: Long) {
        super.toPage(GoodsRoutes.DETAIL, goodsId)
    }

    /**
     * 跳转到用户足迹
     */
    fun toUserFootprintPage() {
        super.toPage(UserRoutes.FOOTPRINT)
    }

    /**
     * 跳转到收货地址列表
     */
    fun toAddressListPage() {
        super.toPage(UserRoutes.ADDRESS_LIST)
    }

    /**
     * 跳转到我的优惠券
     */
    fun toCouponPage() {
        super.toPage(MarketRoutes.COUPON)
    }

    /**
     * 跳转到用户资料页面
     */
    fun toProfilePage() {
        val avatarUrl = userInfo.value?.avatarUrl
        val route = if (avatarUrl?.isNotEmpty() == true) {
            "${UserRoutes.PROFILE}?avatar_url=${java.net.URLEncoder.encode(avatarUrl, "UTF-8")}"
        } else {
            UserRoutes.PROFILE
        }
        super.toPage(route)
    }

    /**
     * 跳转到客服聊天页面
     */
    fun toChatPage() {
        super.toPage(CsRoutes.CHAT)
    }

    /**
     * 跳转到意见反馈页面
     */
    fun toFeedbackPage() {
        super.toPage(FeedbackRoutes.LIST)
    }

    /**
     * 跳转到关于我们页面
     */
    fun toAboutPage() {
        super.toPage(CommonRoutes.ABOUT)
    }

    /**
     * 跳转到设置页面
     */
    fun toSettingsPage() {
        super.toPage(CommonRoutes.SETTINGS)
    }
}
