package com.joker.coolmall.feature.feedback.viewmodel

import android.content.Context
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
import com.joker.coolmall.feature.feedback.R
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.FeedbackRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * 反馈列表 ViewModel
 *
 * @param context 应用上下文
 * @param navigator 导航器
 * @param appState 应用状态
 * @param feedbackRepository 反馈仓库
 * @param commonRepository 通用数据仓库
 * @author Joker.X
 */
@HiltViewModel
class FeedbackListViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
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
     * @author Joker.X
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
     *
     * @author Joker.X
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
     * @author Joker.X
     */
    fun getFeedbackTypeName(typeValue: Int?): String {
        val unknownType = context.getString(R.string.unknown_type)
        if (typeValue == null) return unknownType
        return _feedbackTypes.value.find { it.value == typeValue }?.name ?: unknownType
    }

    /**
     * 导航到提交反馈页面
     *
     * @author Joker.X
     */
    fun toFeedbackSubmitPage() {
        navigate(FeedbackRoutes.Submit)
    }
}