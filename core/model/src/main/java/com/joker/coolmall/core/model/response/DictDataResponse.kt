/**
 * 字典数据响应模型
 *
 * @author Joker.X
 */
package com.joker.coolmall.core.model.response

import com.joker.coolmall.core.model.entity.DictItem
import kotlinx.serialization.Serializable

/**
 * 字典数据响应
 */
@Serializable
data class DictDataResponse(
    /**
     * 订单取消原因字典
     */
    val orderCancelReason: List<DictItem>? = null,

    /**
     * 订单退款原因字典
     */
    val orderRefundReason: List<DictItem>? = null
)