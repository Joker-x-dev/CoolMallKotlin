package com.joker.coolmall.feature.cs.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.cs.view.ChatRoute
import com.joker.coolmall.navigation.routes.CsRoutes

/**
 * 客服聊天页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.chatScreen() {
    composable<CsRoutes.Chat> {
        ChatRoute()
    }
}
