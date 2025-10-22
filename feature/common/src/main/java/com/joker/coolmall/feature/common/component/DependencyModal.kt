package com.joker.coolmall.feature.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.modal.BottomModal
import com.joker.coolmall.core.ui.component.tag.Tag
import com.joker.coolmall.core.ui.component.tag.TagSize
import com.joker.coolmall.core.ui.component.tag.TagStyle
import com.joker.coolmall.core.ui.component.tag.TagType
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.common.R
import com.joker.coolmall.feature.common.model.Dependency

/**
 * 依赖致谢弹出层组件
 *
 * 用于展示项目中使用的第三方库信息，支持点击跳转到官方网站。
 *
 * @param visible 是否显示弹出层
 * @param dependencies 依赖列表
 * @param onDismiss 关闭弹出层回调
 * @param onDependencyClick 点击依赖项回调，传入依赖对象
 * @param sheetState 底部弹出层状态
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DependencyModal(
    visible: Boolean,
    dependencies: List<Dependency>,
    onDismiss: () -> Unit,
    onDependencyClick: (Dependency) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    BottomModal(
        visible = visible,
        title = stringResource(id = R.string.dependency_modal_title),
        onDismiss = onDismiss,
        sheetState = sheetState,
        showDragIndicator = true,
        horizontalPadding = SpacePaddingLarge,
        containerColor = MaterialTheme.colorScheme.background,
        indicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    ) {
        // 依赖列表
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = SpacePaddingLarge),
            verticalArrangement = Arrangement.spacedBy(SpacePaddingMedium)
        ) {
            items(dependencies) { dependency ->
                DependencyCard(
                    dependency = dependency,
                    onClick = { onDependencyClick(dependency) }
                )
            }
        }
    }
}

/**
 * 依赖卡片组件
 *
 * 展示单个依赖的信息，包括名称、版本、描述和分类。
 *
 * @param dependency 依赖对象
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
private fun DependencyCard(
    dependency: Dependency,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacePaddingLarge)
        ) {
            // 顶部：名称
            AppText(
                text = dependency.name,
                size = TextSize.BODY_LARGE,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            SpaceVerticalSmall()

            // 描述
            if (dependency.description.isNotEmpty()) {
                AppText(
                    text = dependency.description,
                    size = TextSize.BODY_MEDIUM,
                    type = TextType.SECONDARY,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                SpaceVerticalSmall()
            }

            // 底部：分类标签
            Tag(
                text = dependency.category,
                type = TagType.PRIMARY,
                size = TagSize.SMALL,
                style = TagStyle.LIGHT,
            )
        }
    }
}