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
 *
 * @param footprintRepository 足迹仓库
 * @param orderRepository 订单仓库
 * @author Joker.X
 */
@HiltViewModel
class MeViewModel @Inject constructor(
    private val appState: AppState,
    footprintRepository: FootprintRepository,
    private val orderRepository: OrderRepository
) : BaseViewModel(), DefaultLifecycleObserver {

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
     *
     * @author Joker.X
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
}
