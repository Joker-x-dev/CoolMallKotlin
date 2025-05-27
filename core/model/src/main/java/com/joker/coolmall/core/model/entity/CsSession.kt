package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 客服会话
 */
@Serializable
data class CsSession(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 用户ID
     */
    val userId: Long = 0,

    /**
     * 最后一条消息
     */
    val lastMsg: CsMsg? = null,

    /**
     * 客服未读消息数
     */
    val adminUnreadCount: Long = 0,

    /**
     * 用户昵称
     */
    val nickName: String = "",

    /**
     * 用户头像
     */
    val avatarUrl: String = "",

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
) 