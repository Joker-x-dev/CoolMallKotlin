package com.joker.coolmall.feature.goods.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.model.entity.Comment
import com.joker.coolmall.core.model.request.GoodsCommentPageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.navigation.goods.GoodsRoutes
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

/**
 * 商品评论 ViewModel
 *
 * @param navKey 路由参数
 * @param goodsRepository 商品仓库
 * @author Joker.X
 */
@HiltViewModel(assistedFactory = GoodsCommentViewModel.Factory::class)
class GoodsCommentViewModel @AssistedInject constructor(
    @Assisted navKey: GoodsRoutes.Comment,
    private val goodsRepository: GoodsRepository,
) : BaseNetWorkListViewModel<Comment>() {

    /**
     * 商品ID
     */
    private val goodsId = navKey.goodsId

    init {
        initLoad()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     *
     * @return 商品评论分页数据的Flow
     */
    override fun requestListData(): Flow<NetworkResponse<NetworkPageData<Comment>>> {
        return goodsRepository.getGoodsCommentPage(
            GoodsCommentPageRequest(
                goodsId = goodsId.toString(),
                page = super.currentPage,
                size = super.pageSize
            )
        )
    }

    /**
     * Assisted Factory
     *
     * @author Joker.X
     */
    @AssistedFactory
    interface Factory {
        /**
         * 创建 ViewModel 实例
         *
         * @param navKey 路由参数
         * @return ViewModel 实例
         * @author Joker.X
         */
        fun create(navKey: GoodsRoutes.Comment): GoodsCommentViewModel
    }
}
