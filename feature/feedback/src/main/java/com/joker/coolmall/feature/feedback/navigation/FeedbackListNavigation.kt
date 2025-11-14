package com.joker.coolmall.feature.feedback.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.feedback.view.FeedbackListRoute
import com.joker.coolmall.navigation.routes.FeedbackRoutes

/**
 * 反馈列表页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.feedbackListScreen(navController: NavHostController) {
    composable<FeedbackRoutes.List> {
        FeedbackListRoute(navController = navController)
    }
}
