package com.joker.coolmall.navigation.routes

import kotlinx.serialization.Serializable

/**
 * 反馈模块路由
 *
 * @author Joker.X
 */
object FeedbackRoutes {
    /**
     * 反馈列表路由
     *
     * @author Joker.X
     */
    @Serializable
    data object List

    /**
     * 提交反馈路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Submit
}