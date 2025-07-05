package com.joker.coolmall.feature.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.common.view.WebRoute
import com.joker.coolmall.navigation.routes.CommonRoutes

/**
 * WebView 页面路由常量
 */
object WebRoutes {
    const val URL_ARG = "url"
    const val TITLE_ARG = "title"

    /**
     * 带参数的路由模式
     */
    const val WEB_PATTERN = "${CommonRoutes.WEB}?${URL_ARG}={${URL_ARG}}&${TITLE_ARG}={${TITLE_ARG}}"
}

/**
 * 网页页面导航
 */
fun NavGraphBuilder.webScreen() {
    composable(
        route = WebRoutes.WEB_PATTERN,
        arguments = listOf(
            navArgument(WebRoutes.URL_ARG) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(WebRoutes.TITLE_ARG) {
                type = NavType.StringType
                defaultValue = "网页"
            }
        )
    ) {
        WebRoute()
    }
} 