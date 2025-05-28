package com.joker.coolmall.core.model.request

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable

/**
 * 分页查询消息请求
 */
@Serializable
data class MessagePageRequest(

    /**
     * 会话ID
     */
    val sessionId: Long = 0,

    /**
     * 页码
     */
    @EncodeDefault
    val page: Int = 1,

    /**
     * 每页大小
     */
    @EncodeDefault
    val size: Int = 20,

    /**
     * 排序字段
     */
    @EncodeDefault
    val order: String = "createTime",

    /**
     * 排序方式："asc"升序，"desc"降序
     */
    @EncodeDefault
    val sort: String = "desc"
)
