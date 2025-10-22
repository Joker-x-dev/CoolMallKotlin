package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.common.Id
import com.joker.coolmall.core.model.common.Ids
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 用户地址相关接口
 *
 * @author Joker.X
 */
interface AddressService {

    /**
     * 修改地址
     *
     * @param params 地址信息
     * @return 修改结果响应
     * @author Joker.X
     */
    @POST("user/address/update")
    suspend fun updateAddress(@Body params: Address): NetworkResponse<Unit>

    /**
     * 分页查询地址
     *
     * @param params 分页请求参数
     * @return 地址分页数据响应
     * @author Joker.X
     */
    @POST("user/address/page")
    suspend fun getAddressPage(@Body params: PageRequest): NetworkResponse<NetworkPageData<Address>>

    /**
     * 查询地址列表
     *
     * @return 地址列表响应
     * @author Joker.X
     */
    @POST("user/address/list")
    suspend fun getAddressList(): NetworkResponse<List<Address>>

    /**
     * 删除地址
     *
     * @param params 地址ID列表
     * @return 删除结果响应
     * @author Joker.X
     */
    @POST("user/address/delete")
    suspend fun deleteAddress(@Body params: Ids): NetworkResponse<Unit>

    /**
     * 新增地址
     *
     * @param params 地址信息
     * @return 新增地址ID响应
     * @author Joker.X
     */
    @POST("user/address/add")
    suspend fun addAddress(@Body params: Address): NetworkResponse<Id>

    /**
     * 获取地址信息
     *
     * @param id 地址ID
     * @return 地址信息响应
     * @author Joker.X
     */
    @GET("user/address/info")
    suspend fun getAddressInfo(@Query("id") id: Long): NetworkResponse<Address>

    /**
     * 获取默认地址
     *
     * @return 默认地址响应
     * @author Joker.X
     */
    @GET("user/address/default")
    suspend fun getDefaultAddress(): NetworkResponse<Address?>
} 