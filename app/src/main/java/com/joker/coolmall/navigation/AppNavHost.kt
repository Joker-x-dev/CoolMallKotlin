package com.joker.coolmall.navigation

import MAIN_ROUTE
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import mainScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    // 创建导航控制器实例，用于管理页面导航
    val navController = rememberNavController()

    NavHost(
        // 设置导航控制器
        navController = navController,
        // 设置应用启动时显示的首页
        startDestination = MAIN_ROUTE,
        // 设置修饰符
        modifier = modifier,
        // 配置页面进入动画：从右向左滑入
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                // 设置动画持续时间为300毫秒
                animationSpec = tween(300)
            )
        },
        // 配置页面退出动画：向左滑出
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        // 配置返回时页面进入动画：从左向右滑入
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        // 配置返回时页面退出动画：向右滑出
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        }
    ) {
        // 主页面（包含底部导航栏和四个子页面）
        mainScreen()
    }
}