package com.joker.coolmall.feature.user.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.joker.coolmall.core.navigation.user.UserRoutes
import com.joker.coolmall.feature.user.view.AddressDetailRoute
import com.joker.coolmall.feature.user.view.AddressListRoute
import com.joker.coolmall.feature.user.view.FootprintRoute
import com.joker.coolmall.feature.user.view.ProfileRoute

/**
 * 用户模块导航图
 *
 * @param sharedTransitionScope 共享转换作用域
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun EntryProviderScope<NavKey>.userGraph(
    sharedTransitionScope: SharedTransitionScope,
) {
    entry<UserRoutes.Profile> {
        ProfileRoute(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = LocalNavAnimatedContentScope.current,
        )
    }
    entry<UserRoutes.AddressList> { key ->
        AddressListRoute(navKey = key)
    }
    entry<UserRoutes.AddressDetail> { key ->
        AddressDetailRoute(navKey = key)
    }
    entry<UserRoutes.Footprint> {
        FootprintRoute()
    }
}
