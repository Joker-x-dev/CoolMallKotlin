package com.joker.coolmall.feature.cs.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.component.AppColumn
import com.joker.coolmall.core.designsystem.component.AppRow
import com.joker.coolmall.core.designsystem.component.FullScreenBox
import com.joker.coolmall.core.designsystem.theme.AppTheme
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
import com.joker.coolmall.core.ui.component.image.Avatar
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.tag.Tag
import com.joker.coolmall.core.ui.component.tag.TagStyle
import com.joker.coolmall.core.ui.component.tag.TagType
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.core.util.time.TimeUtils
import com.joker.coolmall.feature.cs.R
import com.joker.coolmall.feature.cs.component.ChatInputArea
import com.joker.coolmall.feature.cs.component.ChatLoadMore
import com.joker.coolmall.feature.cs.viewmodel.ChatViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 客服聊天路由
 *
 * @param viewModel 客服聊天 ViewModel
 * @author Joker.X
 */
@Composable
internal fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel()
) {
    // 从ViewModel收集UI状态
    // 页面UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 消息列表
    val messages by viewModel.messages.collectAsState()
    // 是否正在加载历史消息
    val isLoadingHistory by viewModel.isLoadingHistory.collectAsState()
    // 加载更多状态
    val loadMoreState by viewModel.loadMoreState.collectAsState()
    // 输入框文本
    val inputText by viewModel.inputText.collectAsState()
    // 新消息ID集合
    val newMessageIds by viewModel.newMessageIds.collectAsState()

    ChatScreen(
        uiState = uiState,
        messages = messages,
        isLoadingHistory = isLoadingHistory,
        loadMoreState = loadMoreState,
        inputText = inputText,
        newMessageIds = newMessageIds,
        onBackClick = viewModel::navigateBack,
        onRefresh = viewModel::retryRequest,
        onLoadMore = viewModel::loadMoreMessages,
        onSendMessage = viewModel::sendMessage,
        onInputTextChange = viewModel::updateInputText,
        onMarkAsRead = viewModel::markMessagesAsRead,
        newMessageEvent = viewModel.newMessageEvent,
        onClearMessageAnimation = viewModel::clearMessageAnimation
    )
}

/**
 * 客服聊天界面
 *
 * @param uiState 聊天界面状态
 * @param messages 消息列表
 * @param isLoadingHistory 是否正在加载历史消息
 * @param loadMoreState 加载更多状态
 * @param inputText 输入框文本
 * @param newMessageIds 新消息ID集合
 * @param onBackClick 返回按钮回调
 * @param onRefresh 刷新消息回调
 * @param onLoadMore 加载更多消息回调
 * @param onSendMessage 发送消息回调
 * @param onInputTextChange 输入框文本变化回调
 * @param onMarkAsRead 标记消息已读回调
 * @param newMessageEvent 新消息事件流
 * @param onClearMessageAnimation 清除消息动画状态回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatScreen(
    uiState: BaseNetWorkUiState<Unit> = BaseNetWorkUiState.Loading,
    messages: List<CsMsg> = emptyList(),
    isLoadingHistory: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    inputText: String = "",
    newMessageIds: Set<Long> = emptySet(),
    onBackClick: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    onSendMessage: () -> Unit = {},
    onInputTextChange: (String) -> Unit = {},
    onMarkAsRead: () -> Unit = {},
    newMessageEvent: kotlinx.coroutines.flow.Flow<Unit>? = null,
    onClearMessageAnimation: (Long) -> Unit = {}
) {
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

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

        BaseNetWorkView(
            uiState = uiState,
            padding = paddingValues,
            onRetry = onRefresh,
        ) {
            ChatContentView(
                messages = messages,
                isLoadingHistory = isLoadingHistory,
                loadMoreState = loadMoreState,
                inputText = inputText,
                newMessageIds = newMessageIds,
                onLoadMore = onLoadMore,
                onSendMessage = onSendMessage,
                onInputTextChange = onInputTextChange,
                onClearMessageAnimation = onClearMessageAnimation,
                newMessageEvent = newMessageEvent,
            )
        }
    }
}

/**
 * 聊天主视图
 *
 * @param modifier 修饰符
 * @param messages 消息列表
 * @param isLoadingHistory 是否正在加载历史消息
 * @param loadMoreState 加载更多状态
 * @param inputText 输入框文本
 * @param newMessageIds 新消息ID集合，用于控制动画
 * @param onLoadMore 加载更多消息回调
 * @param onSendMessage 发送消息回调
 * @param onInputTextChange 输入框文本变化回调
 * @param onClearMessageAnimation 清除消息动画状态回调
 * @param newMessageEvent 新消息事件流，用于触发自动滚动
 * @author Joker.X
 */
@Composable
private fun ChatContentView(
    modifier: Modifier = Modifier,
    messages: List<CsMsg>,
    isLoadingHistory: Boolean,
    loadMoreState: LoadMoreState,
    inputText: String,
    newMessageIds: Set<Long>,
    onLoadMore: () -> Unit,
    onSendMessage: () -> Unit,
    onInputTextChange: (String) -> Unit,
    onClearMessageAnimation: (Long) -> Unit,
    newMessageEvent: kotlinx.coroutines.flow.Flow<Unit>? = null,
) {
    val scrollState = rememberLazyListState()

    // 监听新消息事件，自动滚动到底部
    LaunchedEffect(newMessageEvent) {
        newMessageEvent?.collect {
            // 添加延迟确保消息已经添加到列表中
            delay(50)

            // 滚动到列表顶部（因为是倒序排列，所以顶部是最新消息）
            // 如果已经在顶部，先稍微向下滚动一点再回到顶部，创造动画效果
            if (scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset == 0) {
                scrollState.scrollToItem(0, 100) // 先向下偏移一点
                delay(16) // 一帧的时间
            }
            scrollState.animateScrollToItem(0)
        }
    }

    FullScreenBox(
        modifier = modifier
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
                    newMessageIds = newMessageIds,
                    onLoadMore = onLoadMore,
                    onClearMessageAnimation = onClearMessageAnimation
                )
            }

            // 输入框域组件
            ChatInputArea(
                inputText = inputText,
                onInputTextChange = onInputTextChange,
                onSendMessage = {
                    onSendMessage()
                },
                modifier = Modifier.fillMaxWidth()
            )
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
 * @param newMessageIds 新消息ID集合
 * @param onLoadMore 加载更多回调
 * @param onClearMessageAnimation 清除消息动画状态回调
 * @author Joker.X
 */
@Composable
fun MessageList(
    messages: List<CsMsg>,
    isLoading: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.PullToLoad,
    scrollState: LazyListState,
    newMessageIds: Set<Long>,
    onLoadMore: () -> Unit = {},
    onClearMessageAnimation: (Long) -> Unit
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
            itemsIndexed(
                items = messages,
                key = { _, message -> message.id }
            ) { index, message ->
                // 使用新的方法来判断是否显示时间头部，解决分页加载问题
                val shouldShowTimeHeader = TimeUtils.shouldShowTimeHeaderInList(messages, index)

                // 显示时间头部（如果需要且时间字符串有效）
                if (shouldShowTimeHeader) {
                    val timeString = TimeUtils.formatChatTime(message.createTime)
                    if (timeString.isNotEmpty()) {
                        DayHeader(dayString = timeString)
                    }
                }

                Message(
                    msg = message,
                    isUserMe = message.type == 0, // 0-反馈(用户), 1-回复(客服)
                    isFirstMessageByAuthor = true, // 简化处理
                    isLastMessageByAuthor = true, // 简化处理
                    isNewMessage = newMessageIds.contains(message.id),
                    onAnimationFinished = { onClearMessageAnimation(message.id) }
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
        // 当滚动超过3个消息项时显示按钮
        val jumpToBottomButtonEnabled by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex > 2
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
 * @param msg 消息数据
 * @param isUserMe 是否为当前用户发送的消息
 * @param isFirstMessageByAuthor 是否为该作者的第一条消息
 * @param isLastMessageByAuthor 是否为该作者的最后一条消息
 * @param isNewMessage 是否为新消息，用于控制进入动画
 * @param onAnimationFinished 动画完成回调，用于清除新消息状态
 * @author Joker.X
 */
@Composable
fun Message(
    msg: CsMsg,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    isNewMessage: Boolean = false,
    onAnimationFinished: () -> Unit = {},
) {
    // 作者之间的间距修饰符
    val spaceBetweenAuthors =
        if (isLastMessageByAuthor) Modifier.padding(top = SpaceVerticalSmall) else Modifier

    // 动画状态控制：新消息初始不可见，旧消息直接可见
    var visible by remember { mutableStateOf(!isNewMessage) }

    // 如果是新消息，启动进入动画
    LaunchedEffect(isNewMessage) {
        if (isNewMessage) {
            visible = true
            // 等待动画完成后通知清除新消息状态
            delay(500) // 等待动画完成
            onAnimationFinished()
        }
    }

    // 消息进入动画：用户消息从右侧滑入，客服消息从左侧滑入
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            // 根据消息发送者决定滑入方向：用户消息从右侧，客服消息从左侧
            initialOffsetX = { fullWidth -> if (isUserMe) fullWidth else -fullWidth },
            animationSpec = tween(
                durationMillis = 400,
                easing = FastOutSlowInEasing
            )
        ) + fadeIn(
            // 配合淡入效果，稍有延迟使动画更自然
            animationSpec = tween(
                durationMillis = 300,
                delayMillis = 100,
                easing = FastOutSlowInEasing
            )
        )
    ) {
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
                Avatar(
                    avatarUrl = imageUrl,
                    size = 36.dp,
                    modifier = Modifier.align(Alignment.Top)
                )

                // 头像与消息之间的间距
                SpaceHorizontalSmall()
            }

            AuthorAndTextMessage(
                msg = msg,
                isUserMe = isUserMe,
                isFirstMessageByAuthor = isFirstMessageByAuthor,
                isLastMessageByAuthor = isLastMessageByAuthor,
                modifier = Modifier.weight(1f, fill = false),
            )

            if (isUserMe && isLastMessageByAuthor) {
                // 头像与消息之间的间距
                SpaceHorizontalSmall()

                // 用户头像，右侧显示
                Avatar(
                    avatarUrl = msg.avatarUrl,
                    size = 36.dp,
                    modifier = Modifier.align(Alignment.Top)
                )
            }
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
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
fun AuthorAndTextMessage(
    msg: CsMsg,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
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
 * 作者名称组件（移除时间戳显示）
 *
 * @param msg 消息数据
 * @param isUserMe 是否为当前用户发送的消息
 * @author Joker.X
 */
@Composable
private fun AuthorNameTimestamp(msg: CsMsg, isUserMe: Boolean) {
    // 只显示用户名，不显示时间
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
        } else {
            // 为用户消息显示用户名
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
 * @author Joker.X
 */
@Composable
fun DayHeader(dayString: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SpacePaddingSmall),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(horizontal = SpacePaddingMedium)
        ) {
            AppText(
                text = dayString,
                size = TextSize.BODY_SMALL,
                type = TextType.TERTIARY,
                modifier = Modifier.padding(
                    horizontal = SpacePaddingSmall,
                    vertical = 4.dp
                ),
            )
        }
    }
}

/**
 * 聊天气泡组件
 *
 * @param message 消息数据
 * @param isUserMe 是否为当前用户发送的消息
 * @author Joker.X
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
        // 根据消息内容类型显示不同内容
        when (message.content.type.lowercase()) {
            "image" -> {
                // 显示图片消息
                NetWorkImage(
                    model = message.content.data,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(200.dp),
                    cornerShape = RoundedCornerShape(12.dp),
                    contentScale = ContentScale.Crop
                )
            }

            else -> {
                // 显示文本消息
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
    }
}

/**
 * 跳转到底部按钮组件
 *
 * @param enabled 是否启用按钮
 * @param onClicked 点击回调
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
fun JumpToBottom(
    enabled: Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.animation.AnimatedVisibility(
        visible = enabled,
        enter = androidx.compose.animation.fadeIn(androidx.compose.animation.core.tween(300)) +
                androidx.compose.animation.scaleIn(
                    initialScale = 0.8f,
                    animationSpec = androidx.compose.animation.core.spring(
                        dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
                        stiffness = androidx.compose.animation.core.Spring.StiffnessLow
                    )
                ),
        exit = androidx.compose.animation.fadeOut(androidx.compose.animation.core.tween(200)) +
                androidx.compose.animation.scaleOut(
                    targetScale = 0.8f,
                    animationSpec = androidx.compose.animation.core.tween(200)
                ),
        modifier = modifier
    ) {
        Tag(
            stringResource(R.string.back_to_bottom),
            shape = ShapeExtraLarge,
            type = TagType.PRIMARY,
            style = TagStyle.LIGHT,
            modifier = Modifier
                .padding(bottom = SpacePaddingSmall)
                .clip(ShapeExtraLarge)
                .clickable(onClick = onClicked)
        )
    }
}

/**
 * 客服聊天界面浅色主题预览
 *
 * @author Joker.X
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
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun ChatScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        ChatScreen()
    }
}