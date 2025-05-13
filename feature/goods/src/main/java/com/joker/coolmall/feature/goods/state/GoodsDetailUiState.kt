package com.joker.coolmall.feature.goods.state

import com.joker.coolmall.core.model.entity.Goods

/**
 * 商品详情UI状态封装类
 * 
 * 该接口使用sealed interface来表示商品详情页所有可能的UI状态：
 * - 加载中状态 [Loading]
 * - 加载成功状态 [Success]
 * - 加载失败状态 [Error]
 */
sealed interface GoodsDetailUiState {
    /**
     * 数据加载中状态
     */
    object Loading : GoodsDetailUiState

    /**
     * 数据加载成功状态
     *
     * @property data 商品数据模型
     */
    data class Success(
        val data: Goods,
    ) : GoodsDetailUiState

    /**
     * 数据加载失败状态
     *
     * @property message 错误信息
     */
    data class Error(
        val message: String
    ) : GoodsDetailUiState
} 