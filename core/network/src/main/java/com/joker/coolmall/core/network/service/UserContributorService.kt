package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.UserContributor
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 用户贡献者相关接口
 *
 * @author Joker.X
 */
interface UserContributorService {

    /**
     * 分页查询用户贡献者
     *
     * @param params 分页请求参数
     * @return 用户贡献者分页数据响应
     * @author Joker.X
     */
    @POST("user/contributor/page")
    suspend fun getUserContributorPage(@Body params: PageRequest): NetworkResponse<NetworkPageData<UserContributor>>
}
