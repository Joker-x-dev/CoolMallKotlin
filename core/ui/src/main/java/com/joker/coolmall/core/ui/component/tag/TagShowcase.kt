package com.joker.coolmall.core.ui.component.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.title.TitleWithLine

/**
 * Tag组件展示页面
 *
 * 用于展示所有标签组件的使用示例，包括：
 * - 基础样式（默认、主要、警告、危险、成功）
 * - 浅色样式
 * - 空心样式
 * - 不同大小（小、中、大）
 * - 可关闭标签
 * - 不同样式的可关闭标签
 *
 * @author Joker.X
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagShowcase() {

    // 用于记录可关闭标签的显示状态
    var showTag1 by remember { mutableStateOf(true) }
    var showTag2 by remember { mutableStateOf(true) }
    var showTag3 by remember { mutableStateOf(true) }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(SpacePaddingMedium)
                .padding(it)
        ) {
            Text(
                text = "标签 Tag",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            SpaceVerticalMedium()

            TitleWithLine(text = "基础样式")

            SpaceVerticalSmall()

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Tag(text = "默认", type = TagType.DEFAULT, style = TagStyle.FILLED)
                Tag(text = "主要", type = TagType.PRIMARY, style = TagStyle.FILLED)
                Tag(text = "警告", type = TagType.WARNING, style = TagStyle.FILLED)
                Tag(text = "危险", type = TagType.DANGER, style = TagStyle.FILLED)
                Tag(text = "成功", type = TagType.SUCCESS, style = TagStyle.FILLED)
            }


            SpaceVerticalMedium()

            TitleWithLine(text = "浅色样式")

            SpaceVerticalSmall()

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Tag(text = "默认", type = TagType.DEFAULT, style = TagStyle.LIGHT)
                Tag(text = "主要", type = TagType.PRIMARY, style = TagStyle.LIGHT)
                Tag(text = "警告", type = TagType.WARNING, style = TagStyle.LIGHT)
                Tag(text = "危险", type = TagType.DANGER, style = TagStyle.LIGHT)
                Tag(text = "成功", type = TagType.SUCCESS, style = TagStyle.LIGHT)
            }


            SpaceVerticalMedium()

            TitleWithLine(text = "空心样式")

            SpaceVerticalSmall()

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Tag(text = "默认", type = TagType.DEFAULT, style = TagStyle.OUTLINED)
                Tag(text = "主要", type = TagType.PRIMARY, style = TagStyle.OUTLINED)
                Tag(text = "警告", type = TagType.WARNING, style = TagStyle.OUTLINED)
                Tag(text = "危险", type = TagType.DANGER, style = TagStyle.OUTLINED)
                Tag(text = "成功", type = TagType.SUCCESS, style = TagStyle.OUTLINED)
            }

            SpaceVerticalMedium()

            TitleWithLine(text = "不同大小")

            SpaceVerticalSmall()

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tag(text = "小尺寸", type = TagType.PRIMARY, size = TagSize.SMALL)
                Tag(text = "中尺寸", type = TagType.PRIMARY, size = TagSize.MEDIUM)
                Tag(text = "大尺寸", type = TagType.PRIMARY, size = TagSize.LARGE)
            }

            SpaceVerticalMedium()

            TitleWithLine(text = "带关闭按钮的标签（可交互）")

            SpaceVerticalSmall()

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (showTag1) {
                    TagClosable(
                        text = "标签一",
                        type = TagType.PRIMARY,
                        style = TagStyle.FILLED,
                        onClose = { showTag1 = false }
                    )
                }

                if (showTag2) {
                    TagClosable(
                        text = "标签二",
                        type = TagType.DANGER,
                        style = TagStyle.FILLED,
                        onClose = { showTag2 = false }
                    )
                }

                if (showTag3) {
                    TagClosable(
                        text = "标签三",
                        type = TagType.SUCCESS,
                        style = TagStyle.FILLED,
                        onClose = { showTag3 = false }
                    )
                }
            }

            // 提示文本
            if (!showTag1 && !showTag2 && !showTag3) {
                SpaceVerticalSmall()
                Text(
                    text = "所有标签已关闭，请刷新预览重新显示",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            // 不同样式的可关闭标签
            SpaceVerticalMedium()

            TitleWithLine(text = "不同样式的可关闭标签")

            SpaceVerticalSmall()

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TagClosable(
                    text = "填充样式",
                    type = TagType.PRIMARY,
                    style = TagStyle.FILLED,
                    onClose = {}
                )

                TagClosable(
                    text = "浅色样式",
                    type = TagType.PRIMARY,
                    style = TagStyle.LIGHT,
                    onClose = {}
                )

                TagClosable(
                    text = "空心样式",
                    type = TagType.PRIMARY,
                    style = TagStyle.OUTLINED,
                    onClose = {}
                )
            }
        }
    }
}

/**
 * Tag组件展示页面预览 - 浅色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun TagShowcasePreviewLight() {
    AppTheme(darkTheme = false) {
        TagShowcase()
    }
}

/**
 * Tag组件展示页面预览 - 深色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun TagShowcasePreviewDark() {
    AppTheme(darkTheme = true) {
        TagShowcase()
    }
} 