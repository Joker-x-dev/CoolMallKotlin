package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 订单信息
 *
 * @param id ID
 * @param userId 用户ID
 * @param title 标题
 * @param payType 支付方式 0-待支付 1-微信 2-支付宝
 * @param payTime 支付时间
 * @param orderNum 订单号
 * @param status 状态 0-待付款 1-待发货 2-待收货 3-待评价 4-交易完成 5-退款中 6-已退款 7-已关闭
 * @param price 价格
 * @param discountPrice 优惠金额
 * @param realPrice 实际支付金额
 * @param discountSource 优惠来源
 * @param address 地址
 * @param logistics 物流信息
 * @param refund 退款
 * @param refundStatus 退款状态
 * @param refundApplyTime 退款申请时间
 * @param remark 备注
 * @param closeRemark 关闭备注
 * @param invoice 已开票 0-未开票 1-已开票
 * @param wxType 微信类型 0-小程序 1-公众号 2-App
 * @param goodsList 订单商品列表
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
 */
@Serializable
data class Order(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 用户ID
     */
    val userId: Long = 0,

    /**
     * 标题
     */
    val title: String? = null,

    /**
     * 支付方式 0-待支付 1-微信 2-支付宝
     */
    val payType: Int = 0,

    /**
     * 支付时间
     */
    val payTime: String? = null,

    /**
     * 订单号
     */
    val orderNum: String = "",

    /**
     * 状态 0-待付款 1-待发货 2-待收货 3-待评价 4-交易完成 5-退款中 6-已退款 7-已关闭
     */
    val status: Int = 0,

    /**
     * 价格
     */
    val price: Int = 0,

    /**
     * 优惠金额
     */
    val discountPrice: Int = 0,

    /**
     * 实际支付金额
     */
    val realPrice: Int = 0,

    /**
     * 优惠来源
     */
    val discountSource: DiscountSource? = null,

    /**
     * 地址
     */
    val address: Address? = null,

    /**
     * 物流信息
     */
    val logistics: Logistics? = null,

    /**
     * 退款
     */
    val refund: Refund? = null,

    /**
     * 退款状态
     */
    val refundStatus: Int? = null,

    /**
     * 退款申请时间
     */
    val refundApplyTime: String? = null,

    /**
     * 备注
     */
    val remark: String? = null,

    /**
     * 关闭备注
     */
    val closeRemark: String? = null,

    /**
     * 已开票: 0-未开票 1-已开票
     */
    val invoice: Int = 0,

    /**
     * 微信类型 0-小程序 1-公众号 2-App
     */
    val wxType: Int = 0,

    /**
     * 订单商品列表
     */
    val goodsList: List<OrderGoods>? = null,

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
     * 优惠来源
     *
     * @param type 类型 0-优惠券
     * @param objectId 对象ID
     * @param info 优惠信息
     * @author Joker.X
     */
    @Serializable
    data class DiscountSource(
        /**
         * 类型 0-优惠券
         */
        val type: Int? = null,

        /**
         * 对象ID
         */
        val objectId: Long? = null,

        /**
         * 优惠信息
         */
        val info: DiscountInfo? = null
    )

    /**
     * 优惠信息详情
     *
     * @param id ID
     * @param num 数量
     * @param type 类型
     * @param title 标题
     * @param description 描述
     * @param amount 金额
     * @param minAmount 最低消费
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @author Joker.X
     */
    @Serializable
    data class DiscountInfo(
        /**
         * ID
         */
        val id: Long? = null,

        /**
         * 数量
         */
        val num: Int? = null,

        /**
         * 类型
         */
        val type: Int? = null,

        /**
         * 标题
         */
        val title: String? = null,

        /**
         * 描述
         */
        val description: String? = null,

        /**
         * 金额
         */
        val amount: Double? = null,

        /**
         * 最低消费
         */
        val minAmount: Double? = null,

        /**
         * 开始时间
         */
        val startTime: String? = null,

        /**
         * 结束时间
         */
        val endTime: String? = null
    )

    /**
     * 物流信息
     *
     * @param company 物流公司
     * @param num 物流单号
     * @author Joker.X
     */
    @Serializable
    data class Logistics(
        /**
         * 物流公司
         */
        val company: String? = null,

        /**
         * 物流单号
         */
        val num: String? = null
    )

    /**
     * 退款信息
     *
     * @param orderNum 退款单号
     * @param amount 金额
     * @param realAmount 实际退款金额
     * @param status 状态 0-申请中 1-已退款 2-拒绝
     * @param applyTime 申请时间
     * @param time 退款时间
     * @param reason 退款原因
     * @param refuseReason 拒绝原因
     * @author Joker.X
     */
    @Serializable
    data class Refund(
        /**
         * 退款单号
         */
        val orderNum: String? = null,

        /**
         * 金额
         */
        val amount: Double? = null,

        /**
         * 实际退款金额
         */
        val realAmount: Double? = null,

        /**
         * 状态 0-申请中 1-已退款 2-拒绝
         */
        val status: Int? = null,

        /**
         * 申请时间
         */
        val applyTime: String? = null,

        /**
         * 退款时间
         */
        val time: String? = null,

        /**
         * 退款原因
         */
        val reason: String? = null,

        /**
         * 拒绝原因
         */
        val refuseReason: String? = null
    )
} 