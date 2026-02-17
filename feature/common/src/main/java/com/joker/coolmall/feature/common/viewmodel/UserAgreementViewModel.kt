package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.CommonRepository
import com.joker.coolmall.core.model.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 用户协议 ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class UserAgreementViewModel @Inject constructor(
    private val commonRepository: CommonRepository
) : BaseNetWorkViewModel<String>() {

    init {
        super.executeRequest()
    }

    /**
     * 请求用户协议数据
     *
     * @return 用户协议HTML内容的Flow
     * @author Joker.X
     */
    override fun requestApiFlow(): Flow<NetworkResponse<String>> {
        return commonRepository.getParam("userAgreement")
    }
}