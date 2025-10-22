package com.joker.coolmall.feature.cs.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 客服模块导航图
 *
 * 整合客服模块下所有页面的导航
 *
 * @param navController 导航控制器
 * @author Joker.X
 */
fun NavGraphBuilder.csGraph(navController: NavHostController) {
    // 客服聊天页面
    chatScreen()
} 