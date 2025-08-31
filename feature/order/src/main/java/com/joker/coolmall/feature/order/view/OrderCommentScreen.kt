package com.joker.coolmall.feature.order.view

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXXLarge
import com.joker.coolmall.core.model.entity.MediaItem
import com.joker.coolmall.core.ui.component.bottombar.AppBottomButton
import com.joker.coolmall.core.ui.component.rate.WeRate
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.feature.order.viewmodel.OrderCommentViewModel

/**
 * 订单评价路由
 *
 * @param viewModel 订单评价 ViewModel
 */
@Composable
internal fun OrderCommentRoute(
    viewModel: OrderCommentViewModel = hiltViewModel()
) {
    OrderCommentScreen(
        onBackClick = viewModel::navigateBack,
    )
}

/**
 * 订单评价界面
 *
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderCommentScreen(
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    var ratingValue by remember { mutableIntStateOf(0) }
    var commentText by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf<List<MediaItem>>(emptyList()) }

    AppScaffold(
        titleText = "商品评价",
        useLargeTopBar = true,
        onBackClick = onBackClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
        largeTopBarExpandedBackgroundColor = MaterialTheme.colorScheme.surface,
        largeTopBarCollapsedBackgroundColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            AppBottomButton(
                text = "提交评价",
                onClick = {}
            )
        }
    ) {
        OrderCommentContentView(
            ratingValue = ratingValue,
            commentText = commentText,
            selectedImages = selectedImages,
            onRatingChange = { ratingValue = it },
            onCommentChange = { commentText = it },
            onImagesChange = { selectedImages = it }
        )
    }
}

/**
 * 订单评价内容视图
 */
@Composable
private fun OrderCommentContentView(
    ratingValue: Int,
    commentText: String,
    selectedImages: List<MediaItem>,
    onRatingChange: (Int) -> Unit,
    onCommentChange: (String) -> Unit,
    onImagesChange: (List<MediaItem>) -> Unit
) {
    VerticalList(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        SpaceVerticalXXLarge()
        // 评分
        WeRate(
            value = ratingValue,
            count = 5,
            onChange = onRatingChange,
        )
        AppText(
            text = when (ratingValue) {
                0 -> "请点击星星进行评分"
                1 -> "很差"
                2 -> "较差"
                3 -> "一般"
                4 -> "满意"
                5 -> "非常满意"
                else -> "请点击星星进行评分"
            },
        )

        SpaceVerticalXSmall()

        // 评价内容输入框
        TextField(
            value = commentText,
            onValueChange = onCommentChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = ShapeMedium
                ),
            placeholder = { Text("请输入您的评价内容...") },
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

        SpaceVerticalXSmall()

        // 图片选择区域
        AppText(
            text = "添加图片",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )

        SpaceVerticalXSmall()

    }
}

/**
 * 订单评价界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun OrderCommentScreenPreview() {
    AppTheme {
        OrderCommentScreen()
    }
}

/**
 * 订单评价界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun OrderCommentScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderCommentScreen()
    }
}