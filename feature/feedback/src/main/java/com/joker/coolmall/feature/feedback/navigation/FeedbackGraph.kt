package com.joker.coolmall.feature.feedback.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

/**
 * 反馈模块导航图
 *
 * @param navController 导航控制器
 * @author Joker.X
 */
fun NavGraphBuilder.feedbackGraph(navController: NavController) {
    // 反馈列表页面
    feedbackListScreen()

    // 提交反馈页面
    feedbackSubmitScreen()
}