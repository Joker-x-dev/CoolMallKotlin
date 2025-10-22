package com.joker.coolmall.core.util.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.joker.coolmall.core.designsystem.R

/**
 * 通知工具类
 * 用于发送系统通知，支持普通通知和验证码通知
 *
 * @author Joker.X
 */
object NotificationUtil {

    private const val DEFAULT_CHANNEL_ID = "default_channel"
    private const val VERIFICATION_CODE_CHANNEL_ID = "verification_code_channel"

    /**
     * 初始化通知渠道
     * 建议在应用启动时调用此方法
     *
     * @param context 上下文
     * @author Joker.X
     */
    fun initNotificationChannels(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 创建默认通知渠道
        val defaultChannel = NotificationChannel(
            DEFAULT_CHANNEL_ID,
            "默认通知",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "应用的默认通知渠道"
        }

        // 创建验证码通知渠道
        val verificationCodeChannel = NotificationChannel(
            VERIFICATION_CODE_CHANNEL_ID,
            "验证码通知",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "用于显示短信验证码的通知渠道"
        }

        // 注册通知渠道
        notificationManager.createNotificationChannels(
            listOf(
                defaultChannel,
                verificationCodeChannel
            )
        )
    }

    /**
     * 发送普通通知
     *
     * @param context 上下文
     * @param title 通知标题
     * @param content 通知内容
     * @param iconResId 通知图标资源ID，默认使用系统信息图标
     * @param intent 点击通知时触发的Intent，默认为null
     * @param autoCancel 点击后是否自动取消通知，默认为true
     * @return 通知ID
     * @author Joker.X
     */
    @SuppressLint("MissingPermission")
    fun sendNotification(
        context: Context,
        title: String,
        content: String,
        iconResId: Int = R.drawable.ic_logo,
        intent: Intent? = null,
        autoCancel: Boolean = true
    ): Int {
        // 确保通知渠道已创建（Android 8.0及以上需要）
        ensureChannelExists(
            context,
            DEFAULT_CHANNEL_ID,
            "默认通知",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        // 构建通知
        val builder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
            .setSmallIcon(iconResId)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(autoCancel)

        // 设置点击意图
        intent?.let {
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                it,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            builder.setContentIntent(pendingIntent)
        }

        // 生成通知ID
        val notificationId = System.currentTimeMillis().toInt()

        // 发送通知
        NotificationManagerCompat.from(context).apply {
            notify(notificationId, builder.build())
        }

        return notificationId
    }

    /**
     * 发送验证码通知
     *
     * @param context 上下文
     * @param code 验证码
     * @param iconResId 通知图标资源ID，默认使用系统信息图标
     * @return 通知ID
     * @author Joker.X
     */
    @SuppressLint("MissingPermission")
    fun sendVerificationCodeNotification(
        context: Context,
        code: String,
        iconResId: Int = R.drawable.ic_logo
    ): Int {
        // 确保通知渠道已创建（Android 8.0及以上需要）
        ensureChannelExists(
            context,
            VERIFICATION_CODE_CHANNEL_ID,
            "验证码通知",
            NotificationManager.IMPORTANCE_HIGH
        )

        // 构建通知
        val builder = NotificationCompat.Builder(context, VERIFICATION_CODE_CHANNEL_ID)
            .setSmallIcon(iconResId)
            .setContentTitle("验证码")
            .setContentText("您的验证码是: $code")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // 生成通知ID
        val notificationId = System.currentTimeMillis().toInt()

        // 发送通知
        NotificationManagerCompat.from(context).apply {
            notify(notificationId, builder.build())
        }

        return notificationId
    }

    /**
     * 取消通知
     *
     * @param context 上下文
     * @param notificationId 通知ID
     * @author Joker.X
     */
    fun cancelNotification(context: Context, notificationId: Int) {
        NotificationManagerCompat.from(context).cancel(notificationId)
    }

    /**
     * 取消所有通知
     *
     * @param context 上下文
     * @author Joker.X
     */
    fun cancelAllNotifications(context: Context) {
        NotificationManagerCompat.from(context).cancelAll()
    }

    /**
     * 确保通知渠道存在（仅适用于Android 8.0及以上）
     *
     * @param context 上下文
     * @param channelId 渠道ID
     * @param channelName 渠道名称
     * @param importance 重要性级别
     * @author Joker.X
     */
    private fun ensureChannelExists(
        context: Context,
        channelId: String,
        channelName: String,
        importance: Int
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 检查渠道是否已存在
        val existingChannel = notificationManager.getNotificationChannel(channelId)
        if (existingChannel == null) {
            // 创建渠道
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = when (channelId) {
                    DEFAULT_CHANNEL_ID -> "应用的默认通知渠道"
                    VERIFICATION_CODE_CHANNEL_ID -> "用于显示短信验证码的通知渠道"
                    else -> "通知渠道"
                }
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
} 