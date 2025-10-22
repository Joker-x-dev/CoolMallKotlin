package com.joker.coolmall.core.ui.component.modal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * Modal类型枚举
 *
 * @author Joker.X
 */
enum class ModalType {
    /**
     * 底部弹出模式
     */
    BOTTOM,

    /**
     * 居中弹出模式
     */
    CENTER
}

/**
 * 通用Modal组件
 *
 * 支持底部弹出和居中弹出两种模式
 *
 * @param visible 是否显示
 * @param modalType 弹出类型，默认为底部弹出
 * @param onDismiss 关闭回调
 * @param dismissOnBackPress 是否允许返回键关闭
 * @param dismissOnClickOutside 是否允许点击外部关闭
 * @param content 内容
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Modal(
    visible: Boolean,
    modalType: ModalType = ModalType.BOTTOM,
    onDismiss: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    var localVisible by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        localVisible = visible
    }

    if (visible || localVisible) {
        when (modalType) {
            ModalType.BOTTOM -> {
                val sheetState = rememberModalBottomSheetState()
                ModalBottomSheet(
                    onDismissRequest = onDismiss,
                    sheetState = sheetState,
                    containerColor = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    dragHandle = null
                ) {
                    content()
                }
            }

            ModalType.CENTER -> {
                Dialog(
                    onDismissRequest = onDismiss,
                    properties = DialogProperties(
                        dismissOnBackPress = dismissOnBackPress,
                        dismissOnClickOutside = dismissOnClickOutside
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedVisibility(
                            visible = visible && localVisible,
                            enter = fadeIn(animationSpec = tween(200)) +
                                    slideInVertically(
                                        animationSpec = tween(200),
                                        initialOffsetY = { it / 2 }
                                    ),
                            exit = fadeOut(animationSpec = tween(200)) +
                                    slideOutVertically(
                                        animationSpec = tween(200),
                                        targetOffsetY = { it / 2 }
                                    )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surface,
                                        RoundedCornerShape(12.dp)
                                    )
                            ) {
                                content()
                            }
                        }
                    }
                }
            }
        }
    }
} 