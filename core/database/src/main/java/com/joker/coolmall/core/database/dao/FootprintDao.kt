package com.joker.coolmall.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joker.coolmall.core.database.entity.FootprintEntity
import kotlinx.coroutines.flow.Flow

/**
 * 用户足迹数据访问对象
 */
@Dao
interface FootprintDao {

    /**
     * 插入或更新足迹记录
     * 如果已存在相同goodsId的记录，则替换（更新浏览时间）
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFootprint(footprint: FootprintEntity)

    /**
     * 根据商品ID删除足迹记录
     */
    @Query("DELETE FROM footprints WHERE goodsId = :goodsId")
    suspend fun deleteFootprintByGoodsId(goodsId: Long)

    /**
     * 清空所有足迹记录
     */
    @Query("DELETE FROM footprints")
    suspend fun clearAllFootprints()

    /**
     * 获取所有足迹记录，按浏览时间倒序排列（最新的在前面）
     * 使用Flow实现响应式
     */
    @Query("SELECT * FROM footprints ORDER BY viewTime DESC")
    fun getAllFootprints(): Flow<List<FootprintEntity>>

    /**
     * 获取足迹记录数量
     */
    @Query("SELECT COUNT(*) FROM footprints")
    fun getFootprintCount(): Flow<Int>

    /**
     * 获取指定数量的最新足迹记录
     */
    @Query("SELECT * FROM footprints ORDER BY viewTime DESC LIMIT :limit")
    fun getRecentFootprints(limit: Int): Flow<List<FootprintEntity>>

    /**
     * 根据商品ID查询足迹记录
     */
    @Query("SELECT * FROM footprints WHERE goodsId = :goodsId")
    suspend fun getFootprintByGoodsId(goodsId: Long): FootprintEntity?
}