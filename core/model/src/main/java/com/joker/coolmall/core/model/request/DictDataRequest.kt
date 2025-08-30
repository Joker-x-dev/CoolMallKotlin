/**
 * 字典数据请求模型
 *
 * @author Joker.X
 */
package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 字典数据请求
 * @param types 字典类型列表
 */
@Serializable
data class DictDataRequest(
    val types: List<String>
)