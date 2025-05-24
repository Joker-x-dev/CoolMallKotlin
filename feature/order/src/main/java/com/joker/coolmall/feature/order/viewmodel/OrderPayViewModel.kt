package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.order.model.Alipay
import com.joker.coolmall.feature.order.navigation.OrderPayRoutes
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 订单支付 ViewModel
 */
@HiltViewModel
class OrderPayViewModel @Inject constructor(
    navigator: AppNavigator,
    savedStateHandle: SavedStateHandle,
    private val orderRepository: OrderRepository
) : BaseViewModel(navigator) {

    /**
     * 订单ID
     */
    var orderId = 0L

    /**
     * 订单价格
     */
    private val _price = MutableStateFlow(0)
    val price = _price.asStateFlow()

    /**
     * 支付宝支付参数
     */
    private val _alipayPayInfo = MutableStateFlow("")
    val alipayPayInfo = _alipayPayInfo.asStateFlow()

    init {
        // 从路由参数中获取订单ID和价格
        orderId = savedStateHandle.get<Long>(OrderPayRoutes.ORDER_ID_ARG) ?: 0L
        _price.value = savedStateHandle.get<Int>(OrderPayRoutes.PRICE_ARG) ?: 0
    }

    /**
     * 发起支付宝支付
     */
    fun startAlipayPayment() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.alipayAppPay(mapOf("orderId" to orderId)).asResult(),
            showToast = true,
            onData = { data -> _alipayPayInfo.value = data }
        )
    }

    /**
     * 处理支付宝支付结果
     */
    fun processAlipayResult(param: Map<String, String>) {
        val result = Alipay(param)
        when (result.resultStatus) {
            Alipay.RESULT_STATUS_CANCEL -> ToastUtils.showError("支付取消")
            Alipay.RESULT_STATUS_SUCCESS -> {
                ToastUtils.showSuccess("支付成功")
            }

            else -> ToastUtils.showError("支付失败")
        }
    }
}