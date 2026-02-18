package com.joker.coolmall.feature.launch.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.joker.coolmall.navigation.launch.LaunchRoutes
import com.joker.coolmall.feature.launch.view.GuideRoute
import com.joker.coolmall.feature.launch.view.SplashRoute

/**
 * 启动模块导航图
 *
 * @param sharedTransitionScope 共享转换作用域
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun EntryProviderScope<NavKey>.launchGraph(
    sharedTransitionScope: SharedTransitionScope,
) {
    entry<LaunchRoutes.Splash> {
        SplashRoute(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = LocalNavAnimatedContentScope.current,
        )
    }
    entry<LaunchRoutes.Guide> { key ->
        GuideRoute(navKey = key)
    }
}
