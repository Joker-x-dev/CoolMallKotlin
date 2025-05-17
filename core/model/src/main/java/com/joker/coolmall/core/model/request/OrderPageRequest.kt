package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 订单分页查询请求模型
 */
@Serializable
data class OrderPageRequest(

    /**
     * 页码
     */
    var page: Int = 1,

    /**
     * 每页大小
     */
    var size: Int = 20,

    /**
     * 订单状态列表
     */
    var status: List<Int>? = null
)
