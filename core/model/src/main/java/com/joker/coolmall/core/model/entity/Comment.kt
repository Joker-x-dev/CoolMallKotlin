package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 商品评价
 *
 * @param id ID
 * @param userId 用户ID
 * @param goodsId 商品ID
 * @param orderId 订单ID
 * @param content 内容
 * @param starCount 星数
 * @param pics 图片
 * @param nickName 用户昵称
 * @param avatarUrl 用户头像
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
 */
@Serializable
data class Comment(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 用户ID
     */
    val userId: Long = 0,

    /**
     * 商品ID
     */
    val goodsId: Long = 0,

    /**
     * 订单ID
     */
    val orderId: Long = 0,

    /**
     * 内容
     */
    val content: String = "",

    /**
     * 星数
     */
    val starCount: Int = 5,

    /**
     * 图片
     */
    val pics: List<String>? = null,

    /**
     * 用户昵称
     */
    val nickName: String? = null,

    /**
     * 用户头像
     */
    val avatarUrl: String? = null,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
)