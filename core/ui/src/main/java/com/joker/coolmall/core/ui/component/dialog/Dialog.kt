package com.joker.coolmall.core.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.joker.coolmall.core.ui.component.divider.WeDivider

/**
 * 对话框
 *
 * @param title 标题
 * @param content 内容
 * @param okText 确定按钮文字
 * @param cancelText 取消按钮文字
 * @param okColor 确定按钮颜色
 * @param onOk 确定事件
 * @param onCancel 取消事件
 * @param onDismiss 关闭事件
 */
@Composable
fun WeDialog(
    title: String,
    content: String? = null,
    okText: String = "确定",
    cancelText: String = "取消",
    okColor: Color = Color.Unspecified,
    onOk: () -> Unit,
    onCancel: (() -> Unit)? = null,
    onDismiss: () -> Unit
) {
    val confirmColor = if (okColor == Color.Unspecified) {
        MaterialTheme.colorScheme.primary
    } else {
        okColor
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth(0.8f)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 32.dp,
                            bottom = if (content != null) 16.dp else 0.dp,
                            start = 24.dp,
                            end = 24.dp
                        ),
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                if (content != null) {
                    Text(
                        text = content,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                WeDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (onCancel != null) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .clickable(onClick = onCancel),
                            contentAlignment = Alignment.Center
                        ) {
                        Text(
                            text = cancelText,
                            color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(0.5.dp, 56.dp)
                                .background(MaterialTheme.colorScheme.outline)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clickable(onClick = onOk),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = okText,
                            color = confirmColor,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Stable
interface DialogState {
    /**
     * 是否显示
     */
    val visible: Boolean

    /**
     * 显示对话框
     */
    fun show(
        title: String,
        content: String? = null,
        okText: String = "确定",
        cancelText: String = "取消",
        okColor: Color? = null,
        closeOnAction: Boolean = true,
        onCancel: (() -> Unit)? = {},
        onOk: (() -> Unit)? = null
    )

    /**
     * 隐藏对话框
     */
    fun hide()
}

@Composable
fun rememberDialogState(): DialogState {
    val state = remember { DialogStateImpl() }

    if (state.visible) {
        state.props?.let { props ->
            WeDialog(
                title = props.title,
                content = props.content,
                okText = props.okText,
                cancelText = props.cancelText,
                okColor = props.okColor ?: Color.Unspecified,
                onOk = {
                    props.onOk?.invoke()
                    if (props.closeOnAction) {
                        state.hide()
                    }
                },
                onCancel = if (props.onCancel != null) {
                    {
                        props.onCancel.invoke()
                        if (props.closeOnAction) {
                            state.hide()
                        }
                    }
                } else null,
                onDismiss = {
                    state.hide()
                }
            )
        }
    }

    return state
}

private class DialogStateImpl : DialogState {
    override var visible by mutableStateOf(false)
    var props by mutableStateOf<DialogProps?>(null)
        private set

    override fun show(
        title: String,
        content: String?,
        okText: String,
        cancelText: String,
        okColor: Color?,
        closeOnAction: Boolean,
        onCancel: (() -> Unit)?,
        onOk: (() -> Unit)?
    ) {
        props = DialogProps(
            title,
            content,
            okText,
            cancelText,
            okColor,
            closeOnAction,
            onCancel,
            onOk
        )
        visible = true
    }

    override fun hide() {
        visible = false
    }
}

private data class DialogProps(
    val title: String,
    val content: String?,
    val okText: String,
    val cancelText: String,
    val okColor: Color?,
    val closeOnAction: Boolean,
    val onCancel: (() -> Unit)?,
    val onOk: (() -> Unit)?
)
