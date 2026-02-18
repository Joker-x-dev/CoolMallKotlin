package com.joker.coolmall.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.joker.coolmall.navigation.launch.LaunchRoutes
import com.joker.coolmall.feature.auth.navigation.authGraph
import com.joker.coolmall.feature.common.navigation.commonGraph
import com.joker.coolmall.feature.cs.navigation.csGraph
import com.joker.coolmall.feature.feedback.navigation.feedbackGraph
import com.joker.coolmall.feature.goods.navigation.goodsGraph
import com.joker.coolmall.feature.launch.navigation.launchGraph
import com.joker.coolmall.feature.main.navigation.mainGraph
import com.joker.coolmall.feature.market.navigation.marketGraph
import com.joker.coolmall.feature.order.navigation.orderGraph
import com.joker.coolmall.feature.user.navigation.userGraph

/**
 * 页面切换动画时长（毫秒）
 */
private const val NAV_ANIMATION_DURATION = 300

/**
 * 页面切换动画规范
 */
private val NAV_ANIMATION_SPEC: FiniteAnimationSpec<IntOffset> =
    tween(durationMillis = NAV_ANIMATION_DURATION)

/**
 * 应用导航宿主
 *
 * @param navigator 导航管理器
 * @param modifier 修饰符
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    navigator: AppNavigator,
    modifier: Modifier = Modifier,
) {
    // 创建应用级回退栈，首个页面固定为主页面。
    val backStack = rememberNavBackStack(LaunchRoutes.Splash)
    // 基于当前回退栈构建导航控制器，供 AppNavigator 分发命令时使用。
    val navigationController = remember(backStack, navigator) {
        createBackStackNavigationController(backStack, navigator)
    }

    // 在组合生命周期内绑定/解绑导航控制器，确保导航命令总是指向当前有效宿主。
    DisposableEffect(navigationController) {
        // 绑定到 AppNavigator，接收全局导航命令。
        navigator.attachController(navigationController)
        // 绑定到全局导航服务，支持业务层直接调用 navigate(...)。
        NavigationService.bind(navigator)
        onDispose {
            // 宿主销毁时先解绑导航服务，避免持有失效导航器引用。
            NavigationService.unbind(navigator)
            // 最后从 AppNavigator 注销控制器，防止后续命令误发到旧宿主。
            navigator.detachController(navigationController)
        }
    }

    SharedTransitionLayout {
        NavDisplay(
            backStack = backStack,
            modifier = modifier,
            onBack = { navigationController.navigateBack() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            transitionSpec = { createForwardTransition() },
            popTransitionSpec = { createBackwardTransition() },
            predictivePopTransitionSpec = { createBackwardTransition() },
            entryProvider = appEntryProvider(this@SharedTransitionLayout),
        )
    }
}

/**
 * 创建前进导航动画（右入左出）
 *
 * @return 前进导航动画
 * @author Joker.X
 */
private fun createForwardTransition() = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = NAV_ANIMATION_SPEC,
) togetherWith slideOutHorizontally(
    targetOffsetX = { -it },
    animationSpec = NAV_ANIMATION_SPEC,
)

/**
 * 创建返回导航动画（左入右出）
 *
 * @return 返回导航动画
 * @author Joker.X
 */
private fun createBackwardTransition() = slideInHorizontally(
    initialOffsetX = { -it },
    animationSpec = NAV_ANIMATION_SPEC,
) togetherWith slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = NAV_ANIMATION_SPEC,
)

/**
 * 构建应用级路由注册器
 *
 * 按模块聚合 graph，避免全部 entry 混在同一个函数中。
 *
 * @return 应用级 EntryProvider
 * @author Joker.X
 */
private fun appEntryProvider(sharedTransitionScope: SharedTransitionScope) = entryProvider {
    mainGraph(sharedTransitionScope)
    goodsGraph()
    authGraph()
    userGraph(sharedTransitionScope)
    orderGraph()
    csGraph()
    commonGraph()
    marketGraph()
    feedbackGraph()
    launchGraph(sharedTransitionScope)
}
