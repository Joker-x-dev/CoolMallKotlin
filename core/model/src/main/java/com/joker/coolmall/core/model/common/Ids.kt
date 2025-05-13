package com.joker.coolmall.core.model.common

import kotlinx.serialization.Serializable

/**
 * 删除请求模型
 */
@Serializable
data class Ids(
    /**
     * 地址ID数组，用于批量删除
     */
    val ids: List<Long>
)