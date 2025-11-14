package com.joker.coolmall.feature.feedback.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.CommonRepository
import com.joker.coolmall.core.data.repository.FeedbackRepository
import com.joker.coolmall.core.data.repository.FileUploadRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.DictItem
import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.request.FeedbackSubmitRequest
import com.joker.coolmall.core.model.response.DictDataResponse
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.util.log.LogUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.feedback.R
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.RefreshResultKey
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 提交反馈 ViewModel
 *
 * @param context 应用上下文
 * @param navigator 导航器
 * @param appState 应用状态
 * @param commonRepository 通用数据仓库
 * @param feedbackRepository 反馈仓库
 * @param fileUploadRepository 文件上传仓库
 * @author Joker.X
 */
@HiltViewModel
class FeedbackSubmitViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    navigator: AppNavigator,
    appState: AppState,
    private val commonRepository: CommonRepository,
    private val feedbackRepository: FeedbackRepository,
    private val fileUploadRepository: FileUploadRepository
) : BaseNetWorkViewModel<DictDataResponse>(navigator, appState) {

    /**
     * 选中的反馈类型
     */
    private val _selectedFeedbackType = MutableStateFlow<DictItem?>(null)
    val selectedFeedbackType: StateFlow<DictItem?> = _selectedFeedbackType.asStateFlow()

    /**
     * 联系方式
     */
    private val _contact = MutableStateFlow("")
    val contact: StateFlow<String> = _contact.asStateFlow()

    /**
     * 反馈内容
     */
    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    /**
     * 选择的图片列表
     */
    private val _selectedImages = MutableStateFlow<List<Uri>>(emptyList())
    val selectedImages: StateFlow<List<Uri>> = _selectedImages.asStateFlow()

    /**
     * 上传的图片URL列表
     */
    private val _uploadedImageUrls = MutableStateFlow<List<String>>(emptyList())
    val uploadedImageUrls: StateFlow<List<String>> = _uploadedImageUrls.asStateFlow()

    /**
     * 提交状态
     */
    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting.asStateFlow()

    init {
        super.executeRequest()
    }

    /**
     * 选择反馈类型
     *
     * @param feedbackType 反馈类型
     * @author Joker.X
     */
    fun selectFeedbackType(feedbackType: DictItem) {
        _selectedFeedbackType.value = feedbackType
    }

    /**
     * 更新联系方式
     *
     * @param contact 联系方式
     * @author Joker.X
     */
    fun updateContact(contact: String) {
        _contact.value = contact
    }

    /**
     * 更新反馈内容
     *
     * @param content 反馈内容
     * @author Joker.X
     */
    fun updateContent(content: String) {
        _content.value = content
    }

    /**
     * 更新选择的图片列表
     *
     * @param images 图片列表
     * @author Joker.X
     */
    fun updateSelectedImages(images: List<Uri>) {
        _selectedImages.value = images
        // 清空之前上传的URL
        _uploadedImageUrls.value = emptyList()
    }

    /**
     * 检查表单是否有效
     *
     * @return 表单是否有效
     * @author Joker.X
     */
    fun isFormValid(): Boolean {
        return _selectedFeedbackType.value != null && _content.value.isNotBlank()
    }

    /**
     * 按钮点击处理方法
     *
     * @author Joker.X
     */
    fun onSubmitButtonClick() {
        // 表单验证
        if (!isFormValid()) {
            ToastUtils.showError(R.string.please_select_type_and_fill_content)
            return
        }

        // 防止重复提交
        if (_isSubmitting.value) {
            ToastUtils.showWarning(R.string.submitting)
            LogUtils.w("正在提交中，请勿重复提交")
            return
        }

        // 设置提交状态
        _isSubmitting.value = true

        // 开始上传图片并提交流程
        uploadImagesAndSubmit()
    }

    /**
     * 上传图片并提交反馈
     *
     * @author Joker.X
     */
    private fun uploadImagesAndSubmit() {
        val imagesToUpload = _selectedImages.value
        if (imagesToUpload.isEmpty()) {
            // 没有图片直接提交
            submitFeedbackToServer()
            return
        }

        LogUtils.d("开始上传 ${imagesToUpload.size} 张图片")

        viewModelScope.launch {
            fileUploadRepository.uploadImages(imagesToUpload).collect { result ->
                if (result.isSucceeded && result.data != null) {
                    _uploadedImageUrls.value = result.data ?: emptyList()

                    // 打印上传成功的图片URL列表
                    LogUtils.d("图片上传成功！上传的图片URL列表: ${result.data}")
                    LogUtils.d("共上传 ${result.data?.size ?: 0} 张图片")

                    // 图片上传成功后自动提交反馈
                    submitFeedbackToServer()
                } else {
                    _isSubmitting.value = false
                    LogUtils.e("图片上传失败: ${result.message}")
                }
            }
        }
    }

    /**
     * 提交反馈到服务器
     *
     * @author Joker.X
     */
    private fun submitFeedbackToServer() {
        // 创建提交请求
        val request = FeedbackSubmitRequest(
            contact = _contact.value,
            type = _selectedFeedbackType.value?.value ?: 0,
            content = _content.value,
            images = _uploadedImageUrls.value.ifEmpty { null }
        )

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = feedbackRepository.submitFeedback(request).asResult(),
            onData = { success ->
                ToastUtils.showSuccess(R.string.feedback_submit_success)
                // 使用 NavigationResult 回传刷新信号，通知反馈列表刷新
                popBackStackWithResult(RefreshResultKey, true)
            },
            onFinally = { _isSubmitting.value = false }
        )
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     *
     * @return 字典数据的Flow
     * @author Joker.X
     */
    override fun requestApiFlow(): Flow<NetworkResponse<DictDataResponse>> {
        return commonRepository.getDictData(
            DictDataRequest(
                types = listOf("feedbackType")
            )
        )
    }
}