package com.joker.coolmall.feature.cs.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.joker.coolmall.feature.cs.R

/**
 * 聊天音效管理器
 * 负责播放发送和接收消息的提示音
 *
 * @param context 应用上下文
 * @author Joker.X
 */
class ChatSoundManager(private val context: Context) {

    private var soundPool: SoundPool? = null
    private var sendSoundId: Int = 0
    private var receiveSoundId: Int = 0
    private var isInitialized = false

    init {
        initSoundPool()
    }

    /**
     * 初始化SoundPool
     *
     * @author Joker.X
     */
    private fun initSoundPool() {
        try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(2) // 最多同时播放2个音效
                .setAudioAttributes(audioAttributes)
                .build()

            soundPool?.let { pool ->
                // 加载音效文件
                sendSoundId = pool.load(context, R.raw.send, 1)
                receiveSoundId = pool.load(context, R.raw.receive, 1)

                // 设置加载完成监听器
                pool.setOnLoadCompleteListener { _, _, status ->
                    if (status == 0) {
                        isInitialized = true
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 播放发送消息音效
     *
     * @author Joker.X
     */
    fun playMessageSentSound() {
        if (isInitialized && sendSoundId != 0) {
            soundPool?.play(
                sendSoundId,
                1.0f, // 左声道音量
                1.0f, // 右声道音量
                1,    // 优先级
                0,    // 循环次数（0表示不循环）
                1.0f  // 播放速率
            )
        }
    }

    /**
     * 播放接收消息音效
     *
     * @author Joker.X
     */
    fun playMessageReceivedSound() {
        if (isInitialized && receiveSoundId != 0) {
            soundPool?.play(
                receiveSoundId,
                1.0f, // 左声道音量
                1.0f, // 右声道音量
                1,    // 优先级
                0,    // 循环次数（0表示不循环）
                1.0f  // 播放速率
            )
        }
    }

    /**
     * 释放资源
     *
     * @author Joker.X
     */
    fun release() {
        soundPool?.release()
        soundPool = null
        isInitialized = false
        sendSoundId = 0
        receiveSoundId = 0
    }
}