package com.joker.coolmall.core.database.datasource.footprint

import com.joker.coolmall.core.database.dao.FootprintDao
import com.joker.coolmall.core.database.entity.FootprintEntity
import com.joker.coolmall.core.model.entity.Footprint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用户足迹数据源
 * 负责足迹相关的数据库操作
 */
@Singleton
class FootprintDataSource @Inject constructor(
    private val footprintDao: FootprintDao
) {

    /**
     * 添加足迹记录
     */
    suspend fun addFootprint(footprint: Footprint) {
        footprintDao.insertFootprint(footprint.toFootprintEntity())
    }

    /**
     * 根据商品ID删除足迹记录
     */
    suspend fun removeFootprint(goodsId: Long) {
        footprintDao.deleteFootprintByGoodsId(goodsId)
    }

    /**
     * 清空所有足迹记录
     */
    suspend fun clearAllFootprints() {
        footprintDao.clearAllFootprints()
    }

    /**
     * 获取所有足迹记录
     * 返回响应式Flow，按浏览时间倒序排列
     */
    fun getAllFootprints(): Flow<List<Footprint>> {
        return footprintDao.getAllFootprints().map { entities ->
            entities.map { it.toFootprint() }
        }
    }

    /**
     * 获取足迹记录总数量
     * 返回响应式Flow
     */
    fun getFootprintCount(): Flow<Int> {
        return footprintDao.getFootprintCount()
    }

    /**
     * 获取指定数量的最新足迹记录
     */
    fun getRecentFootprints(limit: Int): Flow<List<Footprint>> {
        return footprintDao.getRecentFootprints(limit).map { entities ->
            entities.map { it.toFootprint() }
        }
    }

    /**
     * 根据商品ID获取足迹记录
     */
    suspend fun getFootprintByGoodsId(goodsId: Long): Footprint? {
        return footprintDao.getFootprintByGoodsId(goodsId)?.toFootprint()
    }

    // 扩展函数：将实体模型转换为领域模型
    private fun FootprintEntity.toFootprint(): Footprint {
        return Footprint().apply {
            goodsId = this@toFootprint.goodsId
            goodsName = this@toFootprint.goodsName
            goodsSubTitle = this@toFootprint.goodsSubTitle
            goodsMainPic = this@toFootprint.goodsMainPic
            goodsPrice = this@toFootprint.goodsPrice
            goodsSold = this@toFootprint.goodsSold
            viewTime = this@toFootprint.viewTime
        }
    }

    // 扩展函数：将领域模型转换为实体模型
    private fun Footprint.toFootprintEntity(): FootprintEntity {
        return FootprintEntity(
            goodsId = this.goodsId,
            goodsName = this.goodsName,
            goodsSubTitle = this.goodsSubTitle,
            goodsMainPic = this.goodsMainPic,
            goodsPrice = this.goodsPrice,
            goodsSold = this.goodsSold,
            viewTime = this.viewTime
        )
    }
}