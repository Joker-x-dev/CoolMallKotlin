package com.joker.coolmall.feature.user.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.modal.BottomModal
import com.joker.coolmall.core.ui.component.tag.Tag
import com.joker.coolmall.core.ui.component.tag.TagSize
import com.joker.coolmall.core.ui.component.tag.TagStyle
import com.joker.coolmall.core.ui.component.tag.TagType
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.model.Region
import com.joker.coolmall.core.ui.R as CoreUiR

/**
 * 地区选择对话框
 *
 * @param visible 是否显示
 * @param onDismiss 关闭回调
 * @param regions 省级地区列表
 * @param onRegionSelected 地区选择回调，参数为完整地址字符串，如"广西壮族自治区 崇左市 大新县"
 * @param initialRegion 初始选中的地区，格式为"省 市 区"
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionPickerModal(
    visible: Boolean,
    onDismiss: () -> Unit,
    regions: List<Region>,
    onRegionSelected: (String) -> Unit,
    initialRegion: String = ""
) {
    // 存储当前选中的省、市、区
    var selectedProvince by remember { mutableStateOf<Region?>(null) }
    var selectedCity by remember { mutableStateOf<Region?>(null) }
    var selectedDistrict by remember { mutableStateOf<Region?>(null) }

    // 当前显示的级别: 0=省, 1=市, 2=区
    var currentLevel by remember { mutableIntStateOf(0) }

    // 底部弹出层状态
    val sheetState = rememberModalBottomSheetState()

    // 显示的地区列表
    val displayRegions = remember(currentLevel, selectedProvince, selectedCity) {
        when (currentLevel) {
            0 -> regions
            1 -> selectedProvince?.children ?: emptyList()
            2 -> selectedCity?.children ?: emptyList()
            else -> emptyList()
        }
    }

    // 处理初始地区
    LaunchedEffect(initialRegion, regions) {
        if (initialRegion.isNotBlank() && regions.isNotEmpty()) {
            val parts = initialRegion.split(" ")
            if (parts.isNotEmpty()) {
                // 查找初始省份
                val province = regions.find { it.name == parts[0] }
                selectedProvince = province

                if (parts.size > 1 && province != null) {
                    // 查找初始城市
                    val city = province.children.find { it.name == parts[1] }
                    selectedCity = city

                    if (parts.size > 2 && city != null) {
                        // 查找初始区县
                        selectedDistrict = city.children.find { it.name == parts[2] }
                    }
                }

                // 设置当前级别
                currentLevel = when {
                    selectedDistrict != null -> 2
                    selectedCity != null -> 1
                    else -> 0
                }
            }
        }
    }

    BottomModal(
        visible = visible,
        onDismiss = onDismiss,
        title = stringResource(id = R.string.please_select_region),
        sheetState = sheetState,
        showDragIndicator = true
    ) {
        // 标签栏
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 省标签
            Tag(
                text = selectedProvince?.name ?: "请选择",
                type = if (currentLevel == 0) TagType.PRIMARY else TagType.DEFAULT,
                style = if (currentLevel == 0) TagStyle.FILLED else TagStyle.LIGHT,
                size = TagSize.MEDIUM,
                shape = ShapeCircle,
                modifier = Modifier
                    .clip(ShapeCircle)
                    .clickable {
                        currentLevel = 0
                        selectedCity = null
                        selectedDistrict = null
                    }
            )

            if (selectedProvince != null) {
                // 市标签
                SpaceHorizontalSmall()
                Tag(
                    text = selectedCity?.name ?: "请选择",
                    type = if (currentLevel == 1) TagType.PRIMARY else TagType.DEFAULT,
                    style = if (currentLevel == 1) TagStyle.FILLED else TagStyle.LIGHT,
                    size = TagSize.MEDIUM,
                    shape = ShapeCircle,
                    modifier = Modifier
                        .clip(ShapeCircle)
                        .clickable {
                            if (selectedProvince != null) {
                                currentLevel = 1
                                selectedDistrict = null
                            }
                        }
                )

                if (selectedCity != null) {
                    // 区标签
                    SpaceHorizontalSmall()
                    Tag(
                        text = selectedDistrict?.name ?: "请选择",
                        shape = ShapeCircle,
                        type = if (currentLevel == 2) TagType.PRIMARY else TagType.DEFAULT,
                        style = if (currentLevel == 2) TagStyle.FILLED else TagStyle.LIGHT,
                        size = TagSize.MEDIUM,
                        modifier = Modifier
                            .clip(ShapeCircle)
                            .clickable {
                                if (selectedCity != null) {
                                    currentLevel = 2
                                }
                            }
                    )
                }
            }
        }

        SpaceVerticalMedium()
        WeDivider()
        // 地区列表
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(displayRegions) { region ->
                RegionItem(
                    region = region,
                    isSelected = when (currentLevel) {
                        0 -> region.id == selectedProvince?.id
                        1 -> region.id == selectedCity?.id
                        2 -> region.id == selectedDistrict?.id
                        else -> false
                    },
                    onClick = {
                        when (currentLevel) {
                            0 -> {
                                selectedProvince = region
                                selectedCity = null
                                selectedDistrict = null
                                // 如果有子地区，自动切换到城市级别
                                if (region.children.isNotEmpty()) {
                                    currentLevel = 1
                                } else {
                                    // 没有子地区，直接选择完成
                                    onRegionSelected(region.name)
                                    onDismiss()
                                }
                            }

                            1 -> {
                                selectedCity = region
                                selectedDistrict = null
                                // 如果有子地区，自动切换到区县级别
                                if (region.children.isNotEmpty()) {
                                    currentLevel = 2
                                } else {
                                    // 没有子地区，选择完成
                                    val result = "${selectedProvince?.name} ${region.name}"
                                    onRegionSelected(result)
                                    onDismiss()
                                }
                            }

                            2 -> {
                                selectedDistrict = region
                                // 选择完成，返回完整地址
                                val result =
                                    "${selectedProvince?.name} ${selectedCity?.name} ${region.name}"
                                onRegionSelected(result)
                                onDismiss()
                            }
                        }
                    }
                )
            }
        }
    }
}

/**
 * 地区列表项
 *
 * @param region 地区信息
 * @param isSelected 是否选中
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
private fun RegionItem(
    region: Region,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    AppListItem(
        title = region.name,
        showArrow = false,
        onClick = onClick,
        verticalPadding = SpaceVerticalLarge,
        trailingContent = if (isSelected) {
            {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    CommonIcon(
                        resId = CoreUiR.drawable.ic_success_circle,
                        tint = Color.White,
                        size = 14.dp,
                    )
                }
            }
        } else null
    )
}