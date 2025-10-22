package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 客服连接
 *
 * @param id ID
 * @param userId 用户ID
 * @param connId 连接ID
 * @param type 类型 0-客户 1-后台
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
 */
@Serializable
data class CsConn(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 用户ID
     */
    val userId: Long = 0,

    /**
     * 连接ID
     */
    val connId: String = "",

    /**
     * 类型 0-客户 1-后台
     */
    val type: Int = 0,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
) 