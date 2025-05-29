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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.LoadMoreState
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
import com.joker.coolmall.feature.cs.R
import com.joker.coolmall.feature.cs.component.ChatInputArea
import com.joker.coolmall.feature.cs.component.ChatLoadMore
import com.joker.coolmall.feature.cs.viewmodel.ChatViewModel
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
    val messages by viewModel.messages.collectAsState()
    val isLoadingHistory by viewModel.isLoadingHistory.collectAsState()
    val loadMoreState by viewModel.loadMoreState.collectAsState()
    val inputText by viewModel.inputText.collectAsState()

    ChatScreen(
        messages = messages,
        isLoadingHistory = isLoadingHistory,
        loadMoreState = loadMoreState,
        inputText = inputText,
        onBackClick = viewModel::navigateBack,
        onRefresh = viewModel::refreshMessages,
        onLoadMore = viewModel::loadMoreMessages,
        onSendMessage = viewModel::sendMessage,
        onInputTextChange = viewModel::updateInputText,
        onMarkAsRead = viewModel::markMessagesAsRead,
        newMessageEvent = viewModel.newMessageEvent
    )
}

/**
 * 客服聊天界面
 *
 * @param messages 消息列表
 * @param isLoadingHistory 是否正在加载历史消息
 * @param loadMoreState 加载更多状态
 * @param inputText 输入框文本
 * @param onBackClick 返回按钮回调
 * @param onRefresh 刷新消息回调
 * @param onLoadMore 加载更多消息回调
 * @param onSendMessage 发送消息回调
 * @param onInputTextChange 输入框文本变化回调
 * @param onMarkAsRead 标记消息已读回调
 * @param newMessageEvent 新消息事件流
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatScreen(
    messages: List<CsMsg> = emptyList(),
    isLoadingHistory: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    inputText: String = "",
    onBackClick: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    onSendMessage: () -> Unit = {},
    onInputTextChange: (String) -> Unit = {},
    onMarkAsRead: () -> Unit = {},
    newMessageEvent: kotlinx.coroutines.flow.Flow<Unit>? = null
) {
    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    // 监听新消息事件，自动滚动到底部
    LaunchedEffect(newMessageEvent) {
        newMessageEvent?.collect {
            // 滚动到列表顶部（因为是倒序排列，所以顶部是最新消息）
            try {
                scrollState.animateScrollToItem(0)
            } catch (_: Exception) {
                // 如果动画滚动失败，尝试立即滚动
                scrollState.scrollToItem(0)
            }
        }
    }

    Scaffold(
        topBar = {
            CenterTopAppBar(
                title = R.string.customer_service_chat,
                onBackClick = onBackClick
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
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
                        loadMoreState = loadMoreState,
                        scrollState = scrollState,
                        onRefresh = onRefresh,
                        onLoadMore = onLoadMore
                    )
                }

                // 使用封装后的输入区域组件
                ChatInputArea(
                    inputText = inputText,
                    onInputTextChange = onInputTextChange,
                    onSendMessage = {
                        // 发送消息逻辑已在ViewModel中处理，包括清空输入框
                        onSendMessage()
                        // 滚动逻辑由LaunchedEffect中的newMessageEvent处理
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * 消息列表组件
 *
 * @param messages 消息列表数据
 * @param isLoading 是否正在加载
 * @param loadMoreState 加载更多状态
 * @param scrollState 滚动状态
 * @param onRefresh 刷新回调
 * @param onLoadMore 加载更多回调
 */
@Composable
fun MessageList(
    messages: List<CsMsg>,
    isLoading: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.PullToLoad,
    scrollState: LazyListState,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    // 监听滚动状态，自动触发加载更多
    LaunchedEffect(scrollState, loadMoreState) {
        snapshotFlow {
            scrollState.layoutInfo
        }.collect { layoutInfo ->
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

            // 当LoadMore组件可见且状态为PullToLoad时触发加载
            if (totalItems > 0 &&
                lastVisibleItemIndex >= totalItems - 1 &&
                loadMoreState == LoadMoreState.PullToLoad
            ) {
                onLoadMore()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = SpacePaddingSmall)
        ) {
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

            // 加载更多组件
            item {
                ChatLoadMore(
                    state = loadMoreState,
                    listState = scrollState,
                    onRetry = onLoadMore
                )
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

/**
 * 单条消息组件
 *
 * @param onAuthorClick 点击作者头像回调
 * @param msg 消息数据
 * @param isUserMe 是否为当前用户发送的消息
 * @param isFirstMessageByAuthor 是否为该作者的第一条消息
 * @param isLastMessageByAuthor 是否为该作者的最后一条消息
 */
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
            // 根据消息类型使用不同的头像
            val imageUrl =
                if (msg.adminUserHeadImg.isEmpty()) msg.avatarUrl else msg.adminUserHeadImg
            NetWorkImage(
                model = imageUrl,
                modifier = Modifier
                    .clickable(onClick = { onAuthorClick(if (msg.adminUserName.isEmpty()) msg.nickName else msg.adminUserName) })
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

/**
 * 作者信息和文本消息组件
 *
 * @param msg 消息数据
 * @param isUserMe 是否为当前用户发送的消息
 * @param isFirstMessageByAuthor 是否为该作者的第一条消息
 * @param isLastMessageByAuthor 是否为该作者的最后一条消息
 * @param authorClicked 点击作者回调
 * @param modifier 修饰符
 */
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

/**
 * 作者名称和时间戳组件
 *
 * @param msg 消息数据
 * @param isUserMe 是否为当前用户发送的消息
 */
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
            // 为客服消息显示名称，优先使用adminUserName，如果为空则使用nickName
            val displayName =
                if (msg.adminUserName.isEmpty()) msg.nickName else msg.adminUserName
            AppText(
                text = displayName,
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

/**
 * 日期头部组件
 *
 * @param dayString 日期字符串
 */
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

/**
 * 日期头部分割线组件
 */
@Composable
private fun RowScope.DayHeaderLine() {
    HorizontalDivider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}

/**
 * 聊天气泡组件
 *
 * @param message 消息数据
 * @param isUserMe 是否为当前用户发送的消息
 */
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

/**
 * 跳转到底部按钮组件
 *
 * @param enabled 是否启用按钮
 * @param onClicked 点击回调
 * @param modifier 修饰符
 */
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
                .clip(ShapeExtraLarge)
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
        ChatScreen()
    }
}

/**
 * 客服聊天界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun ChatScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        ChatScreen()
    }
}