package com.joker.coolmall.navigation.launch

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * 启动流程模块路由
 *
 * @author Joker.X
 */
object LaunchRoutes {
    /**
     * 启动页路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Splash : NavKey

    /**
     * 引导页路由
     *
     * @param fromSettings 是否从设置页面进入
     * @author Joker.X
     */
    @Serializable
    data class Guide(val fromSettings: Boolean = false) : NavKey
}
