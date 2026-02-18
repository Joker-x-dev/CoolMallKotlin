package com.joker.coolmall.navigation.cs

import com.joker.coolmall.navigation.navigate

/**
 * 客服模块导航封装
 *
 * @author Joker.X
 */
object CsNavigator {

    /**
     * 跳转到客服聊天页
     *
     * @author Joker.X
     */
    fun toChat() {
        navigate(CsRoutes.Chat)
    }
}
