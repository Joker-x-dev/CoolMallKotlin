package com.joker.coolmall.feature.main.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.navigation.routes.MainRoutes

enum class TopLevelDestination(
    @StringRes val titleTextId: Int,
    @RawRes val animationResId: Int,
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