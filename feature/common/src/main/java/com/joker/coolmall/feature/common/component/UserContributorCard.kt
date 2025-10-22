package com.joker.coolmall.feature.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.UserContributor
import com.joker.coolmall.core.ui.component.image.SmallAvatar
import com.joker.coolmall.core.ui.component.tag.Tag
import com.joker.coolmall.core.ui.component.tag.TagSize
import com.joker.coolmall.core.ui.component.tag.TagStyle
import com.joker.coolmall.core.ui.component.tag.TagType
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 用户贡献者卡片组件
 *
 * 参考CommentCard设计：左侧头像固定，右侧内容区域垂直排列
 * 展示贡献者的基本信息、个性签名、标签和网站链接
 *
 * @param contributor 贡献者数据
 * @param modifier 修饰符
 * @param onClick 点击事件回调，参数为贡献者数据
 * @author Joker.X
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserContributorCard(
    contributor: UserContributor,
    modifier: Modifier = Modifier,
    onClick: ((UserContributor) -> Unit)? = null,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(ShapeMedium)
            .clickable { onClick?.invoke(contributor) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium),
            verticalAlignment = Alignment.Top
        ) {
            // 用户头像
            SmallAvatar(
                avatarUrl = contributor.avatarUrl,
                modifier = Modifier
            )

            SpaceHorizontalMedium()

            // 内容区域
            Column(modifier = Modifier.weight(1f)) {
                AppText(
                    text = contributor.nickName,
                    size = TextSize.BODY_LARGE,
                    type = TextType.PRIMARY
                )

                SpaceVerticalXSmall()

                // 个性签名/个人描述
                contributor.signature?.let { signature ->
                    if (signature.isNotEmpty()) {
                        AppText(
                            text = signature,
                            size = TextSize.BODY_MEDIUM,
                            type = TextType.SECONDARY,
                            modifier = Modifier.fillMaxWidth()
                        )

                        SpaceVerticalSmall()
                    }
                }

                // 个人标签
                contributor.tags?.let { tags ->
                    if (tags.isNotEmpty()) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            tags.forEach { tag ->
                                Tag(
                                    text = tag,
                                    type = TagType.PRIMARY,
                                    style = TagStyle.FILLED,
                                    size = TagSize.SMALL
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 组件预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun UserContributorCardPreview() {
    AppTheme {
        UserContributorCard(
            contributor = UserContributor(
                id = 1,
                avatarUrl = "https://example.com/avatar1.jpg",
                nickName = "Joker.X",
                websiteUrl = "https://github.com/Joker-x-dev",
                signature = "热爱开源，专注于Android开发，致力于打造优秀的移动应用体验。",
                tags = listOf("开发者", "Android", "Kotlin", "Jetpack Compose"),
                createTime = "2025-09-20 22:53:13"
            ),
        )
    }
}