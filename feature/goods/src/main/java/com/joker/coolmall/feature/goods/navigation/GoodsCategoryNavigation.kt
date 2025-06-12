package com.joker.coolmall.feature.goods.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.goods.view.GoodsCategoryRoute
import com.joker.coolmall.navigation.routes.GoodsRoutes

/**
 * 商品分类页面路由常量
 */
object GoodsCategoryRoutes {
    const val GOODS_TYPE_ID_ARG = "type_id"
    const val GOODS_FEATURED_ARG = "featured"
    const val GOODS_RECOMMEND_ARG = "recommend"
    const val GOODS_KEYWORD_ARG = "keyword"

    /**
     * 带参数的路由模式
     */
    const val GOODS_CATEGORY_PATTERN = "${GoodsRoutes.CATEGORY}?$GOODS_TYPE_ID_ARG={$GOODS_TYPE_ID_ARG}&$GOODS_FEATURED_ARG={$GOODS_FEATURED_ARG}&$GOODS_RECOMMEND_ARG={$GOODS_RECOMMEND_ARG}&$GOODS_KEYWORD_ARG={$GOODS_KEYWORD_ARG}"
}

/**
 * 商品分类页面导航
 */
fun NavGraphBuilder.goodsCategoryScreen() {
    composable(
        route = GoodsCategoryRoutes.GOODS_CATEGORY_PATTERN,
        // 传递一个 string 的 id 数组 例如 typeId = 1,2,3 逗号分隔
        arguments = listOf(
            navArgument(GoodsCategoryRoutes.GOODS_TYPE_ID_ARG) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(GoodsCategoryRoutes.GOODS_FEATURED_ARG) {
                type = NavType.BoolType
                nullable = false
                defaultValue = false
            },
            navArgument(GoodsCategoryRoutes.GOODS_RECOMMEND_ARG) {
                type = NavType.BoolType
                nullable = false
                defaultValue = false
            },
            navArgument(GoodsCategoryRoutes.GOODS_KEYWORD_ARG) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {
        GoodsCategoryRoute()
    }
}