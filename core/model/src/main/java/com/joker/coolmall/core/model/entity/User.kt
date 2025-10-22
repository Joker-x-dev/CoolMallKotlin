package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 用户信息模型
 *
 * @param id ID
 * @param unionid 登录唯一ID
 * @param avatarUrl 头像
 * @param nickName 昵称
 * @param phone 手机号
 * @param gender 性别 0-未知 1-男 2-女
 * @param status 状态 0-禁用 1-正常 2-已注销
 * @param loginType 登录方式 0-小程序 1-公众号 2-H5
 * @param password 密码
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
 */
@Serializable
data class User(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 登录唯一ID
     */
    val unionid: String = "",

    /**
     * 头像
     */
    val avatarUrl: String? = null,

    /**
     * 昵称
     */
    val nickName: String? = null,

    /**
     * 手机号
     */
    val phone: String? = null,

    /**
     * 性别 0-未知 1-男 2-女
     */
    val gender: Int = 0,

    /**
     * 状态 0-禁用 1-正常 2-已注销
     */
    val status: Int = 1,

    /**
     * 登录方式 0-小程序 1-公众号 2-H5
     */
    val loginType: String = "0",

    /**
     * 密码
     */
    val password: String? = null,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
)
