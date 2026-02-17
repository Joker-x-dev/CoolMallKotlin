package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.CommonRepository
import com.joker.coolmall.core.model.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 隐私政策 ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class PrivacyPolicyViewModel @Inject constructor(
    private val commonRepository: CommonRepository
) : BaseNetWorkViewModel<String>() {

    init {
        super.executeRequest()
    }

    /**
     * 请求隐私政策数据
     *
     * @return 隐私政策HTML内容的Flow
     * @author Joker.X
     */
    override fun requestApiFlow(): Flow<NetworkResponse<String>> {
        return commonRepository.getParam("privacyPolicy")
    }
}