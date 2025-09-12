package com.joker.coolmall.feature.feedback.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.CommonRepository
import com.joker.coolmall.core.data.repository.FeedbackRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.DictItem
import com.joker.coolmall.core.model.entity.Feedback
import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.FeedbackRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 反馈列表 ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class FeedbackListViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val feedbackRepository: FeedbackRepository,
    private val commonRepository: CommonRepository
) : BaseNetWorkListViewModel<Feedback>(navigator, appState) {

    // 反馈类型字典数据
    private val _feedbackTypes = MutableStateFlow<List<DictItem>>(emptyList())

    init {
        initLoad()
        loadFeedbackTypes()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     *
     * @return 反馈分页数据的Flow
     */
    override fun requestListData(): Flow<NetworkResponse<NetworkPageData<Feedback>>> {
        return feedbackRepository.getFeedbackPage(
            PageRequest(
                page = super.currentPage,
                size = super.pageSize
            )
        )
    }

    /**
     * 加载反馈类型字典数据
     */
    fun loadFeedbackTypes() {
        ResultHandler.handleResultWithData(
            showToast = false,
            scope = viewModelScope,
            flow = commonRepository.getDictData(
                DictDataRequest(types = listOf("feedbackType"))
            ).asResult(),
            onData = { data ->
                // 存储字典数据到StateFlow
                _feedbackTypes.value = data.feedbackType ?: emptyList()
            },
        )
    }

    /**
     * 根据反馈类型值获取对应的中文名称
     *
     * @param typeValue 反馈类型值
     * @return 反馈类型中文名称
     */
    fun getFeedbackTypeName(typeValue: Int?): String {
        if (typeValue == null) return "未知类型"
        return _feedbackTypes.value.find { it.value == typeValue }?.name ?: "未知类型"
    }

    /**
     * 导航到提交反馈页面
     */
    fun toFeedbackSubmitPage() {
        toPage(FeedbackRoutes.SUBMIT)
    }
}