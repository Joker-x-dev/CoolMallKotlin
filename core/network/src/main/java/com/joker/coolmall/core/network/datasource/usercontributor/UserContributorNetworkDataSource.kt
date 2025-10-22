package com.joker.coolmall.core.network.datasource.usercontributor

import com.joker.coolmall.core.model.entity.UserContributor
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 用户贡献者网络数据源接口
 *
 * @author Joker.X
 */
interface UserContributorNetworkDataSource {

    /**
     * 分页查询用户贡献者
     *
     * @param params 请求参数，包含分页信息
     * @return 用户贡献者分页列表响应数据
     * @author Joker.X
     */
    suspend fun getUserContributorPage(params: PageRequest): NetworkResponse<NetworkPageData<UserContributor>>
}
