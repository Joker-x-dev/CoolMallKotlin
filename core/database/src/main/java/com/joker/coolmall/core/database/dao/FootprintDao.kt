package com.joker.coolmall.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joker.coolmall.core.database.entity.FootprintEntity
import kotlinx.coroutines.flow.Flow

/**
 * 用户足迹数据访问对象
 *
 * @author Joker.X
 */
@Dao
interface FootprintDao {

    /**
     * 插入或更新足迹记录
     * 如果已存在相同goodsId的记录，则替换（更新浏览时间）
     *
     * @param footprint 足迹记录
     * @author Joker.X
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFootprint(footprint: FootprintEntity)

    /**
     * 根据商品ID删除足迹记录
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    @Query("DELETE FROM footprints WHERE goodsId = :goodsId")
    suspend fun deleteFootprintByGoodsId(goodsId: Long)

    /**
     * 清空所有足迹记录
     *
     * @author Joker.X
     */
    @Query("DELETE FROM footprints")
    suspend fun clearAllFootprints()

    /**
     * 获取所有足迹记录，按浏览时间倒序排列（最新的在前面）
     * 使用Flow实现响应式
     *
     * @return 足迹记录列表的Flow
     * @author Joker.X
     */
    @Query("SELECT * FROM footprints ORDER BY viewTime DESC")
    fun getAllFootprints(): Flow<List<FootprintEntity>>

    /**
     * 获取足迹记录数量
     *
     * @return 足迹记录数量的Flow
     * @author Joker.X
     */
    @Query("SELECT COUNT(*) FROM footprints")
    fun getFootprintCount(): Flow<Int>

    /**
     * 获取指定数量的最新足迹记录
     *
     * @param limit 限制数量
     * @return 足迹记录列表的Flow
     * @author Joker.X
     */
    @Query("SELECT * FROM footprints ORDER BY viewTime DESC LIMIT :limit")
    fun getRecentFootprints(limit: Int): Flow<List<FootprintEntity>>

    /**
     * 根据商品ID查询足迹记录
     *
     * @param goodsId 商品ID
     * @return 足迹记录，如不存在则返回null
     * @author Joker.X
     */
    @Query("SELECT * FROM footprints WHERE goodsId = :goodsId")
    suspend fun getFootprintByGoodsId(goodsId: Long): FootprintEntity?
}