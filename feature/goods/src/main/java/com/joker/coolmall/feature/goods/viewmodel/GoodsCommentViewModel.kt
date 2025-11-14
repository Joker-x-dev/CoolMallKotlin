package com.joker.coolmall.feature.goods.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.data.state.AppState
import androidx.navigation.toRoute
import com.joker.coolmall.core.model.entity.Comment
import com.joker.coolmall.core.model.request.GoodsCommentPageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 商品评论 ViewModel
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param savedStateHandle 保存的状态句柄
 * @param goodsRepository 商品仓库
 * @author Joker.X
 */
@HiltViewModel
class GoodsCommentViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    savedStateHandle: SavedStateHandle,
    private val goodsRepository: GoodsRepository,
) : BaseNetWorkListViewModel<Comment>(navigator, appState) {

    /**
     * 商品ID
     */
    private val goodsId = savedStateHandle.toRoute<GoodsRoutes.Comment>().goodsId

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
}