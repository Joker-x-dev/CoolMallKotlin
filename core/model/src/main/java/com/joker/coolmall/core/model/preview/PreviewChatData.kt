package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.CsMsg

/**
 * 预览客服聊天数据
 *
 */

// 客服头像
private const val CS_AVATAR =
    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F6d416005e44546d581f5a9189a69c756_5%E5%A4%B4%E5%83%8F.png"

// 用户头像
private const val USER_AVATAR =
    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fe8d66b8f73bc463a80e4b983ed61c2af_4%E5%A4%B4%E5%83%8F.png"

/**
 * 订单问题聊天记录
 *
 */
val orderProblemChat = listOf(
    CsMsg(
        id = 1L,
        userId = 10001L,
        sessionId = 2001L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "您好，请问有什么可以帮到您的呢？"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "Joker.X",
        adminUserHeadImg = CS_AVATAR,
        createTime = "10:30"
    ),
    CsMsg(
        id = 2L,
        userId = 10001L,
        sessionId = 2001L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "您好，我想咨询一下关于订单的问题"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "Joker.X",
        adminUserHeadImg = CS_AVATAR,
        createTime = "10:32"
    ),
    CsMsg(
        id = 3L,
        userId = 10001L,
        sessionId = 2001L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "请问是哪个订单呢？"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "Joker.X",
        adminUserHeadImg = CS_AVATAR,
        createTime = "10:33"
    ),
    CsMsg(
        id = 4L,
        userId = 10001L,
        sessionId = 2001L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "可以提供一下订单号吗？"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "Joker.X",
        adminUserHeadImg = CS_AVATAR,
        createTime = "10:33"
    ),
    CsMsg(
        id = 5L,
        userId = 10001L,
        sessionId = 2001L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "我的订单号是CMS12345678，显示已发货"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "Joker.X",
        adminUserHeadImg = CS_AVATAR,
        createTime = "10:35"
    ),
    CsMsg(
        id = 6L,
        userId = 10001L,
        sessionId = 2001L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "但是物流信息一直没有更新"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "Joker.X",
        adminUserHeadImg = CS_AVATAR,
        createTime = "10:35"
    ),
    CsMsg(
        id = 7L,
        userId = 10001L,
        sessionId = 2001L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "请您稍等，我帮您查询一下订单物流信息"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "Joker.X",
        adminUserHeadImg = CS_AVATAR,
        createTime = "10:36"
    ),
    CsMsg(
        id = 8L,
        userId = 10001L,
        sessionId = 2001L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "您的订单已于昨天发出，因为天气原因物流可能有所延迟，请您再耐心等待1-2天"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "Joker.X",
        adminUserHeadImg = CS_AVATAR,
        createTime = "10:38"
    ),
    CsMsg(
        id = 9L,
        userId = 10001L,
        sessionId = 2001L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "好的，谢谢您"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "Joker.X",
        adminUserHeadImg = CS_AVATAR,
        createTime = "10:39"
    )
)

/**
 * 退款问题聊天记录
 *
 */
val refundProblemChat = listOf(
    CsMsg(
        id = 1L,
        userId = 10002L,
        sessionId = 2002L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "您好，很高兴为您服务，请问有什么可以帮到您？"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "客服李四",
        adminUserHeadImg = CS_AVATAR,
        createTime = "14:20"
    ),
    CsMsg(
        id = 2L,
        userId = 10002L,
        sessionId = 2002L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "您好，我想申请退款"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "客服李四",
        adminUserHeadImg = CS_AVATAR,
        createTime = "14:21"
    ),
    CsMsg(
        id = 3L,
        userId = 10002L,
        sessionId = 2002L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "我购买的商品与描述不符"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "客服李四",
        adminUserHeadImg = CS_AVATAR,
        createTime = "14:22"
    ),
    CsMsg(
        id = 4L,
        userId = 10002L,
        sessionId = 2002L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "好的，请您提供一下订单号，我帮您查询"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "客服李四",
        adminUserHeadImg = CS_AVATAR,
        createTime = "14:23"
    ),
    CsMsg(
        id = 5L,
        userId = 10002L,
        sessionId = 2002L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "订单号是CMS87654321"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "客服李四",
        adminUserHeadImg = CS_AVATAR,
        createTime = "14:25"
    ),
    CsMsg(
        id = 6L,
        userId = 10002L,
        sessionId = 2002L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "请问您是想申请退货退款还是仅退款呢？"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "客服李四",
        adminUserHeadImg = CS_AVATAR,
        createTime = "14:26"
    ),
    CsMsg(
        id = 7L,
        userId = 10002L,
        sessionId = 2002L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "我想申请退货退款"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "客服李四",
        adminUserHeadImg = CS_AVATAR,
        createTime = "14:27"
    ),
    CsMsg(
        id = 8L,
        userId = 10002L,
        sessionId = 2002L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "好的，我已为您创建退货申请，请您在我的订单-退款/售后中上传商品问题的照片，并填写详细退货原因"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "客服李四",
        adminUserHeadImg = CS_AVATAR,
        createTime = "14:30"
    ),
    CsMsg(
        id = 9L,
        userId = 10002L,
        sessionId = 2002L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "审核通过后，系统会显示退货地址，请您按照地址退回商品"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "客服李四",
        adminUserHeadImg = CS_AVATAR,
        createTime = "14:31"
    ),
    CsMsg(
        id = 10L,
        userId = 10002L,
        sessionId = 2002L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "好的，我明白了，谢谢"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "客服李四",
        adminUserHeadImg = CS_AVATAR,
        createTime = "14:32"
    )
)

/**
 * 商品咨询聊天记录
 *
 */
val productInquiryChat = listOf(
    CsMsg(
        id = 1L,
        userId = 10003L,
        sessionId = 2003L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "您好，欢迎咨询，请问有什么可以帮您？"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "产品顾问王五",
        adminUserHeadImg = CS_AVATAR,
        createTime = "16:10"
    ),
    CsMsg(
        id = 2L,
        userId = 10003L,
        sessionId = 2003L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "您好，我想咨询一下这款手机的详细参数"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "产品顾问王五",
        adminUserHeadImg = CS_AVATAR,
        createTime = "16:12"
    ),
    CsMsg(
        id = 3L,
        userId = 10003L,
        sessionId = 2003L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "具体是哪款手机呢？可以提供一下商品链接或者型号吗？"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "产品顾问王五",
        adminUserHeadImg = CS_AVATAR,
        createTime = "16:13"
    ),
    CsMsg(
        id = 4L,
        userId = 10003L,
        sessionId = 2003L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "是首页推荐的那款超薄5G手机，商品ID是10086"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "产品顾问王五",
        adminUserHeadImg = CS_AVATAR,
        createTime = "16:15"
    ),
    CsMsg(
        id = 5L,
        userId = 10003L,
        sessionId = 2003L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "好的，我查询到了，这款手机的主要参数如下：\n处理器：骁龙8+ Gen 1\n内存：8GB/12GB\n存储：128GB/256GB/512GB\n屏幕：6.7英寸 AMOLED 高刷屏\n电池：4500mAh\n快充：67W有线 + 50W无线\n相机：后置5000万像素三摄，前置3200万像素"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "产品顾问王五",
        adminUserHeadImg = CS_AVATAR,
        createTime = "16:17"
    ),
    CsMsg(
        id = 6L,
        userId = 10003L,
        sessionId = 2003L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "请问支持NFC和红外功能吗？"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "产品顾问王五",
        adminUserHeadImg = CS_AVATAR,
        createTime = "16:19"
    ),
    CsMsg(
        id = 7L,
        userId = 10003L,
        sessionId = 2003L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "是的，这款手机支持NFC功能，可以用于公交卡、门禁卡模拟和手机支付等。同时也支持红外遥控功能，可以控制电视、空调等家电。"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "产品顾问王五",
        adminUserHeadImg = CS_AVATAR,
        createTime = "16:20"
    ),
    CsMsg(
        id = 8L,
        userId = 10003L,
        sessionId = 2003L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "另外，这款手机还支持IP68防水防尘，屏幕使用了康宁大猩猩玻璃，抗摔性能很好。"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "产品顾问王五",
        adminUserHeadImg = CS_AVATAR,
        createTime = "16:21"
    ),
    CsMsg(
        id = 9L,
        userId = 10003L,
        sessionId = 2003L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "好的，谢谢您的详细介绍，我了解了"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "产品顾问王五",
        adminUserHeadImg = CS_AVATAR,
        createTime = "16:23"
    ),
    CsMsg(
        id = 10L,
        userId = 10003L,
        sessionId = 2003L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "不客气，如果您对其他商品有疑问，随时可以咨询我们。祝您购物愉快！"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "产品顾问王五",
        adminUserHeadImg = CS_AVATAR,
        createTime = "16:24"
    )
)

/**
 * 售后服务聊天记录
 *
 */
val afterSaleServiceChat = listOf(
    CsMsg(
        id = 1L,
        userId = 10004L,
        sessionId = 2004L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "您好，有什么可以帮到您的吗？"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "售后专员小张",
        adminUserHeadImg = CS_AVATAR,
        createTime = "09:05"
    ),
    CsMsg(
        id = 2L,
        userId = 10004L,
        sessionId = 2004L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "您好，我购买的耳机有杂音，想了解一下保修政策"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "售后专员小张",
        adminUserHeadImg = CS_AVATAR,
        createTime = "09:07"
    ),
    CsMsg(
        id = 3L,
        userId = 10004L,
        sessionId = 2004L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "所有耳机类产品都享有1年的保修期，如果是非人为损坏，可以免费维修或更换"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "售后专员小张",
        adminUserHeadImg = CS_AVATAR,
        createTime = "09:09"
    ),
    CsMsg(
        id = 4L,
        userId = 10004L,
        sessionId = 2004L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "请问您购买的具体是哪款耳机呢？购买时间是什么时候？"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "售后专员小张",
        adminUserHeadImg = CS_AVATAR,
        createTime = "09:10"
    ),
    CsMsg(
        id = 5L,
        userId = 10004L,
        sessionId = 2004L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "是上个月购买的蓝牙降噪耳机，订单号是CMS55667788"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "售后专员小张",
        adminUserHeadImg = CS_AVATAR,
        createTime = "09:12"
    ),
    CsMsg(
        id = 6L,
        userId = 10004L,
        sessionId = 2004L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "好的，我已查询到您的订单信息。您描述的杂音问题很可能是产品故障，建议您申请售后服务"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "售后专员小张",
        adminUserHeadImg = CS_AVATAR,
        createTime = "09:15"
    ),
    CsMsg(
        id = 7L,
        userId = 10004L,
        sessionId = 2004L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "您可以在我的订单中找到该订单，点击申请售后，选择产品故障，然后按提示操作"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "售后专员小张",
        adminUserHeadImg = CS_AVATAR,
        createTime = "09:16"
    ),
    CsMsg(
        id = 8L,
        userId = 10004L,
        sessionId = 2004L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "这款耳机支持先行赔付服务，您可以先收到新品再寄回故障产品"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "售后专员小张",
        adminUserHeadImg = CS_AVATAR,
        createTime = "09:17"
    ),
    CsMsg(
        id = 9L,
        userId = 10004L,
        sessionId = 2004L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "好的，非常感谢您的耐心解答，我现在就去申请"
        ),
        type = 0, // 用户反馈
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "售后专员小张",
        adminUserHeadImg = CS_AVATAR,
        createTime = "09:20"
    ),
    CsMsg(
        id = 10L,
        userId = 10004L,
        sessionId = 2004L,
        content = CsMsg.MessageContent(
            type = "TEXT",
            data = "不客气，很高兴能帮到您。如有其他问题，随时咨询"
        ),
        type = 1, // 客服回复
        status = 1, // 已读
        nickName = "我",
        avatarUrl = USER_AVATAR,
        adminUserName = "售后专员小张",
        adminUserHeadImg = CS_AVATAR,
        createTime = "09:21"
    )
)

/**
 * 所有聊天记录集合
 *
 */
val allChatRecords = listOf(
    orderProblemChat,
    refundProblemChat,
    productInquiryChat,
    afterSaleServiceChat
).flatten()