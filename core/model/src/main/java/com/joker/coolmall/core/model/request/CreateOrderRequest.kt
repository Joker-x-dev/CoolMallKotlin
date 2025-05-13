package com.joker.coolmall.core.model.request

import com.joker.coolmall.core.model.entity.SelectedGoods
import kotlinx.serialization.Serializable


/**
 * 创建订单请求模型
 *
 * 用于创建订单时传递的参数
 */
@Serializable
data class CreateOrderRequest(
    /**
     * 内部数据类，封装创建订单所需的具体参数
     */
    val data: CreateOrder
) {
    /**
     * 内部数据类，封装创建订单所需的具体参数
     */
    @Serializable
    data class CreateOrder(
        /**
         * 备注信息
         */
        var remark: String = "",

        /**
         * 地址ID
         */
        var addressId: Long,

        /**
         * 选中的商品列表
         */
        var goodsList: List<SelectedGoods>,

        /**
         * 优惠券ID （可选）
         */
        var couponId: Long? = null,

        /**
         * 标题
         */
        var title: String = ""
    )
}