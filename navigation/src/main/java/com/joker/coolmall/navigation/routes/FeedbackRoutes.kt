package com.joker.coolmall.navigation.routes

/**
 * 反馈模块路由常量
 *
 * @author Joker.X
 */
object FeedbackRoutes {
    /**
     * 反馈模块根路由
     */
    private const val FEEDBACK_ROUTE = "feedback"

    /**
     * 反馈列表路由
     */
    const val LIST = "$FEEDBACK_ROUTE/list"

    /**
     * 提交反馈路由
     */
    const val SUBMIT = "$FEEDBACK_ROUTE/submit"
}