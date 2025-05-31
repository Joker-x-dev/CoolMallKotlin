package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.database.datasource.footprint.FootprintDataSource
import com.joker.coolmall.core.model.entity.Footprint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用户足迹仓库
 * 负责处理足迹相关的业务逻辑
 */
@Singleton
class FootprintRepository @Inject constructor(
    private val footprintDataSource: FootprintDataSource
) {

    /**
     * 添加足迹记录
     * @param footprint 足迹记录
     */
    suspend fun addFootprint(footprint: Footprint) {
        footprintDataSource.addFootprint(footprint)
    }

    /**
     * 根据商品ID删除足迹记录
     * @param goodsId 商品ID
     */
    suspend fun removeFootprint(goodsId: Long) {
        footprintDataSource.removeFootprint(goodsId)
    }

    /**
     * 清空所有足迹记录
     */
    suspend fun clearAllFootprints() {
        footprintDataSource.clearAllFootprints()
    }

    /**
     * 获取所有足迹记录
     * @return 足迹记录列表的Flow，按浏览时间倒序排列
     */
    fun getAllFootprints(): Flow<List<Footprint>> {
        // 使用IO调度器处理数据库操作
        return footprintDataSource.getAllFootprints().flowOn(Dispatchers.IO)
    }

    /**
     * 获取足迹记录总数量
     * @return 记录数量的Flow
     */
    fun getFootprintCount(): Flow<Int> {
        // 使用IO调度器处理数据库操作
        return footprintDataSource.getFootprintCount().flowOn(Dispatchers.IO)
    }

    /**
     * 获取指定数量的最新足迹记录
     * @param limit 限制数量
     * @return 足迹记录列表的Flow
     */
    fun getRecentFootprints(limit: Int): Flow<List<Footprint>> {
        // 使用IO调度器处理数据库操作
        return footprintDataSource.getRecentFootprints(limit).flowOn(Dispatchers.IO)
    }

    /**
     * 根据商品ID获取足迹记录
     * @param goodsId 商品ID
     * @return 足迹记录，如不存在则返回null
     */
    suspend fun getFootprintByGoodsId(goodsId: Long): Footprint? {
        return footprintDataSource.getFootprintByGoodsId(goodsId)
    }
}