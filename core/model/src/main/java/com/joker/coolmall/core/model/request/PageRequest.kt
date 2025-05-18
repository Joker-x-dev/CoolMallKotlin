package com.joker.coolmall.core.model.request

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable

/**
 * 通用分页请求模型
 */
@Serializable
data class PageRequest(
    /**
     * 页码
     */
    @EncodeDefault
    val page: Int = 1,

    /**
     * 每页大小
     */
    @EncodeDefault
    val size: Int = 10,

    /**
     * 排序方式："asc"升序，"desc"降序
     */
    @EncodeDefault
    val sort: String = "desc"
) 