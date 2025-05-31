package com.joker.coolmall.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.joker.coolmall.feature.auth.navigation.authGraph
import com.joker.coolmall.feature.cs.navigation.csGraph
import com.joker.coolmall.feature.goods.navigation.goodsGraph
import com.joker.coolmall.feature.main.navigation.mainGraph
import com.joker.coolmall.feature.order.navigation.orderGraph
import com.joker.coolmall.feature.user.navigation.userGraph
import com.joker.coolmall.navigation.routes.MainRoutes
import kotlinx.coroutines.flow.collectLatest

/**
 * 应用导航宿主
 * 配置整个应用的导航图和动画
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    navigator: AppNavigator,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    // 监听导航事件
    LaunchedEffect(navController) {
        navigator.navigationEvents.collectLatest { event ->
            navController.handleNavigationEvent(event)
        }
    }

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = MainRoutes.MAIN,
            modifier = modifier,
            // 页面进入动画
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            // 页面退出动画
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            // 返回时页面进入动画
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            // 返回时页面退出动画
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            // 只调用模块级Graph函数，大大减少了冲突可能性
            mainGraph(
                navController,
                this@SharedTransitionLayout
            )
            goodsGraph(navController)
            authGraph(navController)
            userGraph(navController, this@SharedTransitionLayout)
            orderGraph(navController)
            csGraph(navController)
        }
    }
}