package com.joker.coolmall.feature.order.viewmodel

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.FileUploadRepository
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Comment
import com.joker.coolmall.core.model.request.GoodsCommentSubmitRequest
import com.joker.coolmall.core.util.log.LogUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.RefreshResultKey
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 订单评价 ViewModel
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param savedStateHandle 保存状态句柄
 * @param fileUploadRepository 文件上传仓库
 * @param goodsRepository 商品仓库
 * @author Joker.X
 */
@HiltViewModel
class OrderCommentViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    savedStateHandle: SavedStateHandle,
    private val fileUploadRepository: FileUploadRepository,
    private val goodsRepository: GoodsRepository,
) : BaseViewModel(
    navigator = navigator,
    appState = appState,
) {

    // 订单评价路由参数
    private val orderCommentRoute = savedStateHandle.toRoute<OrderRoutes.Comment>()

    // 评价内容
    private val _commentContent = MutableStateFlow("")
    val commentContent: StateFlow<String> = _commentContent.asStateFlow()

    // 评分
    private val _rating = MutableStateFlow(5)
    val rating: StateFlow<Int> = _rating.asStateFlow()

    // 选择的图片列表
    private val _selectedImages = MutableStateFlow<List<Uri>>(emptyList())
    val selectedImages: StateFlow<List<Uri>> = _selectedImages.asStateFlow()

    // 上传的图片URL列表
    private val _uploadedImageUrls = MutableStateFlow<List<String>>(emptyList())
    val uploadedImageUrls: StateFlow<List<String>> = _uploadedImageUrls.asStateFlow()

    // 提交状态
    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting.asStateFlow()

    /**
     * 更新评价内容
     * @param content 评价内容
     */
    fun updateCommentContent(content: String) {
        _commentContent.value = content
    }

    /**
     * 更新评分
     * @param score 评分
     */
    fun updateRating(score: Int) {
        _rating.value = score
    }

    /**
     * 检查表单是否有效
     * @return 表单是否有效
     */
    fun isFormValid(): Boolean {
        return _rating.value > 0 && _commentContent.value.isNotBlank()
    }

    /**
     * 更新选择的图片列表
     * @param images 图片列表
     */
    fun updateSelectedImages(images: List<Uri>) {
        _selectedImages.value = images
        // 清空之前上传的URL
        _uploadedImageUrls.value = emptyList()
    }

    /**
     * 上传图片并提交评价
     */
    private fun uploadImagesAndSubmit() {
        val imagesToUpload = _selectedImages.value
        if (imagesToUpload.isEmpty()) {
            // 没有图片直接提交
            submitCommentToServer()
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

                    // 图片上传成功后自动提交评价
                    submitCommentToServer()
                } else {
                    _isSubmitting.value = false
                    LogUtils.e("图片上传失败: ${result.message}")
                }
            }
        }
    }

    /**
     * 按钮点击处理方法
     *
     * @author Joker.X
     */
    fun onSubmitButtonClick() {
        // 表单验证
        if (!isFormValid()) {
            ToastUtils.showError(R.string.please_fill_comment)
            LogUtils.w("表单验证失败：评分=${_rating.value}, 内容长度=${_commentContent.value.length}")
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
     * 提交评价到服务器
     *
     * @author Joker.X
     */
    private fun submitCommentToServer() {
        // 创建提交请求
        val request = GoodsCommentSubmitRequest(
            orderId = orderCommentRoute.orderId, data = Comment(
                orderId = orderCommentRoute.orderId,
                goodsId = orderCommentRoute.goodsId,
                content = _commentContent.value,
                starCount = _rating.value,
                pics = _uploadedImageUrls.value.ifEmpty { null })
        )

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = goodsRepository.submitGoodsComment(request).asResult(),
            onData = { _ ->
                ToastUtils.showSuccess(R.string.comment_submit_success)
                LogUtils.d("评价提交成功")
                // 使用 NavigationResult 回传刷新信号，通知上一个页面刷新
                popBackStackWithResult(RefreshResultKey, true)
            },
            onFinally = { _isSubmitting.value = false })
    }
}