package com.joker.coolmall.feature.main.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 主模块导航图
 *
 * @param navController 导航控制器
 * @param sharedTransitionScope 共享转场作用域
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    // 只调用页面级导航函数，不包含其他逻辑
    mainScreen(sharedTransitionScope)

    // 购物车页面导航
    cartScreen()

    // 如果主页面内部的子页面需要在Navigation中注册，也可以在这里调用
    // homeScreen()
    // categoryScreen()
    // meScreen()
}

