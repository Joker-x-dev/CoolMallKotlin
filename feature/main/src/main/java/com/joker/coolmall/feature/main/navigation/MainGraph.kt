package com.joker.coolmall.feature.main.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 主模块导航图
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    // 只调用页面级导航函数，不包含其他逻辑
    mainScreen(sharedTransitionScope)

    // 如果主页面内部的子页面需要在Navigation中注册，也可以在这里调用
    // homeScreen()
    // categoryScreen()
    // cartScreen()
    // meScreen()
}

