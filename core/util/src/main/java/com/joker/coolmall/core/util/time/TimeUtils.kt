package com.joker.coolmall.core.util.time

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

/**
 * 时间工具类
 *
 * @author Joker.X
 */
object TimeUtils {

    /**
     * 日期格式化器 - 完整日期时间
     */
    private val fullDateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    /**
     * 日期格式化器 - 仅时间
     */
    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    /**
     * 格式化聊天消息时间
     * 如果是今天的消息，只显示时间（HH:mm）
     * 如果是今天之前的消息，显示完整日期时间（yyyy-MM-dd HH:mm）
     *
     * @param dateString 时间字符串，格式为 "yyyy-MM-dd HH:mm:ss" 或 "HH:mm"
     * @return 格式化后的时间字符串
     * @author Joker.X
     */
    fun formatChatTime(dateString: String?): String {
        if (dateString.isNullOrEmpty()) {
            return ""
        }

        return try {
            // 如果已经是简短格式（HH:mm），直接返回
            if (dateString.matches(Regex("^\\d{1,2}:\\d{2}$"))) {
                return dateString
            }

            // 解析完整的时间字符串，支持多种格式
            val date = parseTimeString(dateString) ?: return dateString

            // 判断是否为今天
            if (isToday(date)) {
                timeFormatter.format(date)
            } else {
                fullDateFormatter.format(date)
            }
        } catch (_: Exception) {
            // 解析失败，返回原字符串
            dateString
        }
    }

    /**
     * 判断给定日期是否为今天
     *
     * @param date 要判断的日期
     * @return 是否为今天
     */
    private fun isToday(date: Date): Boolean {
        val today = Calendar.getInstance()
        val targetDate = Calendar.getInstance().apply { time = date }

        return today.get(Calendar.YEAR) == targetDate.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == targetDate.get(Calendar.DAY_OF_YEAR)
    }

    /**
     * 判断两个时间是否应该显示时间分隔
     * 根据uniapp版本的逻辑，如果两条消息间隔超过10分钟，则显示时间
     *
     * @param currentTimeString 当前消息时间字符串
     * @param previousTimeString 上一条消息时间字符串
     * @return 是否应该显示时间分隔
     * @author Joker.X
     */
    fun shouldShowTimeHeader(currentTimeString: String?, previousTimeString: String?): Boolean {
        if (currentTimeString.isNullOrEmpty()) return false
        if (previousTimeString.isNullOrEmpty()) return true // 第一条消息总是显示时间

        return try {
            val currentTime = parseTimeString(currentTimeString)
            val previousTime = parseTimeString(previousTimeString)

            if (currentTime == null || previousTime == null) return true

            // 计算时间差，超过10分钟则显示时间头部
            val timeDifference = abs(currentTime.time - previousTime.time)
            val tenMinutesInMillis = 10 * 60 * 1000L

            timeDifference > tenMinutesInMillis
        } catch (e: Exception) {
            true // 解析失败时默认显示
        }
    }

    /**
     * 解析时间字符串为Date对象
     * 支持多种格式："HH:mm"、"yyyy-MM-dd HH:mm"、"yyyy-MM-dd HH:mm:ss"
     *
     * @param timeString 时间字符串
     * @return 解析后的Date对象，解析失败返回null
     */
    private fun parseTimeString(timeString: String): Date? {
        return try {
            when {
                // 格式："10:30"
                timeString.matches(Regex("^\\d{1,2}:\\d{2}$")) -> {
                    val today = Calendar.getInstance()
                    val timeParts = timeString.split(":")
                    today.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
                    today.set(Calendar.MINUTE, timeParts[1].toInt())
                    today.set(Calendar.SECOND, 0)
                    today.set(Calendar.MILLISECOND, 0)
                    today.time
                }
                // 格式："2024-01-01 10:30:00"
                timeString.contains(":") && timeString.split(":").size == 3 -> {
                    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    format.parse(timeString)
                }
                // 格式："2024-01-01 10:30"
                else -> {
                    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    format.parse(timeString)
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 判断在消息列表中的指定位置是否应该显示时间头部
     * 解决分页加载时时间头部显示问题
     *
     * @param messages 完整的消息列表（按时间倒序排列，最新消息在前）
     * @param currentIndex 当前消息在列表中的索引
     * @return 是否应该显示时间头部
     * @author Joker.X
     */
    fun shouldShowTimeHeaderInList(
        messages: List<com.joker.coolmall.core.model.entity.CsMsg>,
        currentIndex: Int
    ): Boolean {
        // 如果消息列表为空或索引无效，不显示时间头部
        if (messages.isEmpty() || currentIndex < 0 || currentIndex >= messages.size) {
            return false
        }

        val currentMessage = messages[currentIndex]

        // 检查当前消息的时间是否有效
        if (currentMessage.createTime.isNullOrEmpty()) {
            return false
        }

        // 如果是最后一条消息（最早的消息），且消息数量大于等于3条时才显示时间头部
        // 这样可以避免在消息很少时显示不必要的时间头部
        if (currentIndex == messages.size - 1) {
            return messages.size >= 3
        }

        // 对于其他位置的消息，检查与下一条消息的时间间隔
        val nextMessage = messages[currentIndex + 1]

        // 判断当前消息和下一条消息之间是否需要显示时间分隔
        // 注意：由于列表是倒序的，所以nextMessage实际上是时间上更早的消息
        return shouldShowTimeHeader(
            currentTimeString = currentMessage.createTime,
            previousTimeString = nextMessage.createTime
        )
    }
}