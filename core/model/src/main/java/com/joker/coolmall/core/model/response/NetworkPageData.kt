package com.joker.coolmall.core.model.response

import kotlinx.serialization.Serializable

/**
 * 网络响应分页模型
 *
 * @param T 数据类型
 * @param list 列表
 * @param pagination 分页数据
 * @author Joker.X
 */
@Serializable
data class NetworkPageData<T>(
    /**
     * 列表
     */
    var list: List<T>? = null,

    /**
     * 分页数据
     */
    var pagination: NetworkPageMeta? = null,
)