package com.joker.coolmall.navigation.cs

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * 客服模块路由
 *
 * @author Joker.X
 */
object CsRoutes {
    /**
     * 客服聊天路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Chat : NavKey
}
