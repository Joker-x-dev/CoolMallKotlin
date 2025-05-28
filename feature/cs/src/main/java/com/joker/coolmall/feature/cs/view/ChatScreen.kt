package com.joker.coolmall.feature.cs.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.AppColumn
import com.joker.coolmall.core.designsystem.component.AppRow
import com.joker.coolmall.core.designsystem.component.FullScreenBox
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.ShapeExtraLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.model.entity.CsMsg
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.tag.Tag
import com.joker.coolmall.core.ui.component.tag.TagStyle
import com.joker.coolmall.core.ui.component.tag.TagType
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.cs.component.ChatInputArea
import com.joker.coolmall.feature.cs.viewmodel.ChatViewModel
import com.joker.coolmall.feature.cs.viewmodel.WebSocketConnectionState
import kotlinx.coroutines.launch

/**
 * 客服聊天路由
 *
 * @param viewModel 客服聊天 ViewModel
 */
@Composable
internal fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel()
) {
    ChatScreen(
        viewModel = viewModel,
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 客服聊天界面
 *
 * @param viewModel 客服聊天 ViewModel
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    uiState: BaseNetWorkUiState<Any> = BaseNetWorkUiState.Success(data = Any()),
    onBackClick: () -> Unit = {},
) {
    val messages by viewModel.messages.collectAsState()
    val connectionState by viewModel.connectionState.collectAsState()
    val isLoadingHistory by viewModel.isLoadingHistory.collectAsState()

    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    // 管理输入框状态
    var inputText by remember { mutableStateOf("") }

    // 进入页面标记已读
    DisposableEffect(Unit) {
        viewModel.markMessagesAsRead()
        onDispose { /* 页面销毁时清理资源 */ }
    }

    Scaffold(
        topBar = {
            CenterTopAppBar(
//                title = "客服聊天",
                onBackClick = onBackClick,
                actions = {
                    // 显示连接状态指示器
                    when (connectionState) {
                        WebSocketConnectionState.Connected -> {
                            Box(
                                modifier = Modifier.size(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Surface(
                                    color = Color.Green,
                                    shape = ShapeCircle,
                                    modifier = Modifier.size(8.dp)
                                ) {}
                            }
                        }

                        WebSocketConnectionState.Connecting -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                        }

                        is WebSocketConnectionState.Error, WebSocketConnectionState.Disconnected -> {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "重新连接",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { viewModel.connectWebSocket() }
                            )
                        }
                    }
                }
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
//            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        FullScreenBox(
            modifier = Modifier
                .padding(paddingValues)
                .imePadding()
        ) {
            AppColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // 消息列表
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    MessageList(
                        messages = messages,
                        isLoading = isLoadingHistory,
                        scrollState = scrollState,
                        onRefresh = { viewModel.loadHistoryMessages() }
                    )
                }

                // 使用封装后的输入区域组件
                ChatInputArea(
                    inputText = inputText,
                    onInputTextChange = { inputText = it },
                    onSendMessage = {
                        if (inputText.isNotBlank()) {
                            viewModel.sendMessage(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun MessageList(
    messages: List<CsMsg>,
    isLoading: Boolean = false,
    scrollState: LazyListState,
    onRefresh: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = SpacePaddingSmall)
        ) {
            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(SpacePaddingMedium),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            } else {
                items(
                    items = messages,
                    key = { it.id }
                ) { message ->
                    Message(
                        onAuthorClick = { /* 点击用户头像 */ },
                        msg = message,
                        isUserMe = message.type == 0, // 0-反馈(用户), 1-回复(客服)
                        isFirstMessageByAuthor = true, // 简化处理
                        isLastMessageByAuthor = true, // 简化处理
                    )
                }

                // 上拉加载更多
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(SpacePaddingMedium)
                            .clickable { onRefresh() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "加载更多",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // 滚动到底部按钮
        val jumpThreshold = with(LocalDensity.current) {
            56.dp.toPx()
        }

        val jumpToBottomButtonEnabled by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex != 0 ||
                        scrollState.firstVisibleItemScrollOffset > jumpThreshold
            }
        }

        JumpToBottom(
            // 只有当滚动条不在底部时才显示
            enabled = jumpToBottomButtonEnabled,
            onClicked = {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun Message(
    onAuthorClick: (String) -> Unit,
    msg: CsMsg,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
) {
    val spaceBetweenAuthors =
        if (isLastMessageByAuthor) Modifier.padding(top = SpaceVerticalSmall) else Modifier

    AppRow(
        modifier = spaceBetweenAuthors
            .fillMaxWidth()
            .padding(horizontal = SpacePaddingMedium),
        horizontalArrangement = if (isUserMe) Arrangement.End else Arrangement.Start
    ) {
        if (!isUserMe && isLastMessageByAuthor) {
            // 客服头像，左侧显示
            NetWorkImage(
                model = msg.adminUserHeadImg,
                modifier = Modifier
                    .clickable(onClick = { onAuthorClick(msg.adminUserName) })
                    .size(36.dp)
                    .align(Alignment.Top),
                cornerShape = ShapeCircle,
                contentScale = ContentScale.Crop,
            )

            // 头像与消息之间的间距
            SpaceHorizontalSmall()
        } else if (!isUserMe && !isLastMessageByAuthor) {
            // 客服头像下方的空间
            Spacer(modifier = Modifier.width(36.dp + SpaceHorizontalSmall))
        }

        AuthorAndTextMessage(
            msg = msg,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            isLastMessageByAuthor = isLastMessageByAuthor,
            authorClicked = onAuthorClick,
            modifier = Modifier
                .weight(1f, fill = false),
        )

        if (isUserMe && isLastMessageByAuthor) {
            // 头像与消息之间的间距
            SpaceHorizontalSmall()

            // 用户头像，右侧显示
            NetWorkImage(
                model = msg.avatarUrl,
                modifier = Modifier
                    .clickable(onClick = { onAuthorClick(msg.nickName) })
                    .size(36.dp)
                    .align(Alignment.Top),
                cornerShape = ShapeCircle,
                contentScale = ContentScale.Crop,
            )
        } else if (isUserMe && !isLastMessageByAuthor) {
            // 用户头像下方的空间
            Spacer(modifier = Modifier.width(36.dp + SpaceHorizontalSmall))
        }
    }
}

@Composable
fun AuthorAndTextMessage(
    msg: CsMsg,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    authorClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppColumn(
        modifier = modifier,
        horizontalAlignment = if (isUserMe) Alignment.End else Alignment.Start
    ) {
        if (isLastMessageByAuthor) {
            AuthorNameTimestamp(msg, isUserMe)
        }

        ChatItemBubble(msg, isUserMe)

        if (isFirstMessageByAuthor) {
            // 下一个作者之前的最后一个气泡
            SpaceVerticalSmall()
        } else {
            // 气泡之间
            SpaceVerticalSmall()
        }
    }
}

@Composable
private fun AuthorNameTimestamp(msg: CsMsg, isUserMe: Boolean) {
    // 为了无障碍而结合作者和时间戳
    AppRow(
        modifier = Modifier
            .padding(bottom = SpacePaddingXSmall)
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = if (isUserMe) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isUserMe) {
            AppText(
                text = msg.adminUserName,
                size = TextSize.BODY_MEDIUM,
                modifier = Modifier
                    .alignBy(LastBaseline)
                    .paddingFrom(LastBaseline, after = SpaceHorizontalXSmall), // 到第一个气泡的空间
            )
            SpaceHorizontalXSmall()
        }

        AppText(
            text = msg.createTime ?: "",
            size = TextSize.BODY_SMALL,
            type = TextType.TERTIARY,
            modifier = Modifier.alignBy(LastBaseline),
        )

        if (isUserMe) {
            SpaceHorizontalXSmall()
            AppText(
                text = msg.nickName,
                size = TextSize.BODY_MEDIUM,
                modifier = Modifier
                    .alignBy(LastBaseline)
                    .paddingFrom(LastBaseline, after = SpaceHorizontalXSmall),
            )
        }
    }
}

// 使用设计规范中的圆角参数定义聊天气泡形状
private val UserChatBubbleShape = RoundedCornerShape(
    topStart = 18.dp,
    topEnd = 4.dp,
    bottomStart = 18.dp,
    bottomEnd = 18.dp
)
private val OtherChatBubbleShape = RoundedCornerShape(
    topStart = 4.dp,
    topEnd = 18.dp,
    bottomStart = 18.dp,
    bottomEnd = 18.dp
)

@Composable
fun DayHeader(dayString: String) {
    SpaceBetweenRow(
        modifier = Modifier
            .padding(vertical = SpaceVerticalSmall, horizontal = SpacePaddingMedium)
            .height(16.dp),
    ) {
        DayHeaderLine()
        AppText(
            text = dayString,
            size = TextSize.BODY_SMALL,
            type = TextType.TERTIARY,
            modifier = Modifier.padding(horizontal = SpacePaddingSmall),
        )
        DayHeaderLine()
    }
}

@Composable
private fun RowScope.DayHeaderLine() {
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
    )
}

@Composable
fun ChatItemBubble(message: CsMsg, isUserMe: Boolean) {
    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceContainerHigh
    }

    val textColor = if (isUserMe) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        color = backgroundBubbleColor,
        shape = if (isUserMe) UserChatBubbleShape else OtherChatBubbleShape,
    ) {
        AppText(
            text = message.content.data,
            size = TextSize.BODY_LARGE,
            color = textColor,
            modifier = Modifier
                .padding(horizontal = SpacePaddingLarge)
                .padding(vertical = SpacePaddingMedium)
        )
    }
}

@Composable
fun JumpToBottom(
    enabled: Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (enabled) {
        Tag(
            "回到底部",
            shape = ShapeExtraLarge,
            type = TagType.PRIMARY,
            style = TagStyle.LIGHT,
            modifier = modifier
                .padding(bottom = SpacePaddingSmall)
                .clickable(onClick = onClicked)
        )
    }
}

/**
 * 客服聊天界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun ChatScreenPreview() {
    AppTheme {
        ChatScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 客服聊天界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun ChatScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        ChatScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}