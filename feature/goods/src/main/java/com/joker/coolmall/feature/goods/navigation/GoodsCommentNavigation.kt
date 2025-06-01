package com.joker.coolmall.feature.goods.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.goods.view.GoodsCommentRoute
import com.joker.coolmall.navigation.routes.GoodsRoutes

/**
 * 商品评论页面导航
 */
fun NavGraphBuilder.goodsCommentScreen() {
    composable(route = GoodsRoutes.COMMENT) {
        GoodsCommentRoute()
    }
}