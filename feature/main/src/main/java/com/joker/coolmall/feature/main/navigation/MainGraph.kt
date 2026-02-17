package com.joker.coolmall.feature.main.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.joker.coolmall.core.navigation.main.MainRoutes
import com.joker.coolmall.feature.main.view.CartRoute
import com.joker.coolmall.feature.main.view.MainRoute

/**
 * 主模块导航图
 *
 * @param sharedTransitionScope 共享转换作用域
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun EntryProviderScope<NavKey>.mainGraph(
    sharedTransitionScope: SharedTransitionScope,
) {
    entry<MainRoutes.Main> {
        MainRoute(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = LocalNavAnimatedContentScope.current,
        )
    }
    entry<MainRoutes.Cart> { key ->
        CartRoute(navKey = key)
    }
}
