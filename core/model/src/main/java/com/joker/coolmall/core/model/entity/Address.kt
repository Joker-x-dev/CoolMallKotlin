package com.joker.coolmall.core.model.entity

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.EncodeDefault.Mode
import kotlinx.serialization.Serializable

/**
 * 用户收货地址模型
 *
 * @param id ID
 * @param userId 用户ID
 * @param contact 联系人
 * @param phone 手机号
 * @param province 省
 * @param city 市
 * @param district 区
 * @param address 地址
 * @param isDefault 是否默认
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
 */
@Serializable
data class Address(
    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 用户ID
     */
    val userId: Long = 0,

    /**
     * 联系人
     */
    val contact: String = "",

    /**
     * 手机号
     */
    val phone: String = "",

    /**
     * 省
     */
    val province: String = "",

    /**
     * 市
     */
    val city: String = "",

    /**
     * 区
     */
    val district: String = "",

    /**
     * 地址
     */
    val address: String = "",

    /**
     * 是否默认
     */
    @EncodeDefault(Mode.ALWAYS) // 关键：无论 true/false 都序列化
    val isDefault: Boolean = false,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
) 