package com.joker.coolmall.feature.goods.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.goods.view.GoodsDetailRoute
import com.joker.coolmall.navigation.routes.GoodsRoutes

/**
 * 商品详情页面路由常量
 */
object GoodsDetailRoutes {
    const val GOODS_ID_ARG = "goods_id"

    /**
     * 带参数的路由模式
     */
    const val GOODS_DETAIL_PATTERN = "${GoodsRoutes.DETAIL}/{$GOODS_ID_ARG}"
}

/**
 * 注册商品详情页面路由
 */
fun NavGraphBuilder.goodsDetailScreen() {
    composable(
        route = GoodsDetailRoutes.GOODS_DETAIL_PATTERN,
        arguments = listOf(navArgument(GoodsDetailRoutes.GOODS_ID_ARG) {
            type = NavType.LongType
        })
    ) { backStackEntry ->
        GoodsDetailRoute()
    }
}
