package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.entity.Order


/**
 * 预览订单列表
 *
 */
val previewOrders = listOf(
    Order(
        id = 503,
        userId = 1,
        title = "购买商品",
        payType = 1,
        payTime = "2025-05-18 12:37:31",
        orderNum = "U25051301028123456738",
        status = 3,
        price = 23896,
        discountPrice = 298,
        discountSource = Order.DiscountSource(
            type = 0,
            objectId = 3,
            info = Order.DiscountInfo(
                id = 3,
                num = 100,
                type = 0,
                title = "【满减券】全场满 299 减 298 元优惠券",
                description = "全场可用",
                amount = 298.0,
                minAmount = 299.0,
                startTime = "2025-05-01 00:00:00",
                endTime = "2026-06-06 00:00:00"
            )
        ),
        address = Address(
            id = 21,
            userId = 1,
            contact = "小明",
            phone = "18888888888",
            province = "广东省",
            city = "广州市",
            district = "白云区",
            address = "XXXX街道 XXX 号",
            isDefault = true,
            createTime = "2025-05-04 12:13:25",
            updateTime = "2025-05-04 19:59:48"
        ),
        logistics = Order.Logistics(
            company = "zt",
            num = "463870181015379"
        ),
        remark = "我需要顺丰快递, 包邮送上门",
        closeRemark = "超时未支付",
        invoice = 0,
        wxType = 0,
        createTime = "2025-05-10 10:28:14",
        updateTime = "2025-05-10 12:28:14"
    ),
    Order(
        id = 504,
        userId = 1,
        title = "购买商品",
        payType = 1,
        payTime = "2025-05-18 12:37:33",
        orderNum = "U25051301036123456749",
        status = 5,
        price = 7799,
        discountPrice = 0,
        address = Address(
            id = 21,
            userId = 1,
            contact = "小明",
            phone = "18888888888",
            province = "广东省",
            city = "广州市",
            district = "白云区",
            address = "XXXX街道 XXX 号",
            isDefault = true,
            createTime = "2025-05-04 12:13:25",
            updateTime = "2025-05-04 19:59:48"
        ),
        logistics = Order.Logistics(
            company = "zt",
            num = "463870181015379"
        ),
        refund = Order.Refund(
            orderNum = "504",
            amount = 59900.0,
            realAmount = 59900.0,
            status = 1,
            applyTime = "2023-11-13 14:30:15",
            time = "2023-11-15 10:05:00",
            reason = "商品存在质量问题",
            refuseReason = "商品质量完好不退款"
        ),
        refundStatus = 0,
        refundApplyTime = "2025-05-18 12:37:42",
        closeRemark = "超时未支付",
        invoice = 0,
        wxType = 0,
        createTime = "2025-05-10 10:36:10",
        updateTime = "2025-05-10 12:36:10"
    )
)

/**
 * 单个预览订单数据
 *
 */
val previewOrder = previewOrders.first()
