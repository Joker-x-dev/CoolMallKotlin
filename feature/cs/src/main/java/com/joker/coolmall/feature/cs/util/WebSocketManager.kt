package com.joker.coolmall.feature.cs.util

import com.joker.coolmall.core.model.entity.CsMsg
import com.joker.coolmall.core.util.log.LogUtils
import com.joker.coolmall.feature.cs.state.WebSocketConnectionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

private const val TAG = "WebSocketManager"

/**
 * WebSocket管理器
 * 负责处理WebSocket连接、消息发送和接收
 *
 * @author Joker.X
 */
class WebSocketManager {

    // WebSocket连接状态
    private val _connectionState =
        MutableStateFlow<WebSocketConnectionState>(WebSocketConnectionState.Disconnected)
    val connectionState: StateFlow<WebSocketConnectionState> = _connectionState.asStateFlow()

    // WebSocket客户端
    private var webSocketClient: OkHttpClient? = null
    private var webSocket: WebSocket? = null

    // 重试次数计数
    private var retryCount = 0
    private val maxRetries = 3

    // 回调接口
    private var onMessageReceived: ((CsMsg) -> Unit)? = null
    private var onConnectionStateChanged: ((WebSocketConnectionState) -> Unit)? = null

    /**
     * 设置消息接收回调
     *
     * @param callback 消息接收回调函数
     * @author Joker.X
     */
    fun setOnMessageReceived(callback: (CsMsg) -> Unit) {
        onMessageReceived = callback
    }

    /**
     * 设置连接状态变化回调
     *
     * @param callback 连接状态变化回调函数
     * @author Joker.X
     */
    fun setOnConnectionStateChanged(callback: (WebSocketConnectionState) -> Unit) {
        onConnectionStateChanged = callback
    }

    /**
     * 建立WebSocket连接
     *
     * @param token 用户认证Token
     * @param scope 协程作用域
     * @author Joker.X
     */
    fun connect(token: String, scope: CoroutineScope) {
        if (_connectionState.value == WebSocketConnectionState.Connecting) {
            LogUtils.d(TAG, "WebSocket正在连接中，忽略重复连接请求")
            return
        }

        updateConnectionState(WebSocketConnectionState.Connecting)
        LogUtils.d(TAG, "开始建立WebSocket连接")

        scope.launch {
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
                    LogUtils.d(TAG, "收到WebSocket消息: ${text}...")
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
                    updateConnectionState(WebSocketConnectionState.Error(t.message ?: "连接错误"))

                    // 尝试重连
                    if (retryCount < maxRetries) {
                        retryCount++
                        retryConnection(scope)
                    }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    LogUtils.d(TAG, "WebSocket连接关闭: code=$code, reason=$reason")
                    updateConnectionState(WebSocketConnectionState.Disconnected)
                }
            })
        }
    }

    /**
     * 重试连接
     *
     * @param scope 协程作用域
     * @author Joker.X
     */
    private fun retryConnection(scope: CoroutineScope) {
        scope.launch {
            LogUtils.d(
                TAG,
                "WebSocket连接失败，${1000 * retryCount}毫秒后尝试重连 (第$retryCount)次"
            )
            delay(1000L * retryCount) // 延迟时间随重试次数增加
            // 注意：这里需要重新获取token，所以需要外部调用connect方法
        }
    }

    /**
     * 处理WebSocket消息
     *
     * @param text 消息文本
     * @author Joker.X
     */
    private fun handleWebSocketMessage(text: String) {
        try {
            when {
                // 连接成功消息: 40/cs,{"sid":"708fb2ed-6d08-445c-9264-c57c521eb3f7"}
                text.startsWith("40/cs,") -> {
                    LogUtils.d(TAG, "WebSocket认证成功")
                    updateConnectionState(WebSocketConnectionState.Connected)
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
                    onMessageReceived?.invoke(message)
                }

                // 消息通知: 42/cs,["msg",{消息内容}]
                text.startsWith("42/cs,[\"msg\",") -> {
                    val messageContent =
                        text.substringAfter("42/cs,[\"msg\",").substringBeforeLast("]")
                    val message = Json.decodeFromString<CsMsg>(messageContent)
                    LogUtils.d(TAG, "收到消息通知(42/cs): id=${message.id}, type=${message.type}")
                    onMessageReceived?.invoke(message)
                }

                // 连接成功消息: 42/cs,["message","连接成功"]
                text.contains("42/cs,[\"message\",\"连接成功\"]") -> {
                    LogUtils.d(TAG, "收到连接成功消息: $text")
                    updateConnectionState(WebSocketConnectionState.Connected)
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
     * 发送消息
     *
     * @param sessionId 会话ID
     * @param content 消息内容
     * @param type 消息类型
     * @return 是否发送成功
     * @author Joker.X
     */
    fun sendMessage(sessionId: Long, content: String, type: String = "text"): Boolean {
        if (_connectionState.value != WebSocketConnectionState.Connected) {
            LogUtils.e(TAG, "发送消息失败: WebSocket未连接")
            return false
        }

        // 构建发送消息
        val sendMessage =
            """42/cs,["send",{"sessionId":$sessionId,"content":{"type":"$type","data":"$content"}}]"""

        LogUtils.d(TAG, "发送消息: ${sendMessage.take(50)}...")

        // 通过WebSocket发送
        val success = webSocket?.send(sendMessage) ?: false
        if (!success) {
            LogUtils.e(TAG, "消息发送失败")
            updateConnectionState(WebSocketConnectionState.Error("消息发送失败"))
        } else {
            LogUtils.d(TAG, "消息发送成功")
        }

        return success
    }

    /**
     * 断开WebSocket连接
     *
     * @author Joker.X
     */
    fun disconnect() {
        LogUtils.d(TAG, "断开WebSocket连接")
        webSocket?.close(1000, "正常关闭")
        webSocket = null
        webSocketClient?.dispatcher?.executorService?.shutdown()
        webSocketClient = null
        updateConnectionState(WebSocketConnectionState.Disconnected)
    }

    /**
     * 更新连接状态
     *
     * @param state 新的连接状态
     * @author Joker.X
     */
    private fun updateConnectionState(state: WebSocketConnectionState) {
        _connectionState.value = state
        onConnectionStateChanged?.invoke(state)
    }

    /**
     * 获取当前连接状态
     *
     * @return 是否已连接
     * @author Joker.X
     */
    fun isConnected(): Boolean {
        return _connectionState.value == WebSocketConnectionState.Connected
    }
}