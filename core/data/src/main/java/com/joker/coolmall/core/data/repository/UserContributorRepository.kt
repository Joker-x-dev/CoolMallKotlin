package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.UserContributor
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.usercontributor.UserContributorNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 用户贡献者相关仓库
 *
 * @param userContributorNetworkDataSource 用户贡献者网络数据源
 * @author Joker.X
 */
class UserContributorRepository @Inject constructor(
    private val userContributorNetworkDataSource: UserContributorNetworkDataSource
) {
    /**
     * 分页查询用户贡献者
     *
     * @param params 分页请求参数
     * @return 用户贡献者分页数据Flow
     * @author Joker.X
     */
    fun getUserContributorPage(params: PageRequest): Flow<NetworkResponse<NetworkPageData<UserContributor>>> =
        flow {
            emit(userContributorNetworkDataSource.getUserContributorPage(params))
        }.flowOn(Dispatchers.IO)
}
