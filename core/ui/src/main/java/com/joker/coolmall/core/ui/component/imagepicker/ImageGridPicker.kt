package com.joker.coolmall.core.ui.component.imagepicker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 图片九宫格选择器组件
 *
 * @param selectedImages 已选择的图片列表
 * @param onImagesChanged 图片列表变化回调
 * @param maxImages 最大选择图片数量，默认9张
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
fun ImageGridPicker(
    selectedImages: List<Uri>,
    onImagesChanged: (List<Uri>) -> Unit,
    maxImages: Int = 9,
    modifier: Modifier = Modifier,
) {
    // 图片选择器启动器
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { newUri ->
            if (selectedImages.size < maxImages) {
                onImagesChanged(selectedImages + newUri)
            }
        }
    }

    // 创建包含图片和添加按钮的完整列表
    val allItems: List<Uri?> = selectedImages.toMutableList<Uri?>().apply {
        if (size < maxImages) {
            add(null) // null 表示添加按钮
        }
    }

    // 将项目按每行3个分组
    val rows = allItems.chunked(3)
    val spacing = 8.dp

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                rowItems.forEach { item ->
                    AnimatedVisibility(
                        visible = true,
                        enter = scaleIn(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        ) + fadeIn(),
                        exit = scaleOut() + fadeOut(),
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {
                        if (item != null) {
                            // 显示图片
                            ImageItem(
                                imageUri = item,
                                onRemove = {
                                    onImagesChanged(selectedImages - item)
                                }
                            )
                        } else {
                            // 显示添加按钮
                            AddImageButton(
                                onClick = {
                                    imagePickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                            )
                        }
                    }
                }

                // 填充空白位置以保持对齐
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * 图片项组件
 *
 * @param imageUri 图片URI
 * @param onRemove 删除回调
 * @author Joker.X
 */
@Composable
private fun ImageItem(
    imageUri: Uri,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(ShapeSmall)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // 图片
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.selected_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 删除按钮
        Box(
            modifier = Modifier
                .padding(SpacePaddingXSmall)
                .align(Alignment.TopEnd)
                .size(22.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = ShapeCircle
                )
                .clip(ShapeCircle)
                .clickable { onRemove() },
            contentAlignment = Alignment.Center
        ) {
            CommonIcon(
                resId = R.drawable.ic_close,
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

/**
 * 添加图片按钮
 *
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
private fun AddImageButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(ShapeSmall)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = ShapeSmall
            )
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(id = R.string.add_image),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.size(32.dp)
            )
            SpaceVerticalXSmall()
            AppText(
                text = stringResource(id = R.string.add_image),
                type = TextType.TERTIARY,
            )
        }
    }
}

/**
 * ImageGridPicker 组件预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun ImageGridPickerPreview() {
    AppTheme {
        ImageGridPicker(
            selectedImages = emptyList(),
            onImagesChanged = { },
            maxImages = 9
        )
    }
}

/**
 * ImageGridPicker 组件预览 - 包含图片
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun ImageGridPickerWithImagesPreview() {
    AppTheme {
        // 创建模拟的 URI 列表用于预览
        val mockUris = listOf(
            Uri.parse("content://media/external/images/media/1"),
            Uri.parse("content://media/external/images/media/2"),
            Uri.parse("content://media/external/images/media/3")
        )

        ImageGridPicker(
            selectedImages = mockUris,
            onImagesChanged = { },
            maxImages = 9
        )
    }
}