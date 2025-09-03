package com.joker.coolmall.feature.goods.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.goods.view.GoodsCommentRoute
import com.joker.coolmall.navigation.routes.GoodsRoutes

/**
 * 商品评论页面路由常量
 *
 * @author Joker.X
 */
object GoodsCommentRoutes {
    const val GOODS_ID_ARG = "goods_id"

    /**
     * 带参数的路由模式
     */
    const val GOODS_COMMENT_PATTERN = "${GoodsRoutes.COMMENT}/{$GOODS_ID_ARG}"
}

/**
 * 注册商品评论页面路由
 *
 * @author Joker.X
 */
fun NavGraphBuilder.goodsCommentScreen() {
    composable(
        route = GoodsCommentRoutes.GOODS_COMMENT_PATTERN,
        arguments = listOf(navArgument(GoodsCommentRoutes.GOODS_ID_ARG) {
            type = NavType.LongType
        })
    ) { backStackEntry ->
        GoodsCommentRoute()
    }
}