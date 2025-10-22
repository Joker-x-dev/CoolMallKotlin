package com.joker.coolmall.core.model.entity

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive

/**
 * 自定义序列化器，用于处理condition字段
 * API可能返回字符串格式或对象格式的condition
 *
 * @author Joker.X
 */
object ConditionSerializer : KSerializer<Condition?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Condition", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Condition?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            encoder.encodeSerializableValue(Condition.serializer(), value)
        }
    }

    override fun deserialize(decoder: Decoder): Condition? {
        return try {
            when (decoder) {
                is JsonDecoder -> {
                    val element = decoder.decodeJsonElement()
                    when {
                        element is JsonPrimitive && element.isString -> {
                            // 如果是字符串格式，解析JSON字符串
                            val jsonString = element.content
                            if (jsonString.isBlank()) {
                                null
                            } else {
                                Json.decodeFromString(Condition.serializer(), jsonString)
                            }
                        }

                        else -> {
                            // 如果是对象格式，直接解析
                            Json.decodeFromJsonElement(Condition.serializer(), element)
                        }
                    }
                }

                else -> {
                    // 其他情况，尝试直接解析为Condition对象
                    decoder.decodeSerializableValue(Condition.serializer())
                }
            }
        } catch (_: Exception) {
            // 解析失败时返回null
            null
        }
    }
}

/**
 * 优惠券信息
 *
 * @param id ID
 * @param title 标题
 * @param description 描述
 * @param type 类型 0-满减
 * @param amount 金额
 * @param num 数量
 * @param receivedNum 已领取
 * @param startTime 开始时间
 * @param endTime 结束时间
 * @param status 状态 0-禁用 1-启用
 * @param condition 条件
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @param useStatus 使用状态 0-未使用 1-已使用 2-已过期，仅在"我的优惠券"接口中返回
 * @author Joker.X
 */
@Serializable
data class Coupon(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 标题
     */
    val title: String = "",

    /**
     * 描述
     */
    val description: String = "",

    /**
     * 类型 0-满减
     */
    val type: Int = 0,

    /**
     * 金额
     */
    val amount: Double = 0.0,

    /**
     * 数量
     */
    val num: Int = 0,

    /**
     * 已领取
     */
    val receivedNum: Int = 0,

    /**
     * 开始时间
     */
    val startTime: String? = null,

    /**
     * 结束时间
     */
    val endTime: String? = null,

    /**
     * 状态 0-禁用 1-启用
     */
    val status: Int = 0,

    /**
     * 条件
     */
    @Serializable(with = ConditionSerializer::class)
    val condition: Condition? = null,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null,

    /**
     * 使用状态 0-未使用 1-已使用 2-已过期
     * 注意：此字段仅在"我的优惠券"接口中返回，领取优惠券时不包含此字段
     */
    val useStatus: Int? = null
)

