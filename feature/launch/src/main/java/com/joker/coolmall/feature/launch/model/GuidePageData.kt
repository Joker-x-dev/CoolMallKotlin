package com.joker.coolmall.feature.launch.model

import android.content.Context
import com.joker.coolmall.feature.launch.R

/**
 * 引导页页面数据
 *
 * @param title 页面标题
 * @param description 页面描述
 * @param image 图片资源
 * @author Joker.X
 */
data class GuidePageData(
    val title: String,
    val description: String,
    val image: Int
)

/**
 * 引导页数据提供者
 *
 * @author Joker.X
 */
object GuidePageProvider {

    /**
     * 获取所有引导页数据
     *
     * @param context 上下文
     * @return 引导页数据列表
     * @author Joker.X
     */
    fun getGuidePages(context: Context): List<GuidePageData> = listOf(
        GuidePageData(
            title = context.getString(R.string.guide_page_1_title),
            description = context.getString(R.string.guide_page_1_desc),
            image = R.drawable.ic_book_writer
        ),
        GuidePageData(
            title = context.getString(R.string.guide_page_2_title),
            description = context.getString(R.string.guide_page_2_desc),
            image = R.drawable.ic_home_screen
        ),
        GuidePageData(
            title = context.getString(R.string.guide_page_3_title),
            description = context.getString(R.string.guide_page_3_desc),
            image = R.drawable.ic_add_to_cart
        ),
        GuidePageData(
            title = context.getString(R.string.guide_page_4_title),
            description = context.getString(R.string.guide_page_4_desc),
            image = R.drawable.ic_dev_productivity
        )
    )
}