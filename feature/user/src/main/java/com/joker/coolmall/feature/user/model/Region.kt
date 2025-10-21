package com.joker.coolmall.feature.user.model

/**
 * 地区数据模型
 *
 * @property id 地区ID
 * @property name 地区名称
 * @property children 子地区列表
 * @author Joker.X
 */
data class Region(
    val id: String,
    val name: String,
    val children: List<Region> = emptyList()
) 