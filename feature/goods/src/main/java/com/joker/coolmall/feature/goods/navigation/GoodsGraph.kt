package com.joker.coolmall.feature.goods.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 商品模块导航图
 *
 * @param navController 导航控制器
 * @author Joker.X
 */
fun NavGraphBuilder.goodsGraph(navController: NavHostController) {
    goodsDetailScreen()
    goodsSearchScreen()
    goodsCommentScreen()
    goodsCategoryScreen()
}