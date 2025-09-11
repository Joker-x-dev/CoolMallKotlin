/**
 * @file 反馈列表页面导航
 * @author Joker.X
 */
package com.joker.coolmall.feature.feedback.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.feedback.view.FeedbackListRoute
import com.joker.coolmall.navigation.routes.FeedbackRoutes

/**
 * 反馈列表页面导航
 */
fun NavGraphBuilder.feedbackListScreen() {
    composable(route = FeedbackRoutes.LIST) {
        FeedbackListRoute()
    }
}