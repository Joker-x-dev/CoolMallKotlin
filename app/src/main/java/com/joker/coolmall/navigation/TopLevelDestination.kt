package com.joker.coolmall.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.joker.coolmall.R
import com.joker.coolmall.feature.cart.navigation.CART_ROUTE
import com.joker.coolmall.feature.category.navigation.CATEGORY_ROUTE
import com.joker.coolmall.feature.home.navigation.HOME_ROUTE
import com.joker.coolmall.feature.me.navigation.ME_ROUTE

enum class TopLevelDestination(
    @DrawableRes val selectedIconId: Int,
    @DrawableRes val unselectedIconId: Int,
    @StringRes val titleTextId: Int,
    val route: String
) {
    HOME(
        selectedIconId = R.drawable.ic_home_fill,
        unselectedIconId = R.drawable.ic_home,
        titleTextId = R.string.home,
        route = HOME_ROUTE
    ),
    CATEGORY(
        selectedIconId = R.drawable.ic_category_fill,
        unselectedIconId = R.drawable.ic_category,
        titleTextId = R.string.category,
        route = CATEGORY_ROUTE
    ),
    CART(
        selectedIconId = R.drawable.ic_cart_fill,
        unselectedIconId = R.drawable.ic_cart,
        titleTextId = R.string.cart,
        route = CART_ROUTE
    ),
    ME(
        selectedIconId = R.drawable.ic_me_fill,
        unselectedIconId = R.drawable.ic_me,
        titleTextId = R.string.me,
        route = ME_ROUTE
    )
}