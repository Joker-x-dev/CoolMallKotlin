/**
 * 引导页数据模型
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.launch.model

import com.joker.coolmall.feature.launch.R

/**
 * 引导页页面数据
 *
 * @param title 页面标题
 * @param description 页面描述
 * @param image 图片资源
 */
data class GuidePageData(
    val title: String,
    val description: String,
    val image: Int
)

/**
 * 引导页数据提供者
 */
object GuidePageProvider {

    /**
     * 获取所有引导页数据
     *
     * @return 引导页数据列表
     */
    fun getGuidePages(): List<GuidePageData> = listOf(
        GuidePageData(
            title = "青商城 - 开源电商学习项目",
            description = "100% Kotlin 开发的现代化电商应用\n完全开源免费，助力开发者快速成长\n提供完整的代码参考和学习资源",
            image = R.drawable.ic_book_writer
        ),
        GuidePageData(
            title = "声明式 UI 新体验",
            description = "基于 Jetpack Compose 构建流畅界面\n采用 MVVM + Clean Architecture 架构\nHilt、Coroutines、Flow 等主流技术全整合",
            image = R.drawable.ic_home_screen
        ),
        GuidePageData(
            title = "完整电商业务流程",
            description = "用户认证、商品展示、搜索分类\n购物车管理、订单支付全流程实现\n贴近真实商业场景的学习案例",
            image = R.drawable.ic_add_to_cart
        ),
        GuidePageData(
            title = "模块化架构设计",
            description = "参考 Google Now in Android 最佳实践\n高内聚低耦合，便于维护与扩展\n适合学习与二次开发的优质项目",
            image = R.drawable.ic_dev_productivity
        )
    )
}