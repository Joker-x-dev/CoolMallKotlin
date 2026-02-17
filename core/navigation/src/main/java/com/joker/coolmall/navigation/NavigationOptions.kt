package com.joker.coolmall.core.navigation

import androidx.navigation3.runtime.NavKey

/**
 * 导航选项
 *
 * @param popUpToRoute 回退栈弹出到的目标路由
 * @param inclusive 是否包含目标路由本身
 * @param allowPopToEmpty 是否允许清空整个返回栈（用于替换栈底页面，如 Splash -> Main）
 * @author Joker.X
 */
data class NavigationOptions(
    val popUpToRoute: NavKey? = null,
    val inclusive: Boolean = false,
    val allowPopToEmpty: Boolean = false,
)
