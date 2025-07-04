package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 客服消息
 */
@Serializable
data class CsMsg(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 用户ID
     */
    val userId: Long = 0,

    /**
     * 会话ID
     */
    val sessionId: Long = 0,

    /**
     * 消息内容
     */
    val content: MessageContent = MessageContent(),

    /**
     * 类型 0-反馈 1-回复
     */
    val type: Int = 0,

    /**
     * 状态 0-未读 1-已读
     */
    val status: Int = 0,

    /**
     * 用户昵称
     */
    val nickName: String = "",

    /**
     * 用户头像
     */
    val avatarUrl: String = "",

    /**
     * 管理员用户名
     */
    val adminUserName: String = "",

    /**
     * 管理员头像
     */
    val adminUserHeadImg: String = "",

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
) {

    /**
     * 消息内容
     */
    @Serializable
    data class MessageContent(
        /**
         * 消息类型:
         * TEXT - 文本
         * IMAGE - 图片
         * VOICE - 语音
         * VIDEO - 视频
         * FILE - 文件
         * LINK - 链接
         * LOCATION - 位置
         * EMOJI - 表情
         */
        val type: String = "",

        /**
         * 消息数据
         */
        val data: String = ""
    )
} 