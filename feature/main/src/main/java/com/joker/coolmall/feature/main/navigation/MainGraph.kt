package com.joker.coolmall.feature.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 主模块导航图
 */
fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    // 只调用页面级导航函数，不包含其他逻辑
    mainScreen()
    
    // 如果主页面内部的子页面需要在Navigation中注册，也可以在这里调用
    // homeScreen()
    // categoryScreen()
    // cartScreen()
    // meScreen()
}

