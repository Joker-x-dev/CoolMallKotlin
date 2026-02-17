package com.joker.coolmall.core.navigation.feedback

import androidx.navigation3.runtime.NavKey
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
    data object List : NavKey

    /**
     * 提交反馈路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Submit : NavKey
}