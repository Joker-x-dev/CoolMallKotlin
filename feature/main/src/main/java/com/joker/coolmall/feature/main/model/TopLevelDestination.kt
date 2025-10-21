package com.joker.coolmall.feature.main.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.navigation.routes.MainRoutes

/**
 * 顶级导航目的地
 *
 * @param titleTextId 标题文本资源ID
 * @param animationResId 动画资源ID
 * @param route 路由地址
 * @author Joker.X
 */
enum class TopLevelDestination(
    @param:StringRes val titleTextId: Int,
    @param:RawRes val animationResId: Int,
    val route: String
) {
    HOME(
        titleTextId = R.string.home,
        animationResId = R.raw.home,
        route = MainRoutes.HOME
    ),
    CATEGORY(
        titleTextId = R.string.category,
        animationResId = R.raw.category,
        route = MainRoutes.CATEGORY
    ),
    CART(
        titleTextId = R.string.cart,
        animationResId = R.raw.cart,
        route = MainRoutes.CART
    ),
    ME(
        titleTextId = R.string.me,
        animationResId = R.raw.me,
        route = MainRoutes.ME
    )
}