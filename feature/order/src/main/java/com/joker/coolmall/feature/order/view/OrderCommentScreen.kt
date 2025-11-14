package com.joker.coolmall.feature.order.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXXLarge
import com.joker.coolmall.core.ui.component.bottombar.AppBottomButton
import com.joker.coolmall.core.ui.component.imagepicker.ImageGridPicker
import com.joker.coolmall.core.ui.component.rate.WeRate
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.feature.order.viewmodel.OrderCommentViewModel

/**
 * 订单评价路由
 *
 * @param viewModel 订单评价 ViewModel
 * @author Joker.X
 */
@Composable
internal fun OrderCommentRoute(
    viewModel: OrderCommentViewModel = hiltViewModel()
) {
    // 评分
    val rating by viewModel.rating.collectAsState()
    // 评论内容
    val commentContent by viewModel.commentContent.collectAsState()
    // 选中的图片
    val selectedImages by viewModel.selectedImages.collectAsState()
    // 已上传的图片URL
    val uploadedImageUrls by viewModel.uploadedImageUrls.collectAsState()
    // 是否正在提交
    val isSubmitting by viewModel.isSubmitting.collectAsState()

    OrderCommentScreen(
        rating = rating,
        commentContent = commentContent,
        selectedImages = selectedImages,
        uploadedImageUrls = uploadedImageUrls,
        isSubmitting = isSubmitting,
        isFormValid = viewModel.isFormValid(),
        onBackClick = viewModel::navigateBack,
        onRatingChange = viewModel::updateRating,
        onCommentChange = viewModel::updateCommentContent,
        onImagesChange = viewModel::updateSelectedImages,
        onSubmitComment = viewModel::onSubmitButtonClick
    )
}

/**
 * 订单评价界面
 *
 * @param rating 评分
 * @param commentContent 评论内容
 * @param selectedImages 选中的图片
 * @param uploadedImageUrls 已上传的图片URL列表
 * @param isSubmitting 是否正在提交
 * @param isFormValid 表单是否有效
 * @param onBackClick 返回按钮回调
 * @param onRatingChange 评分变化回调
 * @param onCommentChange 评论内容变化回调
 * @param onImagesChange 图片变化回调
 * @param onSubmitComment 提交评论回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderCommentScreen(
    rating: Int = 5,
    commentContent: String = "",
    selectedImages: List<Uri> = emptyList(),
    uploadedImageUrls: List<String> = emptyList(),
    isSubmitting: Boolean = false,
    isFormValid: Boolean = true,
    onBackClick: () -> Unit = {},
    onRatingChange: (Int) -> Unit = {},
    onCommentChange: (String) -> Unit = {},
    onImagesChange: (List<Uri>) -> Unit = {},
    onSubmitComment: () -> Unit = {},
) {
    AppScaffold(
        titleText = stringResource(R.string.order_comment),
        useLargeTopBar = true,
        onBackClick = onBackClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
        largeTopBarExpandedBackgroundColor = MaterialTheme.colorScheme.surface,
        largeTopBarCollapsedBackgroundColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            AppBottomButton(
                text = stringResource(R.string.submit_comment),
                onClick = onSubmitComment,
                enabled = isFormValid && !isSubmitting,
                loading = isSubmitting
            )
        }
    ) {
        OrderCommentContentView(
            rating = rating,
            commentContent = commentContent,
            selectedImages = selectedImages,
            uploadedImageUrls = uploadedImageUrls,
            onRatingChange = onRatingChange,
            onCommentChange = onCommentChange,
            onImagesChange = onImagesChange,
        )
    }
}

/**
 * 订单评价内容视图
 *
 * @param rating 评分
 * @param commentContent 评论内容
 * @param selectedImages 选中的图片
 * @param uploadedImageUrls 已上传的图片URL列表
 * @param onRatingChange 评分变化回调
 * @param onCommentChange 评论内容变化回调
 * @param onImagesChange 图片变化回调
 * @author Joker.X
 */
@Composable
private fun OrderCommentContentView(
    rating: Int,
    commentContent: String,
    selectedImages: List<Uri>,
    uploadedImageUrls: List<String>,
    onRatingChange: (Int) -> Unit,
    onCommentChange: (String) -> Unit,
    onImagesChange: (List<Uri>) -> Unit,
) {

    VerticalList(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        SpaceVerticalXXLarge()
        // 评分
        WeRate(
            value = rating,
            count = 5,
            onChange = onRatingChange,
        )
        AppText(
            text = when (rating) {
                0 -> stringResource(R.string.please_rate)
                1 -> stringResource(R.string.rating_very_poor)
                2 -> stringResource(R.string.rating_poor)
                3 -> stringResource(R.string.rating_average)
                4 -> stringResource(R.string.rating_good)
                5 -> stringResource(R.string.rating_excellent)
                else -> stringResource(R.string.please_rate)
            },
        )

        SpaceVerticalXSmall()

        // 评价内容输入框
        TextField(
            value = commentContent,
            onValueChange = onCommentChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = ShapeMedium
                ),
            placeholder = { Text(stringResource(R.string.comment_placeholder)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            minLines = 6,
            maxLines = 8
        )

        // 图片选择器
        ImageGridPicker(
            selectedImages = selectedImages,
            onImagesChanged = onImagesChange,
            maxImages = 9
        )

        SpaceVerticalXSmall()

    }
}

/**
 * 订单评价界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun OrderCommentScreenPreview() {
    AppTheme {
        OrderCommentScreen(
            rating = 5,
            commentContent = "这是一个很好的商品",
            selectedImages = emptyList()
        )
    }
}

/**
 * 订单评价界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun OrderCommentScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderCommentScreen()
    }
}