package com.joker.coolmall.feature.cs.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.CustomerServiceRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.CsMsg
import com.joker.coolmall.core.model.request.MessagePageRequest
import com.joker.coolmall.core.model.request.ReadMessageRequest
import com.joker.coolmall.core.util.log.LogUtils
import com.joker.coolmall.feature.cs.state.WebSocketConnectionState
import com.joker.coolmall.feature.cs.util.ChatSoundManager
import com.joker.coolmall.feature.cs.util.WebSocketManager
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ChatViewModel"

/**
 * 客服聊天 ViewModel
 *
 * @param customerServiceRepository 客服仓库
 * @param appState 应用状态
 * @param context 应用上下文
 * @param navigator 导航器
 * @author Joker.X
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val customerServiceRepository: CustomerServiceRepository,
    appState: AppState,
    @param:ApplicationContext private val context: Context,
    navigator: AppNavigator,
) : BaseViewModel(navigator, appState) {

    // 页面UI状态
    private val _uiState = MutableStateFlow<BaseNetWorkUiState<Unit>>(BaseNetWorkUiState.Loading)
    val uiState: StateFlow<BaseNetWorkUiState<Unit>> = _uiState.asStateFlow()

    // 会话ID (内部使用)
    private val _sessionId = MutableStateFlow<Long>(0)

    // 聊天消息列表 (倒序排列，最新的在前面)
    private val _messages = MutableStateFlow<List<CsMsg>>(emptyList())
    val messages: StateFlow<List<CsMsg>> = _messages.asStateFlow()

    // 新消息ID集合，用于控制动画
    private val _newMessageIds = MutableStateFlow<Set<Long>>(emptySet())
    val newMessageIds: StateFlow<Set<Long>> = _newMessageIds.asStateFlow()

    // WebSocket连接状态 (内部使用)
    private val _connectionState =
        MutableStateFlow<WebSocketConnectionState>(WebSocketConnectionState.Disconnected)

    // 是否正在加载历史消息
    private val _isLoadingHistory = MutableStateFlow(false)
    val isLoadingHistory: StateFlow<Boolean> = _isLoadingHistory.asStateFlow()

    // 加载更多状态
    private val _loadMoreState = MutableStateFlow<LoadMoreState>(LoadMoreState.PullToLoad)
    val loadMoreState: StateFlow<LoadMoreState> = _loadMoreState.asStateFlow()

    // 新消息事件流
    private val _newMessageEvent = MutableSharedFlow<Unit>()
    val newMessageEvent: SharedFlow<Unit> = _newMessageEvent.asSharedFlow()

    // 输入框文本状态
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    // 分页相关
    private var currentPage = 1
    private val pageSize = 10
    private var hasMoreData = true

    // WebSocket管理器
    private val webSocketManager = WebSocketManager()

    // 音效管理器
    private val chatSoundManager = ChatSoundManager(context)

    init {
        // 设置WebSocket回调
        setupWebSocketCallbacks()

        // 创建会话
        createSession()
    }

    /**
     * 设置WebSocket回调
     *
     * @author Joker.X
     */
    private fun setupWebSocketCallbacks() {
        webSocketManager.setOnMessageReceived { message ->
            addNewMessage(message)
        }

        webSocketManager.setOnConnectionStateChanged { state ->
            _connectionState.value = state
        }
    }

    /**
     * 创建客服会话
     *
     * @author Joker.X
     */
    private fun createSession() {
        LogUtils.d(TAG, "开始创建会话")

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = customerServiceRepository.createSession().asResult(),
            onData = { session ->
                _sessionId.value = session.id
                LogUtils.d(TAG, "会话创建成功: sessionId = ${session.id}")

                // 设置成功状态
                _uiState.value = BaseNetWorkUiState.Success(Unit)

                // 获取历史消息
                loadHistoryMessages()

                // 建立WebSocket连接
                connectWebSocket()

                // 标记消息为已读
                markMessagesAsRead()
            },
            onError = { message, exception ->
                LogUtils.e(TAG, "会话创建失败: $message")
                _uiState.value = BaseNetWorkUiState.Error(message, exception)
                _connectionState.value = WebSocketConnectionState.Error("创建会话失败")
            }
        )
    }

    /**
     * 加载历史消息
     *
     * @author Joker.X
     */
    fun loadHistoryMessages() {
        if (_isLoadingHistory.value) return

        val sessionId = _sessionId.value
        if (sessionId <= 0) return

        LogUtils.d(TAG, "开始加载历史消息: sessionId = $sessionId, page = $currentPage")
        _isLoadingHistory.value = true

        // 如果是加载更多，设置加载状态
        if (currentPage > 1) {
            _loadMoreState.value = LoadMoreState.Loading
        }

        val params = MessagePageRequest(
            sessionId = sessionId,
            page = currentPage,
            size = pageSize
        )

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = customerServiceRepository.getMessagePage(params).asResult(),
            onData = { data ->
                val newMessages = data.list ?: emptyList()
                val pagination = data.pagination

                LogUtils.d(TAG, "历史消息加载成功: ${newMessages.size} 条消息")

                // 计算是否还有更多数据
                hasMoreData = if (pagination != null) {
                    val total = pagination.total ?: 0
                    val size = pagination.size ?: pageSize
                    val currentPageNum = pagination.page ?: currentPage
                    size * currentPageNum < total
                } else {
                    newMessages.size >= pageSize
                }

                if (currentPage == 1) {
                    // 首次加载或刷新
                    _messages.value = newMessages
                } else {
                    // 加载更多 - 将新消息添加到列表末尾（因为是倒序显示）
                    // 去重处理：过滤掉已存在的消息
                    val currentMessages = _messages.value
                    val existingIds = currentMessages.map { it.id }.toSet()
                    val uniqueNewMessages = newMessages.filter { it.id !in existingIds }
                    _messages.value = currentMessages + uniqueNewMessages
                }

                _isLoadingHistory.value = false

                // 更新加载更多状态
                _loadMoreState.value =
                    if (hasMoreData) LoadMoreState.PullToLoad else LoadMoreState.NoMore
            },
            onError = { message, _ ->
                LogUtils.e(TAG, "历史消息加载失败: $message")
                _isLoadingHistory.value = false

                if (currentPage > 1) {
                    // 加载更多失败，回退页码
                    currentPage--
                    _loadMoreState.value = LoadMoreState.Error
                }
            }
        )
    }

    /**
     * 加载更多历史消息
     *
     * @author Joker.X
     */
    fun loadMoreMessages() {
        // 检查是否可以加载更多
        if (_loadMoreState.value == LoadMoreState.Loading ||
            _loadMoreState.value == LoadMoreState.NoMore ||
            !hasMoreData
        ) {
            return
        }

        currentPage++
        loadHistoryMessages()
    }

    /**
     * 刷新消息列表
     *
     * @author Joker.X
     */
    fun refreshMessages() {
        if (_isLoadingHistory.value) return

        currentPage = 1
        hasMoreData = true
        loadHistoryMessages()
    }

    /**
     * 建立WebSocket连接
     *
     * @author Joker.X
     */
    fun connectWebSocket() {
        val token = appState?.auth?.value?.token ?: ""
        webSocketManager.connect(token, viewModelScope)
    }

    /**
     * 添加新消息到列表
     *
     * @param message 消息数据
     * @author Joker.X
     */
    private fun addNewMessage(message: CsMsg) {
        val currentMessages = _messages.value.toMutableList()

        // 检查消息是否已存在
        if (currentMessages.none { it.id == message.id }) {
            currentMessages.add(0, message) // 新消息在顶部，因为列表是倒序显示
            _messages.value = currentMessages

            // 将新消息ID添加到集合中，用于控制动画
            _newMessageIds.value = _newMessageIds.value + message.id

            // 播放接收消息音效（客服回复）
            if (message.type == 1) { // 1-回复(客服)
                chatSoundManager.playMessageReceivedSound()
            }

            // 触发新消息事件
            viewModelScope.launch {
                _newMessageEvent.emit(Unit)
            }
        }
    }

    /**
     * 更新输入框文本
     *
     * @param text 输入文本
     * @author Joker.X
     */
    fun updateInputText(text: String) {
        _inputText.value = text
    }

    /**
     * 清除消息动画状态
     *
     * @param messageId 消息ID
     * @author Joker.X
     */
    fun clearMessageAnimation(messageId: Long) {
        _newMessageIds.value = _newMessageIds.value - messageId
    }

    /**
     * 发送消息
     *
     * @param content 消息内容
     * @param type 消息类型
     * @author Joker.X
     */
    fun sendMessage(content: String = _inputText.value, type: String = "text") {
        if (content.isBlank()) return

        val sessionId = _sessionId.value
        if (sessionId <= 0) {
            LogUtils.e(TAG, "发送消息失败: 无效的会话ID")
            return
        }

        if (!webSocketManager.isConnected()) {
            LogUtils.e(TAG, "发送消息失败: WebSocket未连接")
            connectWebSocket() // 尝试重新连接
            return
        }

        // 通过WebSocketManager发送消息
        val success = webSocketManager.sendMessage(sessionId, content, type)
        if (success) {
            LogUtils.d(TAG, "消息发送成功")
            // 播放发送消息音效
            chatSoundManager.playMessageSentSound()
            // 清空输入框
            _inputText.value = ""
        } else {
            // 尝试重新连接
            connectWebSocket()
        }
    }

    /**
     * 标记消息为已读
     *
     * @author Joker.X
     */
    fun markMessagesAsRead() {
        viewModelScope.launch {
            // 获取未读消息ID列表
            val unreadMessages = _messages.value.filter { it.status == 0 }
            if (unreadMessages.isEmpty()) return@launch

            val ids = unreadMessages.map { it.id }
            LogUtils.d(TAG, "标记消息已读: ${ids.joinToString()}")
            val request = ReadMessageRequest(ids)

            try {
                customerServiceRepository.readMessage(request).first()
                LogUtils.d(TAG, "消息已标记为已读")
            } catch (e: Exception) {
                LogUtils.e(TAG, "标记消息已读失败", e)
            }
        }
    }

    /**
     * 重试请求
     *
     * @author Joker.X
     */
    fun retryRequest() {
        if (_uiState.value is BaseNetWorkUiState.Error) {
            _uiState.value = BaseNetWorkUiState.Loading
        }
        // 重试创建会话
        createSession()
    }

    /**
     * 断开WebSocket连接
     *
     * @author Joker.X
     */
    fun disconnectWebSocket() {
        webSocketManager.disconnect()
    }

    override fun onCleared() {
        disconnectWebSocket()
        chatSoundManager.release()
        super.onCleared()
    }
}
