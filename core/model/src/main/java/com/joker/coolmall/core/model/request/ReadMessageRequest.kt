package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 已读消息请求模型
 *
 * @param ids 消息ID数组，用于批量已读
 * @author Joker.X
 */
@Serializable
data class ReadMessageRequest(

    /**
     * 消息ID数组，用于批量已读
     */
    val ids: List<Long>
)