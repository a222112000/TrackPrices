package com.alselwi.trackprices.data.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketManager {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private val _incoming = MutableSharedFlow<String>()
    val incoming: SharedFlow<String> = _incoming

    private val _connected = MutableStateFlow(false)
    val connected: StateFlow<Boolean> = _connected

    fun connect(){
        val request = Request.Builder()
            .url("wss://ws.postman-echo.com/raw")
            .build()
        webSocket = client.newWebSocket(request, object : WebSocketListener(){
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                _connected.value = true
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                CoroutineScope(Dispatchers.IO).launch {
                    _incoming.emit(text)
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                _connected.value = false
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                _connected.value = false
            }
        })
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    fun disconnect(){
        webSocket?.close(1000,"Closed")
        _connected.value = false
    }
}