package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 物流信息实体类
 *
 * @param number 物流单号
 * @param type 物流公司类型
 * @param list 物流轨迹列表
 * @param deliverystatus 配送状态
 * @param issign 是否签收
 * @param expName 快递公司名称
 * @param expSite 快递公司官网
 * @param expPhone 快递公司电话
 * @param logo 快递公司logo
 * @param courier 快递员姓名
 * @param courierPhone 快递员电话
 * @param updateTime 更新时间
 * @param takeTime 耗时
 * @author Joker.X
 */
@Serializable
data class Logistics(
    /**
     * 物流单号
     */
    val number: String? = null,

    /**
     * 物流公司类型
     */
    val type: String? = null,

    /**
     * 物流轨迹列表
     */
    val list: List<LogisticsItem>? = null,

    /**
     * 配送状态
     */
    val deliverystatus: String? = null,

    /**
     * 是否签收
     */
    val issign: String? = null,

    /**
     * 快递公司名称
     */
    val expName: String? = null,

    /**
     * 快递公司官网
     */
    val expSite: String? = null,

    /**
     * 快递公司电话
     */
    val expPhone: String? = null,

    /**
     * 快递公司logo
     */
    val logo: String? = null,

    /**
     * 快递员姓名
     */
    val courier: String? = null,

    /**
     * 快递员电话
     */
    val courierPhone: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null,

    /**
     * 耗时
     */
    val takeTime: String? = null
)