package com.joker.coolmall.core.ui.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.component.StartRow
import com.joker.coolmall.core.designsystem.component.WrapColumn
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.model.entity.DictItem
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.empty.EmptyNetwork
import com.joker.coolmall.core.ui.component.loading.PageLoading
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 字典选择底部弹出层
 *
 * 这个组件用于显示字典项列表供用户选择，支持单选模式。
 * 参考了CouponModal和SpecSelectModal的实现，具备完整的网络状态处理能力。
 *
 * @param visible 是否显示弹出层
 * @param onDismiss 关闭弹出层的回调
 * @param title 弹出层标题资源ID
 * @param uiState 字典数据的网络状态，包含加载、错误、成功等状态
 * @param selectedItem 当前选中的字典项，可为空
 * @param onItemSelected 字典项选择回调，当用户点击某个字典项时触发
 * @param onConfirm 确认按钮回调，传递当前选中的字典项
 * @param onRetry 重试回调，当网络请求失败时用户点击重试按钮触发
 * @param onExpanded 弹窗展开完成回调，用于在动画完成后加载数据
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictSelectModal(
    visible: Boolean,
    onDismiss: () -> Unit,
    title: Int = R.string.please_select,
    uiState: BaseNetWorkUiState<List<DictItem>> = BaseNetWorkUiState.Loading,
    selectedItem: DictItem? = null,
    onItemSelected: (DictItem) -> Unit = {},
    onConfirm: (DictItem?) -> Unit = {},
    onRetry: () -> Unit = {},
    onExpanded: () -> Unit = {}
) {
    // 内部选中状态，用于管理用户的选择
    var internalSelectedItem by remember(selectedItem) { mutableStateOf(selectedItem) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // 监听弹窗展开状态，在完全展开后触发回调
    androidx.compose.runtime.LaunchedEffect(sheetState.currentValue) {
        if (sheetState.currentValue == androidx.compose.material3.SheetValue.Expanded && visible) {
            onExpanded()
        }
    }

    BottomModal(
        visible = visible,
        onDismiss = onDismiss,
        title = stringResource(id = title),
        sheetState = sheetState
    ) {
        BaseNetWorkView(
            uiState = uiState,
            customLoading = {
                PageLoading(modifier = Modifier.height(300.dp))
            },
            customError = {
                EmptyNetwork(modifier = Modifier.height(300.dp), onRetryClick = onRetry)
            },
        ) { dictItems ->
            DictSelectModalContent(
                dictItems = dictItems,
                selectedItem = internalSelectedItem,
                onItemSelected = { item ->
                    internalSelectedItem = item
                    onItemSelected(item)
                },
                onConfirm = {
                    onConfirm(internalSelectedItem)
                    onDismiss()
                }
            )
        }
    }
}

/**
 * 字典选择弹出层内容
 *
 * 这个私有组件封装了字典选择的具体UI实现，包括字典项列表和确认按钮。
 * 通过将内容逻辑分离，提高了代码的可维护性和可测试性。
 *
 * @param dictItems 字典项列表数据
 * @param selectedItem 当前选中的字典项
 * @param onItemSelected 字典项选择回调
 * @param onConfirm 确认按钮回调
 */
@Composable
private fun DictSelectModalContent(
    dictItems: List<DictItem>,
    selectedItem: DictItem? = null,
    onItemSelected: (DictItem) -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    WrapColumn {
        // 字典项列表
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp),
            verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium)
        ) {
            items(dictItems) { item ->
                DictItemRow(
                    item = item,
                    isSelected = selectedItem?.id == item.id,
                    onItemClick = { onItemSelected(item) }
                )
            }
        }

        SpaceVerticalLarge()

        // 确认按钮
        AppButton(
            text = stringResource(id = R.string.confirm),
            onClick = onConfirm,
            enabled = selectedItem != null,
            size = ButtonSize.MEDIUM,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * 字典项行组件
 *
 * 显示单个字典项的UI，包含单选按钮和文本。
 * 支持选中状态的视觉反馈。
 *
 * @param item 字典项数据
 * @param isSelected 是否被选中
 * @param onItemClick 点击回调
 */
@Composable
private fun DictItemRow(
    item: DictItem,
    isSelected: Boolean = false,
    onItemClick: () -> Unit = {}
) {
    SpaceBetweenRow(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ShapeSmall)
            .clickable { onItemClick() }
            .padding(
                horizontal = SpaceHorizontalMedium,
                vertical = SpaceVerticalMedium
            )
    ) {
        StartRow {
            // 单选按钮
            RadioButton(
                selected = isSelected,
                onClick = onItemClick
            )

            SpaceHorizontalMedium()

            // 字典项名称
            AppText(
                text = item.name ?: "",
                size = TextSize.BODY_LARGE,
                type = if (isSelected) TextType.PRIMARY else TextType.SECONDARY
            )
        }
    }
}

/**
 * 自定义单选按钮组件
 *
 * 提供与设计系统一致的单选按钮样式。
 *
 * @param selected 是否被选中
 * @param onClick 点击回调
 */
@Composable
private fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = if (selected) Primary else MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .background(
                color = if (selected) Primary else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = CircleShape
                    )
            )
        }
    }
}

/**
 * 字典选择弹出层预览
 */
@Preview(showBackground = true)
@Composable
private fun DictSelectModalPreview() {
    AppTheme {
        val sampleDictItems = listOf(
            DictItem(
                id = 1,
                typeId = 1,
                parentId = null,
                name = "不想要了",
                value = 1
            ),
            DictItem(
                id = 2,
                typeId = 1,
                parentId = null,
                name = "商品错选/多选",
                value = 2
            ),
            DictItem(
                id = 3,
                typeId = 1,
                parentId = null,
                name = "商品无货",
                value = 3
            )
        )

        Column {
            DictSelectModalContent(
                dictItems = sampleDictItems,
                selectedItem = sampleDictItems[0],
                onItemSelected = {},
                onConfirm = {}
            )
        }
    }
}