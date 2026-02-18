package com.joker.coolmall.navigation.goods

import com.joker.coolmall.navigation.navigate

/**
 * 商品模块导航封装
 *
 * @author Joker.X
 */
object GoodsNavigator {

    /**
     * 跳转到商品详情页
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    fun toDetail(goodsId: Long) {
        navigate(GoodsRoutes.Detail(goodsId = goodsId))
    }

    /**
     * 跳转到商品搜索页
     *
     * @author Joker.X
     */
    fun toSearch() {
        navigate(GoodsRoutes.Search)
    }

    /**
     * 跳转到商品分类页
     *
     * @param typeId 类型 ID 列表（逗号分隔）
     * @param featured 是否精选
     * @param recommend 是否推荐
     * @param keyword 关键词
     * @param minPrice 最小金额
     * @author Joker.X
     */
    fun toCategory(
        typeId: String? = null,
        featured: Boolean = false,
        recommend: Boolean = false,
        keyword: String? = null,
        minPrice: String? = null,
    ) {
        navigate(
            GoodsRoutes.Category(
                typeId = typeId,
                featured = featured,
                recommend = recommend,
                keyword = keyword,
                minPrice = minPrice,
            )
        )
    }

    /**
     * 跳转到商品评价页
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    fun toComment(goodsId: Long) {
        navigate(GoodsRoutes.Comment(goodsId = goodsId))
    }
}
