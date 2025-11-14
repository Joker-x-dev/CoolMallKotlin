package com.joker.coolmall.feature.goods.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.goods.view.GoodsCategoryRoute
import com.joker.coolmall.navigation.routes.GoodsRoutes

/**
 * 商品分类页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.goodsCategoryScreen() {
    composable<GoodsRoutes.Category> {
        GoodsCategoryRoute()
    }
}
