package com.joker.coolmall.feature.goods.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.goods.view.GoodsDetailRoute
import com.joker.coolmall.navigation.routes.GoodsRoutes

/**
 * 注册商品详情页面路由
 *
 * @author Joker.X
 */
fun NavGraphBuilder.goodsDetailScreen() {
    composable<GoodsRoutes.Detail> {
        GoodsDetailRoute()
    }
}
