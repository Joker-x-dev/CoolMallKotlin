package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 通用分页请求模型
 */
@Serializable
data class PageRequest(
    /**
     * 页码
     */
    val page: Int = 1,
    
    /**
     * 每页大小
     */
    val size: Int = 20,
    
    /**
     * 排序方式："asc"升序，"desc"降序
     */
    val sort: String = "desc"
) 