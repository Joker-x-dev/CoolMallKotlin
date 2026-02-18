package com.joker.coolmall.navigation.launch

import androidx.navigation3.runtime.NavKey
import com.joker.coolmall.navigation.navigate
import com.joker.coolmall.navigation.navigateAndCloseCurrent

/**
 * 启动流程模块导航封装
 *
 * @author Joker.X
 */
object LaunchNavigator {

    /**
     * 跳转到启动页
     *
     * @author Joker.X
     */
    fun toSplash() {
        navigate(LaunchRoutes.Splash)
    }

    /**
     * 跳转到引导页
     *
     * @param fromSettings 是否从设置页进入
     * @author Joker.X
     */
    fun toGuide(fromSettings: Boolean = false) {
        navigate(LaunchRoutes.Guide(fromSettings = fromSettings))
    }

    /**
     * 关闭当前页并跳转到引导页
     *
     * @param currentRoute 当前路由
     * @param fromSettings 是否从设置页进入
     * @author Joker.X
     */
    fun toGuideAndCloseCurrent(currentRoute: NavKey, fromSettings: Boolean = false) {
        navigateAndCloseCurrent(
            route = LaunchRoutes.Guide(fromSettings = fromSettings),
            currentRoute = currentRoute,
        )
    }
}
