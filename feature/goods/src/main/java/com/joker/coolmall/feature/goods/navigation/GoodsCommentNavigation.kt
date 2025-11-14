package com.joker.coolmall.feature.goods.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.goods.view.GoodsCommentRoute
import com.joker.coolmall.navigation.routes.GoodsRoutes

/**
 * 注册商品评论页面路由
 *
 * @author Joker.X
 */
fun NavGraphBuilder.goodsCommentScreen() {
    composable<GoodsRoutes.Comment> {
        GoodsCommentRoute()
    }
}
