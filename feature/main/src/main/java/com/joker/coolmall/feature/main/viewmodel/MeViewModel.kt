package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.User
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.AuthRoutes
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.navigation.routes.UserRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 我的页面视图模型
 */
@HiltViewModel
class MeViewModel @Inject constructor(
    navigator: AppNavigator,
    private val appState: AppState
) : BaseViewModel(
    navigator = navigator
) {

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

    init {
        // 如果已登录但没有用户信息，则刷新用户信息
        viewModelScope.launch {
            if (isLoggedIn.value && userInfo.value == null) {
                appState.refreshUserInfo()
            }
        }
    }

    /**
     * 跳转到订单列表
     */
    fun toOrderListPage() {
        super.toPage(OrderRoutes.LIST)
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
     * 跳转到用户资料页面
     */
    fun toProfilePage() {
        super.toPage(UserRoutes.PROFILE)
    }

    /**
     * 跳转到登录页面
     */
    fun toLoginPage() {
        super.toPage(AuthRoutes.HOME)
    }
}
