package com.joker.coolmall.core.network.datasource.usercontributor

import com.joker.coolmall.core.model.entity.UserContributor
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.service.UserContributorService
import javax.inject.Inject

/**
 * 用户贡献者网络数据源实现类
 *
 * @param userContributorService 用户贡献者服务接口
 * @author Joker.X
 */
class UserContributorNetworkDataSourceImpl @Inject constructor(
    private val userContributorService: UserContributorService
) : UserContributorNetworkDataSource {

    /**
     * 分页查询用户贡献者
     *
     * @param params 请求参数，包含分页信息
     * @return 用户贡献者分页列表响应数据
     * @author Joker.X
     */
    override suspend fun getUserContributorPage(params: PageRequest): NetworkResponse<NetworkPageData<UserContributor>> {
        return userContributorService.getUserContributorPage(params)
    }
}
