package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.feature.order.model.Alipay
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.RefreshResultKey
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 订单支付 ViewModel
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param savedStateHandle 保存状态句柄
 * @param orderRepository 订单仓库
 * @author Joker.X
 */
@HiltViewModel
class OrderPayViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    savedStateHandle: SavedStateHandle,
    private val orderRepository: OrderRepository
) : BaseViewModel(navigator, appState) {

    /**
     * 订单支付路由参数
     */
    private val _orderPayRoute = MutableStateFlow(savedStateHandle.toRoute<OrderRoutes.Pay>())
    val orderPayRoute: StateFlow<OrderRoutes.Pay> = _orderPayRoute.asStateFlow()

    /**
     * 支付宝支付参数
     */
    private val _alipayPayInfo = MutableStateFlow("")
    val alipayPayInfo = _alipayPayInfo.asStateFlow()

    /**
     * 发起支付宝支付
     *
     * @author Joker.X
     */
    fun startAlipayPayment() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.alipayAppPay(mapOf("orderId" to _orderPayRoute.value.orderId))
                .asResult(),
            showToast = true,
            onData = { data -> _alipayPayInfo.value = data }
        )
    }

    /**
     * 处理支付宝支付结果
     *
     * @param param 支付结果参数
     * @author Joker.X
     */
    fun processAlipayResult(param: Map<String, String>) {
        val result = Alipay(param)
        when (result.resultStatus) {
            Alipay.RESULT_STATUS_CANCEL -> {
                ToastUtils.showError(R.string.payment_cancel)
                // 支付取消后，根据来源判断是否需要跳转到订单详情
                handleBackAfterPayment(false)
            }

            Alipay.RESULT_STATUS_SUCCESS -> {
                ToastUtils.showSuccess(R.string.payment_success)
                // 支付成功，如果是从确认订单页面来，也跳转到详情页面
                handleBackAfterPayment(true)
            }

            else -> {
                ToastUtils.showError(R.string.payment_failed)
                // 支付失败后，根据来源判断是否需要跳转到订单详情
                handleBackAfterPayment(false)
            }
        }
    }

    /**
     * 处理系统返回按钮点击
     *
     * @author Joker.X
     */
    fun handleBackClick() {
        handleBackAfterPayment(false)
    }

    /**
     * 处理支付后的返回逻辑
     *
     * @param isPaySuccess 支付是否成功
     * @author Joker.X
     */
    private fun handleBackAfterPayment(isPaySuccess: Boolean) {
        // 如果来源是确认订单页面，无论支付是否成功，都跳转到订单详情页面
        if (_orderPayRoute.value.from == "confirm") {
            // 返回上一级(确认订单页面)
            navigateBack()
            // 导航到订单详情页面
            navigate(OrderRoutes.Detail(orderId = _orderPayRoute.value.orderId))
        } else {
            // 其他情况正常返回
            if (isPaySuccess) {
                // 支付成功，使用 NavigationResult 回传刷新信号
                popBackStackWithResult(RefreshResultKey, true)
            } else {
                navigateBack()
            }
        }
    }
}