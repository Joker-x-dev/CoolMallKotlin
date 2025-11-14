package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.UserContributorRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.UserContributor
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.CommonRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.net.URLEncoder
import javax.inject.Inject

/**
 * 贡献者列表 ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class ContributorsViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val userContributorRepository: UserContributorRepository,
) : BaseNetWorkListViewModel<UserContributor>(navigator, appState) {

    init {
        initLoad()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     *
     * @return 用户贡献者分页数据的Flow
     * @author Joker.X
     */
    override fun requestListData(): Flow<NetworkResponse<NetworkPageData<UserContributor>>> {
        return userContributorRepository.getUserContributorPage(
            PageRequest(
                page = super.currentPage,
                size = super.pageSize
            )
        )
    }

    /**
     * 点击贡献者卡片
     * 如果贡献者有网站链接，则打开WebView页面显示
     *
     * @param contributor 被点击的贡献者
     * @author Joker.X
     */
    fun onContributorClick(contributor: UserContributor) {
        contributor.websiteUrl?.let { url ->
            if (url.isNotEmpty()) {
                toWebPage(url, contributor.nickName)
            }
        }
    }

    /**
     * 跳转到网页页面
     *
     * @param url 网页URL
     * @param title 页面标题
     * @author Joker.X
     */
    private fun toWebPage(url: String, title: String) {
        navigate(CommonRoutes.Web(url = url, title = title))
    }
}