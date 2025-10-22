package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 客服会话
 *
 * @param id ID
 * @param userId 用户ID
 * @param lastMsg 最后一条消息
 * @param adminUnreadCount 客服未读消息数
 * @param nickName 用户昵称
 * @param avatarUrl 用户头像
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
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