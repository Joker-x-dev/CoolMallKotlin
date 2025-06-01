package com.joker.coolmall.feature.goods.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.goods.view.GoodsSearchRoute
import com.joker.coolmall.navigation.routes.GoodsRoutes

/**
 * 商品搜索页面导航
 */
fun NavGraphBuilder.goodsSearchScreen() {
    composable(route = GoodsRoutes.SEARCH) {
        GoodsSearchRoute()
    }
}