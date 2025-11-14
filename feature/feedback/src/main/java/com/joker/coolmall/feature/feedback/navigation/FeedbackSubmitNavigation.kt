package com.joker.coolmall.feature.feedback.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.feedback.view.FeedbackSubmitRoute
import com.joker.coolmall.navigation.routes.FeedbackRoutes

/**
 * 提交反馈页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.feedbackSubmitScreen() {
    composable<FeedbackRoutes.Submit> {
        FeedbackSubmitRoute()
    }
}
