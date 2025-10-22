package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 用户贡献者实体类
 *
 * @param id ID
 * @param avatarUrl 头像
 * @param nickName 昵称
 * @param websiteUrl 网站地址(GitHub/Gitee/个人博客)
 * @param signature 个性签名/个人描述
 * @param tags 个人标签
 * @param sortNum 排序
 * @param status 状态 1:启用 2:禁用
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
 */
@Serializable
data class UserContributor(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 头像
     */
    val avatarUrl: String = "",

    /**
     * 昵称
     */
    val nickName: String = "",

    /**
     * 网站地址(GitHub/Gitee/个人博客)
     */
    val websiteUrl: String? = null,

    /**
     * 个性签名/个人描述
     */
    val signature: String? = null,

    /**
     * 个人标签
     */
    val tags: List<String>? = null,

    /**
     * 排序
     */
    val sortNum: Int = 0,

    /**
     * 状态 1:启用 2:禁用
     */
    val status: Int = 1,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
)