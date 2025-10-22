package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.Logistics
import com.joker.coolmall.core.model.entity.LogisticsItem

/**
 * 预览物流数据
 *
 * @author Joker.X
 */
val previewLogisticsData = Logistics(
    number = "YD1234567890",
    type = "yunda",
    list = listOf(
        LogisticsItem(
            time = "2024-11-15 16:45:22",
            status = "【代收点】您的快件已投递至星河湾小区南门智能快递柜（3号柜），取件码：SF7890，联系快递员：李师傅(13812345678)"
        ),
        LogisticsItem(
            time = "2024-11-15 14:20:10",
            status = "【杭州市】已到达 浙江杭州西湖区配送站"
        ),
        LogisticsItem(
            time = "2024-11-15 12:05:33",
            status = "【杭州市】浙江杭州西湖区配送站 快递员 李师傅(13812345678) 开始派送"
        ),
        LogisticsItem(
            time = "2024-11-15 10:18:45",
            status = "【杭州市】已离开 浙江杭州分拨中心；发往 西湖区配送站"
        ),
        LogisticsItem(
            time = "2024-11-14 22:30:15",
            status = "【上海市】已离开 上海浦东分拨中心；发往 浙江杭州"
        ),
        LogisticsItem(
            time = "2024-11-14 20:15:00",
            status = "【上海市】已到达 上海浦东分拨中心"
        ),
        LogisticsItem(
            time = "2024-11-14 18:02:37",
            status = "【上海市】上海浦东新区张江公司 已揽收，快递员：王强(15987654321)"
        )
    ),
    deliverystatus = "3",
    issign = "1",
    expName = "韵达快递",
    expSite = "www.yundaex.com",
    expPhone = "95338",
    logo = "https://img3.fegine.com/express/yd.jpg",
    courier = "李师傅",
    courierPhone = "18888888888",
    updateTime = "2024-11-15 16:45:22",
    takeTime = "22小时42分"
)

/**
 * 预览物流轨迹列表
 *
 * @author Joker.X
 */
val previewLogisticsItemList = previewLogisticsData.list ?: emptyList()

/**
 * 单个预览物流轨迹项
 *
 * @author Joker.X
 */
val previewLogisticsItem = previewLogisticsItemList.firstOrNull()