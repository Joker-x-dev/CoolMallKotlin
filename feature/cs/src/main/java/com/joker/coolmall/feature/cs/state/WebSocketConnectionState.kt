package com.joker.coolmall.feature.cs.state

/**
 * WebSocket连接状态
 *
 * @author Joker.X
 */
sealed class WebSocketConnectionState {
    object Disconnected : WebSocketConnectionState()
    object Connecting : WebSocketConnectionState()
    object Connected : WebSocketConnectionState()
    data class Error(val message: String) : WebSocketConnectionState()
}