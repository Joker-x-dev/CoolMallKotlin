package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 已读消息请求模型
 */
@Serializable
data class ReadMessageRequest(

    /**
     * 消息ID数组，用于批量已读
     */
    val ids: List<Long>
)