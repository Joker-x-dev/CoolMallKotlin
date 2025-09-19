package com.joker.coolmall.feature.feedback.view

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.model.entity.DictItem
import com.joker.coolmall.core.model.response.DictDataResponse
import com.joker.coolmall.core.ui.component.bottombar.AppBottomButton
import com.joker.coolmall.core.ui.component.card.AppCard
import com.joker.coolmall.core.ui.component.imagepicker.ImageGridPicker
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.feedback.viewmodel.FeedbackSubmitViewModel

/**
 * 提交反馈路由
 *
 * @param viewModel 提交反馈 ViewModel
 */
@Composable
internal fun FeedbackSubmitRoute(
    viewModel: FeedbackSubmitViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedFeedbackType by viewModel.selectedFeedbackType.collectAsState()
    val contact by viewModel.contact.collectAsState()
    val content by viewModel.content.collectAsState()
    val selectedImages by viewModel.selectedImages.collectAsState()
    val uploadedImageUrls by viewModel.uploadedImageUrls.collectAsState()
    val isSubmitting by viewModel.isSubmitting.collectAsState()

    FeedbackSubmitScreen(
        uiState = uiState,
        selectedFeedbackType = selectedFeedbackType,
        contact = contact,
        content = content,
        selectedImages = selectedImages,
        uploadedImageUrls = uploadedImageUrls,
        isSubmitting = isSubmitting,
        isFormValid = viewModel.isFormValid(),
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
        onFeedbackTypeSelected = viewModel::selectFeedbackType,
        onContactChange = viewModel::updateContact,
        onContentChange = viewModel::updateContent,
        onImagesChange = viewModel::updateSelectedImages,
        onSubmitFeedback = viewModel::onSubmitButtonClick
    )
}

/**
 * 提交反馈界面
 *
 * @param uiState UI状态
 * @param selectedFeedbackType 选中的反馈类型
 * @param contact 联系方式
 * @param content 反馈内容
 * @param selectedImages 选中的图片
 * @param uploadedImageUrls 已上传的图片URL列表
 * @param isSubmitting 是否正在提交
 * @param isFormValid 表单是否有效
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 * @param onFeedbackTypeSelected 反馈类型选择回调
 * @param onContactChange 联系方式变化回调
 * @param onContentChange 反馈内容变化回调
 * @param onImagesChange 图片变化回调
 * @param onSubmitFeedback 提交反馈回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedbackSubmitScreen(
    uiState: BaseNetWorkUiState<DictDataResponse> = BaseNetWorkUiState.Loading,
    selectedFeedbackType: DictItem? = null,
    contact: String = "",
    content: String = "",
    selectedImages: List<Uri> = emptyList(),
    uploadedImageUrls: List<String> = emptyList(),
    isSubmitting: Boolean = false,
    isFormValid: Boolean = true,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onFeedbackTypeSelected: (DictItem) -> Unit = {},
    onContactChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
    onImagesChange: (List<Uri>) -> Unit = {},
    onSubmitFeedback: () -> Unit = {},
) {
    AppScaffold(
        titleText = "我要反馈",
        useLargeTopBar = true,
        onBackClick = onBackClick,
        bottomBar = {
            if (uiState is BaseNetWorkUiState.Success) {
                AppBottomButton(
                    text = "提交",
                    onClick = onSubmitFeedback,
                    enabled = isFormValid && !isSubmitting,
                    loading = isSubmitting
                )
            }
        }
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            FeedbackSubmitContentView(
                feedbackType = it.feedbackType!!,
                selectedFeedbackType = selectedFeedbackType,
                contact = contact,
                content = content,
                selectedImages = selectedImages,
                uploadedImageUrls = uploadedImageUrls,
                onFeedbackTypeSelected = onFeedbackTypeSelected,
                onContactChange = onContactChange,
                onContentChange = onContentChange,
                onImagesChange = onImagesChange,
            )
        }
    }
}

/**
 * 提交反馈内容视图
 *
 * @param feedbackType 反馈类型列表
 * @param selectedFeedbackType 选中的反馈类型
 * @param contact 联系方式
 * @param content 反馈内容
 * @param selectedImages 选中的图片
 * @param uploadedImageUrls 已上传的图片URL列表
 * @param onFeedbackTypeSelected 反馈类型选择回调
 * @param onContactChange 联系方式变化回调
 * @param onContentChange 反馈内容变化回调
 * @param onImagesChange 图片变化回调
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FeedbackSubmitContentView(
    feedbackType: List<DictItem>,
    selectedFeedbackType: DictItem? = null,
    contact: String,
    content: String,
    selectedImages: List<Uri>,
    uploadedImageUrls: List<String>,
    onFeedbackTypeSelected: (DictItem) -> Unit = {},
    onContactChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
    onImagesChange: (List<Uri>) -> Unit = {},
) {
    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        AppCard(lineTitle = "反馈类型") {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall),
                verticalArrangement = Arrangement.spacedBy(SpaceVerticalSmall),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                feedbackType.forEach { item ->
                    FeedbackTypeChip(
                        dictItem = item,
                        isSelected = selectedFeedbackType?.value == item.value,
                        onClick = { onFeedbackTypeSelected(item) }
                    )
                }
            }
        }

        AppCard(lineTitle = "反馈内容") {
            OutlinedTextField(
                value = content,
                onValueChange = onContentChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                placeholder = {
                    AppText(
                        "请尽量详细描述遇到的问题，操作步骤，并附上相关页面截图，以便更好定位问题",
                        type = TextType.TERTIARY
                    )
                },
                shape = ShapeMedium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                minLines = 3,
                maxLines = 5
            )
        }

        AppCard(lineTitle = "联系方式（选填）") {
            OutlinedTextField(
                value = contact,
                onValueChange = onContactChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                placeholder = {
                    AppText(
                        "QQ/微信号，手机号，邮箱",
                        type = TextType.TERTIARY
                    )
                },
                shape = ShapeMedium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }

        AppCard(lineTitle = "问题截图（选填）") {
            ImageGridPicker(
                selectedImages = selectedImages,
                onImagesChanged = onImagesChange,
                maxImages = 9,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/**
 * 反馈类型选择芯片组件
 *
 * @param dictItem 字典项数据
 * @param isSelected 是否选中
 * @param onClick 点击回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedbackTypeChip(
    dictItem: DictItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        onClick = onClick,
        label = {
            dictItem.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        selected = isSelected,
        modifier = Modifier.height(32.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
    )
}

/**
 * 提交反馈界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun FeedbackSubmitScreenPreview() {
    AppTheme {
        FeedbackSubmitScreen(
            uiState = BaseNetWorkUiState.Success(
                data = DictDataResponse()
            )
        )
    }
}

/**
 * 提交反馈界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun FeedbackSubmitScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        FeedbackSubmitScreen(
            uiState = BaseNetWorkUiState.Success(
                data = DictDataResponse()
            )
        )
    }
}