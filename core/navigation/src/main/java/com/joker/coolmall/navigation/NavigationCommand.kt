package com.joker.coolmall.navigation

import androidx.navigation3.runtime.NavKey

/**
 * 导航命令
 *
 * @author Joker.X
 */
internal sealed interface NavigationCommand {
    /**
     * 执行导航命令
     *
     * @param controller 导航控制器
     * @author Joker.X
     */
    fun execute(controller: NavigationController)

    /**
     * 导航到指定路由命令
     *
     * @property route 目标路由
     * @property navOptions 导航选项
     * @author Joker.X
     */
    data class NavigateTo(
        val route: NavKey,
        val navOptions: NavigationOptions?,
    ) : NavigationCommand {
        /**
         * 执行命令
         *
         * @param controller 导航控制器
         * @author Joker.X
         */
        override fun execute(controller: NavigationController) {
            controller.navigateTo(route, navOptions)
        }
    }

    /**
     * 返回上一页命令
     *
     * @author Joker.X
     */
    data object NavigateUp : NavigationCommand {
        /**
         * 执行命令
         *
         * @param controller 导航控制器
         * @author Joker.X
         */
        override fun execute(controller: NavigationController) {
            controller.navigateBack()
        }
    }

    /**
     * 回退到指定路由命令
     *
     * @property route 目标路由
     * @property inclusive 是否包含目标路由
     * @author Joker.X
     */
    data class NavigateBackTo(
        val route: NavKey,
        val inclusive: Boolean,
    ) : NavigationCommand {
        /**
         * 执行命令
         *
         * @param controller 导航控制器
         * @author Joker.X
         */
        override fun execute(controller: NavigationController) {
            controller.navigateBackTo(route, inclusive)
        }
    }

    /**
     * 回退并回传结果命令
     *
     * @property key 结果 Key
     * @property result 结果值
     * @author Joker.X
     */
    data class PopBackStackWithResult<T>(
        val key: NavigationResultKey<T>,
        val result: T,
    ) : NavigationCommand {
        /**
         * 执行命令
         *
         * @param controller 导航控制器
         * @author Joker.X
         */
        override fun execute(controller: NavigationController) {
            controller.popBackStackWithResult(key, result)
        }
    }
}
