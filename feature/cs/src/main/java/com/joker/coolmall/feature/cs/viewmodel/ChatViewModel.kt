package com.joker.coolmall.feature.cs.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.CustomerServiceRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.CsMsg
import com.joker.coolmall.core.model.request.MessagePageRequest
import com.joker.coolmall.core.model.request.ReadMessageRequest
import com.joker.coolmall.core.util.log.LogUtils
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TAG = "ChatViewModel"

/**
 * 客服聊天 ViewModel
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val customerServiceRepository: CustomerServiceRepository,
    private val appState: AppState,
    navigator: AppNavigator,
) : BaseViewModel(navigator) {

    // 会话ID
    private val _sessionId = MutableStateFlow<Long>(0)
    val sessionId: StateFlow<Long> = _sessionId.asStateFlow()

    // 聊天消息列表 (倒序排列，最新的在前面)
    private val _messages = MutableStateFlow<List<CsMsg>>(emptyList())
    val messages: StateFlow<List<CsMsg>> = _messages.asStateFlow()

    // WebSocket连接状态
    private val _connectionState =
        MutableStateFlow<WebSocketConnectionState>(WebSocketConnectionState.Disconnected)
    val connectionState: StateFlow<WebSocketConnectionState> = _connectionState.asStateFlow()

    // 是否正在加载历史消息
    private val _isLoadingHistory = MutableStateFlow(false)
    val isLoadingHistory: StateFlow<Boolean> = _isLoadingHistory.asStateFlow()

    // WebSocket客户端
    private var webSocketClient: OkHttpClient? = null
    private var webSocket: WebSocket? = null

    // 重试次数计数
    private var retryCount = 0
    private val maxRetries = 3

    init {
        // 创建会话
        createSession()
    }

    /**
     * 创建客服会话
     */
    private fun createSession() {
        LogUtils.d(TAG, "开始创建会话")
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = customerServiceRepository.createSession().asResult(),
            onData = { session ->
                _sessionId.value = session.id
                LogUtils.d(TAG, "会话创建成功: sessionId = ${session.id}")

                // 获取历史消息
                loadHistoryMessages()

                // 建立WebSocket连接
                connectWebSocket()
            },
            onError = { message, _ ->
                LogUtils.e(TAG, "会话创建失败: $message")
                _connectionState.value = WebSocketConnectionState.Error("创建会话失败")
            }
        )
    }

    /**
     * 加载历史消息
     */
    fun loadHistoryMessages() {
        if (_isLoadingHistory.value) return

        val sessionId = _sessionId.value
        if (sessionId <= 0) return

        LogUtils.d(TAG, "开始加载历史消息: sessionId = $sessionId")
        _isLoadingHistory.value = true

        val params = MessagePageRequest(
            sessionId = sessionId,
            page = 1,
            size = 20
        )

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = customerServiceRepository.getMessagePage(params).asResult(),
            onData = { data ->
                val messages = data.list ?: emptyList()
                LogUtils.d(TAG, "历史消息加载成功: ${messages.size} 条消息")
                _messages.value = messages
                _isLoadingHistory.value = false
            },
            onError = { message, _ ->
                LogUtils.e(TAG, "历史消息加载失败: $message")
                _isLoadingHistory.value = false
            }
        )
    }

    /**
     * 建立WebSocket连接
     */
    fun connectWebSocket() {
        if (_connectionState.value == WebSocketConnectionState.Connecting) {
            LogUtils.d(TAG, "WebSocket正在连接中，忽略重复连接请求")
            return
        }

        _connectionState.value = WebSocketConnectionState.Connecting
        LogUtils.d(TAG, "开始建立WebSocket连接")

        viewModelScope.launch {
            val token = appState.auth.value?.token ?: ""
            LogUtils.d(TAG, "用户Token: ${token.take(15)}...")

            val request = Request.Builder()
                .url("wss://mall.dusksnow.top/socket.io/?EIO=4&transport=websocket")
                .build()

            // 配置超时和心跳间隔
            // 注意：禁用OkHttp的自动ping机制，我们自己处理心跳
            webSocketClient = OkHttpClient.Builder()
                .pingInterval(0, TimeUnit.SECONDS) // 禁用OkHttp的自动ping
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS) // 增加读取超时时间，避免长时间无消息导致断开
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            webSocket = webSocketClient?.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    LogUtils.d(TAG, "WebSocket连接成功: ${response.code}")
                    retryCount = 0

                    // 发送认证消息
                    val authMessage = """40/cs,{"isAdmin":false,"token":"$token"}"""
                    LogUtils.d(TAG, "发送认证消息: ${authMessage.take(20)}...")
                    webSocket.send(authMessage)
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    LogUtils.d(TAG, "收到WebSocket消息: ${text.take(50)}...")
                    // 如果收到心跳包，立即响应
                    if (text == "2") {
                        LogUtils.d(TAG, "收到心跳包")
                        val success = webSocket.send("3")
                        // 检查心跳响应是否发送成功
                        if (success) {
                            LogUtils.d(TAG, "心跳响应发送成功")
                        } else {
                            LogUtils.e(TAG, "心跳响应发送失败")
                        }
                    }
                    handleWebSocketMessage(text)
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    LogUtils.e(TAG, "WebSocket连接失败: ${t.message}", t)
                    _connectionState.value = WebSocketConnectionState.Error(t.message ?: "连接错误")

                    // 尝试重连
                    if (retryCount < maxRetries) {
                        retryCount++
                        retryConnection()
                    }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    LogUtils.d(TAG, "WebSocket连接关闭: code=$code, reason=$reason")
                    _connectionState.value = WebSocketConnectionState.Disconnected
                }
            })
        }
    }

    /**
     * 重试连接
     */
    private fun retryConnection() {
        viewModelScope.launch {
            LogUtils.d(
                TAG,
                "WebSocket连接失败，${1000 * retryCount}毫秒后尝试重连 (第$retryCount)次"
            )
            delay(1000L * retryCount) // 延迟时间随重试次数增加
            connectWebSocket()
        }
    }

    /**
     * 处理WebSocket消息
     */
    private fun handleWebSocketMessage(text: String) {
        try {
            when {
                // 连接成功消息: 40/cs,{"sid":"708fb2ed-6d08-445c-9264-c57c521eb3f7"}
                text.startsWith("40/cs,") -> {
                    LogUtils.d(TAG, "WebSocket认证成功")
                    _connectionState.value = WebSocketConnectionState.Connected
                }

                // 握手消息: 0{"sid":"708fb2ed-6d08-445c-9264-c57c521eb3f7","upgrades":[],"pingInterval":25000,"pingTimeout":6000000}
                text.startsWith("0{") -> {
                    LogUtils.d(TAG, "WebSocket握手成功")
                    // 握手成功，不做特殊处理
                }

                // 心跳消息: 2
                // 注意：这个处理已经移到onMessage中直接处理，以确保及时响应
                text == "2" -> {
                    // 心跳包的处理已移至onMessage回调中
                    // 这里不再需要额外处理
                }

                // 消息通知: 42["msg",{消息内容}]
                text.startsWith("42[\"msg\",") -> {
                    val messageContent = text.substringAfter("42[\"msg\",").substringBeforeLast("]")
                    val message = Json.decodeFromString<CsMsg>(messageContent)
                    LogUtils.d(TAG, "收到消息通知(42): id=${message.id}, type=${message.type}")
                    addNewMessage(message)
                }

                // 消息通知: 42/cs,["msg",{消息内容}]
                text.startsWith("42/cs,[\"msg\",") -> {
                    val messageContent =
                        text.substringAfter("42/cs,[\"msg\",").substringBeforeLast("]")
                    val message = Json.decodeFromString<CsMsg>(messageContent)
                    LogUtils.d(TAG, "收到消息通知(42/cs): id=${message.id}, type=${message.type}")
                    addNewMessage(message)
                }

                // 连接成功消息: 42/cs,["message","连接成功"]
                text.contains("42/cs,[\"message\",\"连接成功\"]") -> {
                    LogUtils.d(TAG, "收到连接成功消息: $text")
                    _connectionState.value = WebSocketConnectionState.Connected
                }

                // 其他连接状态消息
                text.startsWith("42/cs,[\"message\",") -> {
                    LogUtils.d(TAG, "收到连接状态消息: $text")
                    // 其他连接状态消息，已经在上一个条件处理了连接成功的情况
                }

                else -> {
                    LogUtils.d(TAG, "收到未处理的消息类型: $text")
                }
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, "解析WebSocket消息失败", e)
        }
    }

    /**
     * 添加新消息到列表
     */
    private fun addNewMessage(message: CsMsg) {
        val currentMessages = _messages.value.toMutableList()

        // 检查消息是否已存在
        if (currentMessages.none { it.id == message.id }) {
            currentMessages.add(0, message) // 新消息在顶部，因为列表是倒序显示
            _messages.value = currentMessages
        }
    }

    /**
     * 发送消息
     */
    fun sendMessage(content: String, type: String = "text") {
        val sessionId = _sessionId.value
        if (sessionId <= 0) {
            LogUtils.e(TAG, "发送消息失败: 无效的会话ID")
            return
        }

        if (_connectionState.value != WebSocketConnectionState.Connected) {
            LogUtils.e(TAG, "发送消息失败: WebSocket未连接")
            connectWebSocket() // 尝试重新连接
            return
        }

        // 构建发送消息
        val sendMessage =
            """42/cs,["send",{"sessionId":$sessionId,"content":{"type":"$type","data":"$content"}}]"""

        LogUtils.d(TAG, "发送消息: ${sendMessage.take(50)}...")

        // 通过WebSocket发送
        val success = webSocket?.send(sendMessage) ?: false
        if (!success) {
            LogUtils.e(TAG, "消息发送失败")
            _connectionState.value = WebSocketConnectionState.Error("消息发送失败")

            // 尝试重新连接
            connectWebSocket()
        }
    }

    /**
     * 标记消息为已读
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
     * 断开WebSocket连接
     */
    fun disconnectWebSocket() {
        LogUtils.d(TAG, "断开WebSocket连接")
        webSocket?.close(1000, "正常关闭")
        webSocket = null
        webSocketClient?.dispatcher?.executorService?.shutdown()
        webSocketClient = null
        _connectionState.value = WebSocketConnectionState.Disconnected
    }

    override fun onCleared() {
        disconnectWebSocket()
        super.onCleared()
    }
}

/**
 * WebSocket连接状态
 */
sealed class WebSocketConnectionState {
    object Disconnected : WebSocketConnectionState()
    object Connecting : WebSocketConnectionState()
    object Connected : WebSocketConnectionState()
    data class Error(val message: String) : WebSocketConnectionState()
}