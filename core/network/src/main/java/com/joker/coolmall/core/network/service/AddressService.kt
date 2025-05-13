package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.common.Id
import com.joker.coolmall.core.model.common.Ids
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 用户地址相关接口
 */
interface AddressService {

    /**
     * 修改地址
     */
    @POST("user/address/update")
    suspend fun updateAddress(@Body params: Address): NetworkResponse<Unit>

    /**
     * 分页查询地址
     */
    @POST("user/address/page")
    suspend fun getAddressPage(@Body params: PageRequest): NetworkResponse<NetworkPageData<Address>>

    /**
     * 查询地址列表
     */
    @POST("user/address/list")
    suspend fun getAddressList(): NetworkResponse<List<Address>>

    /**
     * 删除地址
     */
    @POST("user/address/delete")
    suspend fun deleteAddress(@Body params: Ids): NetworkResponse<Unit>

    /**
     * 新增地址
     */
    @POST("user/address/add")
    suspend fun addAddress(@Body params: Address): NetworkResponse<Id>

    /**
     * 地址信息
     */
    @GET("user/address/info")
    suspend fun getAddressInfo(@Query("id") id: Long): NetworkResponse<Address>

    /**
     * 默认地址
     */
    @GET("user/address/default")
    suspend fun getDefaultAddress(): NetworkResponse<Address>
} 