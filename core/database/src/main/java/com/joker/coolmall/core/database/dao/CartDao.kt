package com.joker.coolmall.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joker.coolmall.core.database.entity.CartEntity
import kotlinx.coroutines.flow.Flow

/**
 * 购物车数据访问对象
 *
 * @author Joker.X
 */
@Dao
interface CartDao {

    /**
     * 插入购物车项
     * 如果已存在相同goodsId的记录，则替换
     *
     * @param cart 购物车项
     * @author Joker.X
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity)

    /**
     * 批量插入购物车项
     *
     * @param carts 购物车项列表
     * @author Joker.X
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarts(carts: List<CartEntity>)

    /**
     * 更新购物车项
     *
     * @param cart 购物车项
     * @author Joker.X
     */
    @Update
    suspend fun updateCart(cart: CartEntity)

    /**
     * 删除购物车项
     *
     * @param cart 购物车项
     * @author Joker.X
     */
    @Delete
    suspend fun deleteCart(cart: CartEntity)

    /**
     * 根据商品ID删除购物车项
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    @Query("DELETE FROM carts WHERE goodsId = :goodsId")
    suspend fun deleteCartByGoodsId(goodsId: Long)

    /**
     * 清空购物车
     *
     * @author Joker.X
     */
    @Query("DELETE FROM carts")
    suspend fun clearCart()

    /**
     * 获取所有购物车项
     * 使用Flow实现响应式
     *
     * @return 购物车项列表的Flow
     * @author Joker.X
     */
    @Query("SELECT * FROM carts")
    fun getAllCarts(): Flow<List<CartEntity>>

    /**
     * 根据商品ID获取购物车项
     *
     * @param goodsId 商品ID
     * @return 购物车项，如不存在则返回null
     * @author Joker.X
     */
    @Query("SELECT * FROM carts WHERE goodsId = :goodsId")
    suspend fun getCartByGoodsId(goodsId: Long): CartEntity?

    /**
     * 获取购物车项数量
     *
     * @return 购物车项数量的Flow
     * @author Joker.X
     */
    @Query("SELECT COUNT(*) FROM carts")
    fun getCartCount(): Flow<Int>
}