package com.joker.coolmall.feature.goods.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
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
 * 评论Item组件 - 紧凑型布局
 *
 * 设计特点：左侧头像+内容，右侧单张图片，多图显示数量标识
 *
 * @param comment 评价数据
 * @param modifier 修饰符
 * @param onClick 点击事件回调
 * @author Joker.X
 */
@Composable
fun CommentItem(
    comment: Comment,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(SpacePaddingMedium),
        verticalAlignment = Alignment.Top
    ) {
        // 左侧：用户头像
        Avatar(
            avatarUrl = comment.avatarUrl,
            size = 32.dp,
            modifier = Modifier
        )

        SpaceHorizontalMedium()

        // 中间：内容区域
        Column(modifier = Modifier.weight(1f)) {
            // 用户昵称
            AppText(
                text = comment.nickName ?: stringResource(R.string.anonymous_user),
                size = TextSize.BODY_MEDIUM,
                type = TextType.PRIMARY
            )

            SpaceVerticalXSmall()

            // 星级评分
            WeRate(
                value = comment.starCount,
                count = 5,
                size = 14.dp,
                animationEnabled = false
            )

            SpaceVerticalXSmall()

            // 评价文本内容
            if (comment.content.isNotEmpty()) {
                AppText(
                    text = comment.content,
                    size = TextSize.BODY_MEDIUM,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // 右侧：图片区域
        val picsList = comment.pics
        if (!picsList.isNullOrEmpty()) {
            SpaceHorizontalMedium()
            CommentImagePreview(
                images = picsList,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

/**
 * 评论图片预览组件 - 单图显示，多图标识
 *
 * @param images 图片URL列表
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
private fun CommentImagePreview(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(ShapeSmall)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // 显示第一张图片
        NetWorkImage(
            model = images.first(),
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // 如果有多张图片，显示数量标识
        if (images.size > 1) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.4f),
                        shape = ShapeSmall
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                AppText(
                    text = "+${images.size - 1}",
                    size = TextSize.BODY_SMALL,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * CommentItem 组件预览 - 多图片
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun CommentItemPreview() {
    AppTheme {
        CommentItem(
            comment = Comment(
                id = 1,
                userId = 1001,
                goodsId = 2001,
                orderId = 3001,
                content = "电池续航能力超强，一天一充完全没问题，再也不用刻意担心手机没电了。充电速度也很快",
                starCount = 5,
                pics = listOf(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg",
                    "https://example.com/image3.jpg"
                ),
                nickName = "匿名用户",
                avatarUrl = "https://example.com/avatar.jpg",
                createTime = "2024-04-02 11:20:00"
            )
        )
    }
}

/**
 * CommentItem 组件预览 - 单图片
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun CommentItemSingleImagePreview() {
    AppTheme {
        CommentItem(
            comment = Comment(
                id = 2,
                userId = 1002,
                goodsId = 2002,
                orderId = 3002,
                content = "66666",
                starCount = 5,
                pics = listOf(
                    "https://example.com/image1.jpg"
                ),
                nickName = "匿名用户",
                avatarUrl = "https://example.com/avatar2.jpg",
                createTime = "2024-09-01 09:48:00"
            )
        )
    }
}

/**
 * CommentItem 组件预览 - 无图片
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun CommentItemNoImagesPreview() {
    AppTheme {
        CommentItem(
            comment = Comment(
                id = 3,
                userId = 1003,
                goodsId = 2003,
                orderId = 3003,
                content = "我是评论内容",
                starCount = 5,
                pics = null,
                nickName = "匿名用户",
                avatarUrl = "https://example.com/avatar3.jpg",
                createTime = "2024-09-01 22:40:00"
            )
        )
    }
}

/**
 * CommentItem 组件预览 - 深色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun CommentItemDarkPreview() {
    AppTheme(darkTheme = true) {
        CommentItem(
            comment = Comment(
                id = 4,
                userId = 1004,
                goodsId = 2004,
                orderId = 3004,
                content = "hhhhhh",
                starCount = 5,
                pics = listOf(
                    "https://example.com/image4.jpg",
                    "https://example.com/image5.jpg",
                    "https://example.com/image6.jpg"
                ),
                nickName = "匿名用户",
                avatarUrl = "https://example.com/avatar4.jpg",
                createTime = "2024-09-02 21:32:00"
            )
        )
    }
}