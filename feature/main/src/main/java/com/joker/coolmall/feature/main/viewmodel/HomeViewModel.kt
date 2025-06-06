package com.joker.coolmall.feature.main.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.PageRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 首页ViewModel
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val pageRepository: PageRepository,
) : BaseNetWorkViewModel<Home>(
    navigator = navigator,
    appState = appState
) {
    init {
        super.executeRequest()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     */
    override fun requestApiFlow(): Flow<NetworkResponse<Home>> {
        return pageRepository.getHomeData()
    }

    /**
     * 导航到商品详情页
     */
    fun toGoodsDetail(goodsId: Long) {
        super.toPage(GoodsRoutes.DETAIL, goodsId)
    }

    /**
     * 跳转到商品分类页面
     * @param categoryId 点击的分类ID
     */
    fun toGoodsCategoryPage(categoryId: Long) {
        val data = super.getSuccessData()
        val childCategoryIds =
            findChildCategoryIds(categoryId, data.categoryAll ?: emptyList())
        // 如果有子分类，传递子分类ID
        val typeIdParam = childCategoryIds.joinToString(",")
        super.toPage("${GoodsRoutes.CATEGORY}?type_id=$typeIdParam")
    }

    /**
     * 跳转到限时精选页面
     */
    fun toFlashSalePage() {
        super.toPage("${GoodsRoutes.CATEGORY}?featured=true")
    }

    /**
     * 跳转到推荐商品页面
     */
    fun toRecommendPage() {
        super.toPage("${GoodsRoutes.CATEGORY}?recommend=true")
    }

    /**
     * 查找指定分类的所有子分类ID
     * @param parentId 父分类ID
     * @param allCategories 所有分类列表
     * @return 子分类ID列表
     */
    private fun findChildCategoryIds(parentId: Long, allCategories: List<Category>): List<Long> {
        return allCategories.filter { it.parentId == parentId.toInt() }.map { it.id }
    }
}
