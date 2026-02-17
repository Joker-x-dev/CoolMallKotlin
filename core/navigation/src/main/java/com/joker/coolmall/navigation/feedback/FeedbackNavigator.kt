package com.joker.coolmall.core.navigation.feedback

import com.joker.coolmall.core.navigation.navigate

/**
 * 反馈模块导航封装
 *
 * @author Joker.X
 */
object FeedbackNavigator {

    /**
     * 跳转到反馈列表页
     *
     * @author Joker.X
     */
    fun toList() {
        navigate(FeedbackRoutes.List)
    }

    /**
     * 跳转到提交反馈页
     *
     * @author Joker.X
     */
    fun toSubmit() {
        navigate(FeedbackRoutes.Submit)
    }
}
