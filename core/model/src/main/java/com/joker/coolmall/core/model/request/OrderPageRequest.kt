package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 订单分页查询请求模型
 */
@Serializable
data class OrderPageRequest(

    /**
     * 订单状态
     */
    val status: List<String> = listOf(),

    /**
     * 分页请求参数
     */
    val pageRequest: PageRequest = PageRequest()
)
