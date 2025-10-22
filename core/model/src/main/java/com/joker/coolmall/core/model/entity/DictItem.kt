package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 字典项
 *
 * @param typeId 字典类型ID
 * @param parentId 父级ID
 * @param name 字典项名称
 * @param id 字典项ID
 * @param value 字典项值
 * @author Joker.X
 */
@Serializable
data class DictItem(
    /**
     * 字典类型ID
     */
    val typeId: Long? = 0,

    /**
     * 父级ID
     */
    val parentId: Long? = 0,

    /**
     * 字典项名称
     */
    val name: String? = "",

    /**
     * 字典项ID
     */
    val id: Long? = 0,

    /**
     * 字典项值
     */
    val value: Int? = 0
)