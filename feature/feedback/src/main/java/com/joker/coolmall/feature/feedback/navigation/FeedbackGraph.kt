package com.joker.coolmall.feature.feedback.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.joker.coolmall.navigation.feedback.FeedbackRoutes
import com.joker.coolmall.feature.feedback.view.FeedbackListRoute
import com.joker.coolmall.feature.feedback.view.FeedbackSubmitRoute

/**
 * 反馈模块导航图
 *
 * @author Joker.X
 */
fun EntryProviderScope<NavKey>.feedbackGraph() {
    entry<FeedbackRoutes.List> {
        FeedbackListRoute()
    }
    entry<FeedbackRoutes.Submit> {
        FeedbackSubmitRoute()
    }
}
