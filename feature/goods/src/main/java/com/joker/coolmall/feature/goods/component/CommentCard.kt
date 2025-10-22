package com.joker.coolmall.feature.goods.component

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
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Comment
import com.joker.coolmall.core.ui.component.image.Avatar
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.rate.WeRate
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.goods.R

/**
 * 商品评价卡片组件
 *
 * 参考京东评论样式设计：左侧头像固定，右侧内容区域垂直排列
 *
 * @param modifier 修饰符
 * @param comment 评价数据
 * @author Joker.X
 */
@Composable
fun CommentCard(comment: Comment, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium),
            verticalAlignment = Alignment.Top
        ) {
            // 用户头像
            Avatar(
                avatarUrl = comment.avatarUrl,
                size = 40.dp,
                modifier = Modifier
            )

            SpaceHorizontalMedium()

            // 内容区域
            Column(modifier = Modifier.weight(1f)) {
                SpaceBetweenRow {
                    // 用户昵称
                    AppText(
                        text = comment.nickName ?: stringResource(R.string.anonymous_user),
                        size = TextSize.BODY_LARGE,
                        type = TextType.PRIMARY
                    )

                    // 评论时间
                    comment.createTime?.let { time ->
                        AppText(
                            text = time,
                            size = TextSize.BODY_SMALL,
                            type = TextType.TERTIARY
                        )
                    }
                }

                SpaceVerticalXSmall()

                // 星级评分
                WeRate(
                    value = comment.starCount,
                    count = 5,
                    size = 16.dp,
                    animationEnabled = false,
                )

                SpaceVerticalMedium()

                // 评价文本内容
                if (comment.content.isNotEmpty()) {
                    AppText(
                        text = comment.content,
                        modifier = Modifier.fillMaxWidth()
                    )

                    SpaceVerticalMedium()
                }

                // 九宫格图片展示
                val picsList = comment.pics
                if (!picsList.isNullOrEmpty()) {
                    CommentImageGrid(
                        images = picsList,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * 评价图片九宫格组件
 *
 * @param images 图片URL列表
 * @param modifier 修饰符
 * @param maxImages 最大显示图片数量，默认9张
 * @author Joker.X
 */
@Composable
private fun CommentImageGrid(
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
                    CommentImageItem(
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
 * 评价图片项组件
 *
 * @param imageUrl 图片URL
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
private fun CommentImageItem(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(ShapeSmall)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        NetWorkImage(
            model = imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
    }
}

/**
 * CommentCard 组件预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun CommentCardPreview() {
    AppTheme {
        CommentCard(
            comment = Comment(
                id = 1,
                userId = 1001,
                goodsId = 2001,
                orderId = 3001,
                content = "商品质量很好，物流速度也很快，包装精美，非常满意的一次购物体验！",
                starCount = 5,
                pics = listOf(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg",
                    "https://example.com/image3.jpg"
                ),
                nickName = "张三",
                avatarUrl = "https://example.com/avatar.jpg",
                createTime = "2024-01-15 10:30:00"
            )
        )
    }
}

/**
 * CommentCard 组件预览 - 无图片
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun CommentCardNoImagesPreview() {
    AppTheme {
        CommentCard(
            comment = Comment(
                id = 2,
                userId = 1002,
                goodsId = 2002,
                orderId = 3002,
                content = "商品不错，值得推荐！",
                starCount = 4,
                pics = null,
                nickName = "李四",
                avatarUrl = "https://example.com/avatar2.jpg",
                createTime = "2024-01-14 15:20:00"
            )
        )
    }
}

/**
 * CommentCard 组件预览 - 深色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun CommentCardDarkPreview() {
    AppTheme(darkTheme = true) {
        CommentCard(
            comment = Comment(
                id = 3,
                userId = 1003,
                goodsId = 2003,
                orderId = 3003,
                content = "产品质量超出预期，客服态度也很好，下次还会再来购买的。",
                starCount = 5,
                pics = listOf(
                    "https://example.com/image4.jpg",
                    "https://example.com/image5.jpg"
                ),
                nickName = "王五",
                avatarUrl = "https://example.com/avatar3.jpg",
                createTime = "2024-01-13 09:45:00"
            )
        )
    }
}