package com.joker.coolmall.core.database.util

import androidx.room.TypeConverter
import com.joker.coolmall.core.model.entity.CartGoodsSpec
import kotlinx.serialization.json.Json

/**
 * 购物车规格类型转换器
 * 用于将CartGoodsSpec列表转换为字符串存储在数据库中
 *
 * @author Joker.X
 */
class CartSpecConverter {

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * 将CartGoodsSpec列表转换为JSON字符串
     *
     * @param value 购物车规格列表
     * @return JSON字符串
     * @author Joker.X
     */
    @TypeConverter
    fun fromSpecList(value: List<CartGoodsSpec>?): String {
        return if (value.isNullOrEmpty()) {
            "[]"
        } else {
            json.encodeToString(value)
        }
    }

    /**
     * 将JSON字符串转换为CartGoodsSpec列表
     *
     * @param value JSON字符串
     * @return 购物车规格列表
     * @author Joker.X
     */
    @TypeConverter
    fun toSpecList(value: String): List<CartGoodsSpec> {
        return if (value.isEmpty() || value == "[]") {
            emptyList()
        } else {
            try {
                json.decodeFromString(value)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}