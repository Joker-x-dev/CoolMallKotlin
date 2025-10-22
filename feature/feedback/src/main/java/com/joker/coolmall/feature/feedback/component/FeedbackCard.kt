package com.joker.coolmall.feature.feedback.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.model.entity.Feedback
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.tag.Tag
import com.joker.coolmall.core.ui.component.tag.TagSize
import com.joker.coolmall.core.ui.component.tag.TagType
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.feedback.R

/**
 * 反馈卡片组件
 *
 * @param modifier 修饰符
 * @param feedback 反馈数据
 * @param typeName 反馈类型名称
 * @param onClick 卡片点击回调
 * @author Joker.X
 */
@Composable
fun FeedbackCard(
    modifier: Modifier = Modifier,
    feedback: Feedback,
    typeName: String = feedback.type.toString(),
    onClick: () -> Unit = {}
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium)
        ) {
            // 顶部：反馈类型标签 + 状态标签 + 创建时间
            SpaceBetweenRow(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 反馈类型标签
                    Tag(
                        text = typeName,
                        type = TagType.PRIMARY,
                        size = TagSize.SMALL,
//                        shape = ShapeMedium
                    )

                    // 状态标签
                    Tag(
                        text = stringResource(
                            if (feedback.status == 1) R.string.status_processed else R.string.status_unprocessed
                        ),
                        type = if (feedback.status == 1) TagType.SUCCESS else TagType.WARNING,
                        size = TagSize.SMALL,
//                        shape = ShapeMedium
                    )
                }

                // 创建时间
                AppText(
                    text = feedback.createTime ?: "",
                    type = TextType.SECONDARY,
                    size = TextSize.BODY_SMALL
                )
            }

            SpaceVerticalMedium()

            // 中间：反馈内容文本
            AppText(
                text = feedback.content,
                modifier = Modifier.fillMaxWidth()
            )

            // 图片九宫格（如果有）
            if (!feedback.images.isNullOrEmpty()) {
                SpaceVerticalMedium()

                FeedbackImageGrid(
                    images = feedback.images ?: emptyList(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 客服回复内容（如果有）
            if (!feedback.remark.isNullOrEmpty()) {
                SpaceVerticalMedium()

                // 客服回复区域
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = ShapeSmall
                        )
                        .padding(SpacePaddingMedium)
                ) {
                    Column {
                        AppText(
                            text = stringResource(R.string.customer_service_reply),
                            type = TextType.SECONDARY,
                            size = TextSize.BODY_SMALL
                        )

                        SpaceVerticalSmall()

                        AppText(
                            text = feedback.remark ?: "",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

/**
 * 反馈图片九宫格组件
 *
 * @param images 图片URL列表
 * @param modifier 修饰符
 * @param maxImages 最大显示图片数量，默认9张
 * @author Joker.X
 */
@Composable
private fun FeedbackImageGrid(
    images: List<String>,
    modifier: Modifier = Modifier,
    maxImages: Int = 9
) {
    val displayImages = images.take(maxImages)
    val rows = displayImages.chunked(3)
    val spacing = 8.dp

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        rows.forEach { rowImages ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                rowImages.forEach { imageUrl ->
                    FeedbackImageItem(
                        imageUrl = imageUrl,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                }

                // 填充空白位置以保持对齐
                repeat(3 - rowImages.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * 反馈图片项组件
 *
 * @param imageUrl 图片URL
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
private fun FeedbackImageItem(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    NetWorkImage(
        model = imageUrl,
        showBackground = true,
        modifier = modifier
            .clip(ShapeMedium),
        contentScale = ContentScale.Crop
    )
}

/**
 * 反馈卡片预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun FeedbackCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 有图片的反馈
            FeedbackCard(
                feedback = Feedback(
                    id = 1,
                    type = 0,
                    content = "希望能够增加夜间模式功能，这样在晚上使用时会更加舒适。另外，建议优化一下加载速度，有时候会比较慢。",
                    contact = "user@example.com",
                    remark = "感谢您的反馈，我们会尽快处理。",
                    images = listOf(
                        "https://example.com/image1.jpg",
                        "https://example.com/image2.jpg",
                        "https://example.com/image3.jpg"
                    ),
                    status = 0,
                    createTime = "2025-08-15 10:30:00",
                    updateTime = "2025-08-15 10:30:00"
                )
            )

            // 无图片的反馈
            FeedbackCard(
                feedback = Feedback(
                    id = 2,
                    type = 1,
                    content = "登录时偶尔会出现验证码刷新失败的问题，需要多次刷新才能正常显示。",
                    contact = "13800138000",
                    images = null,
                    status = 1,
                    createTime = "2025-08-15 10:30:00",
                    updateTime = "2025-08-15 10:30:00"
                )
            )
        }
    }
}