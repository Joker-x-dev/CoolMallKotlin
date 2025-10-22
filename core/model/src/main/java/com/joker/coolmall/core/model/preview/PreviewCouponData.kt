package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.Condition
import com.joker.coolmall.core.model.entity.Coupon

/**
 * 可领取的优惠券列表（不包含useStatus字段）
 *
 */
val previewAvailableCoupons = listOf(
    Coupon(
        id = 1,
        title = "【满减券】全场满 100 减 99 元优惠券",
        description = "• 不可与其他优惠同时使用\n• 优惠券不可转让或兑换现金\n• 如有疑问请联系客服",
        type = 0,
        amount = 99.0,
        num = 100,
        receivedNum = 2,
        startTime = "2025-01-01T00:00:00",
        endTime = "2030-01-01T00:00:00",
        status = 1,
        condition = Condition(fullAmount = 100.0),
        createTime = "2025-03-29T22:40:08",
        updateTime = "2025-08-10T22:02:39"
    ),
    Coupon(
        id = 2,
        title = "【满减券】全场满 1888 减 888 元优惠券",
        description = "• 不可与其他优惠同时使用\n• 优惠券不可转让或兑换现金\n• 如有疑问请联系客服",
        type = 0,
        amount = 888.0,
        num = 100,
        receivedNum = 2,
        startTime = "2025-01-01T00:00:00",
        endTime = "2030-01-01T00:00:00",
        status = 1,
        condition = Condition(fullAmount = 1888.0),
        createTime = "2025-03-29T22:41:27",
        updateTime = "2025-08-15T22:04:51"
    ),
    Coupon(
        id = 3,
        title = "【满减券】全场满 299 减 298 元优惠券",
        description = "• 不可与其他优惠同时使用\n• 优惠券不可转让或兑换现金\n• 如有疑问请联系客服",
        type = 0,
        amount = 298.0,
        num = 100,
        receivedNum = 1,
        startTime = "2025-01-01T00:00:00",
        endTime = "2030-01-01T00:00:00",
        status = 1,
        condition = Condition(fullAmount = 299.0),
        createTime = "2025-05-10T10:26:45",
        updateTime = "2025-08-10T22:02:32"
    ),
    Coupon(
        id = 4,
        title = "【新人专享】注册即送 50 元优惠券",
        description = "• 仅限新用户使用\n• 限购买指定商品使用\n• 不可与其他优惠同时使用",
        type = 0,
        amount = 50.0,
        num = 1000,
        receivedNum = 156,
        startTime = "2025-01-01T00:00:00",
        endTime = "2025-12-31T23:59:59",
        status = 1,
        condition = Condition(fullAmount = 200.0),
        createTime = "2025-01-01T00:00:00",
        updateTime = "2025-08-20T10:30:00"
    ),
    Coupon(
        id = 5,
        title = "【限时特惠】满 500 减 100 元",
        description = "• 限时活动，过期不候\n• 全场商品通用\n• 不可与其他优惠同时使用",
        type = 0,
        amount = 100.0,
        num = 50,
        receivedNum = 23,
        startTime = "2025-08-01T00:00:00",
        endTime = "2025-08-31T23:59:59",
        status = 1,
        condition = Condition(fullAmount = 500.0),
        createTime = "2025-07-25T14:20:00",
        updateTime = "2025-08-20T16:45:00"
    )
)

/**
 * 我的优惠券列表（包含useStatus字段）
 *
 */
val previewMyCoupons = listOf(
    Coupon(
        id = 1,
        title = "【满减券】全场满 100 减 99 元优惠券",
        description = "• 不可与其他优惠同时使用\n• 优惠券不可转让或兑换现金\n• 如有疑问请联系客服",
        type = 0,
        amount = 99.0,
        num = 100,
        receivedNum = 2,
        startTime = "2025-01-01T00:00:00",
        endTime = "2030-01-01T00:00:00",
        status = 1,
        condition = Condition(fullAmount = 100.0),
        createTime = "2025-03-29T22:40:08",
        updateTime = "2025-08-10T22:02:39",
        useStatus = 0 // 未使用
    ),
    Coupon(
        id = 2,
        title = "【满减券】全场满 1888 减 888 元优惠券",
        description = "• 不可与其他优惠同时使用\n• 优惠券不可转让或兑换现金\n• 如有疑问请联系客服",
        type = 0,
        amount = 888.0,
        num = 100,
        receivedNum = 2,
        startTime = "2025-01-01T00:00:00",
        endTime = "2030-01-01T00:00:00",
        status = 1,
        condition = Condition(fullAmount = 1888.0),
        createTime = "2025-03-29T22:41:27",
        updateTime = "2025-08-15T22:04:51",
        useStatus = 0 // 未使用
    ),
    Coupon(
        id = 3,
        title = "【满减券】全场满 299 减 298 元优惠券",
        description = "• 不可与其他优惠同时使用\n• 优惠券不可转让或兑换现金\n• 如有疑问请联系客服",
        type = 0,
        amount = 298.0,
        num = 100,
        receivedNum = 1,
        startTime = "2025-01-01T00:00:00",
        endTime = "2030-01-01T00:00:00",
        status = 1,
        condition = Condition(fullAmount = 299.0),
        createTime = "2025-05-10T10:26:45",
        updateTime = "2025-08-10T22:02:32",
        useStatus = 1 // 已使用
    ),
    Coupon(
        id = 6,
        title = "【过期券】满 200 减 50 元优惠券",
        description = "• 此优惠券已过期\n• 不可与其他优惠同时使用\n• 如有疑问请联系客服",
        type = 0,
        amount = 50.0,
        num = 100,
        receivedNum = 45,
        startTime = "2024-01-01T00:00:00",
        endTime = "2024-12-31T23:59:59",
        status = 1,
        condition = Condition(fullAmount = 200.0),
        createTime = "2024-01-01T00:00:00",
        updateTime = "2024-12-31T23:59:59",
        useStatus = 2 // 已过期
    ),
    Coupon(
        id = 7,
        title = "【已使用】满 500 减 80 元优惠券",
        description = "• 此优惠券已使用\n• 感谢您的购买\n• 如有疑问请联系客服",
        type = 0,
        amount = 80.0,
        num = 200,
        receivedNum = 89,
        startTime = "2025-01-01T00:00:00",
        endTime = "2025-12-31T23:59:59",
        status = 1,
        condition = Condition(fullAmount = 500.0),
        createTime = "2025-06-01T00:00:00",
        updateTime = "2025-08-15T14:30:00",
        useStatus = 1 // 已使用
    )
)

/**
 * 单个优惠券示例（可领取状态）
 *
 */
val previewAvailableCoupon = previewAvailableCoupons.first()

/**
 * 单个优惠券示例（我的优惠券）
 *
 */
val previewMyCoupon = previewMyCoupons.first()

/**
 * 不同使用状态的优惠券
 *
 */
val previewUnusedCoupon = previewMyCoupons.first { it.useStatus == 0 }
val previewUsedCoupon = previewMyCoupons.first { it.useStatus == 1 }
val previewExpiredCoupon = previewMyCoupons.first { it.useStatus == 2 }