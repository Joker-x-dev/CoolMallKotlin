package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.database.datasource.cart.CartDataSource
import com.joker.coolmall.core.model.entity.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 购物车仓库
 * 负责处理购物车相关的业务逻辑
 *
 * @param cartDataSource 购物车数据源
 * @author Joker.X
 */
@Singleton
class CartRepository @Inject constructor(
    private val cartDataSource: CartDataSource
) {

    /**
     * 添加商品到购物车
     *
     * @param cart 购物车商品
     * @author Joker.X
     */
    suspend fun addToCart(cart: Cart) {
        cartDataSource.addToCart(cart)
    }

    /**
     * 更新购物车中的商品
     *
     * @param cart 购物车商品
     * @author Joker.X
     */
    suspend fun updateCart(cart: Cart) {
        cartDataSource.updateCart(cart)
    }

    /**
     * 更新购物车中商品的规格数量
     *
     * @param goodsId 商品ID
     * @param specId 规格ID
     * @param count 数量
     * @author Joker.X
     */
    suspend fun updateCartSpecCount(goodsId: Long, specId: Long, count: Int) {
        cartDataSource.updateCartSpecCount(goodsId, specId, count)
    }

    /**
     * 从购物车删除商品
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    suspend fun removeFromCart(goodsId: Long) {
        cartDataSource.removeFromCart(goodsId)
    }

    /**
     * 从购物车删除商品规格
     *
     * @param goodsId 商品ID
     * @param specId 规格ID
     * @author Joker.X
     */
    suspend fun removeSpecFromCart(goodsId: Long, specId: Long) {
        cartDataSource.removeSpecFromCart(goodsId, specId)
    }

    /**
     * 清空购物车
     *
     * @author Joker.X
     */
    suspend fun clearCart() {
        cartDataSource.clearCart()
    }

    /**
     * 获取购物车中所有商品
     *
     * @return 购物车商品列表的Flow
     * @author Joker.X
     */
    fun getAllCarts(): Flow<List<Cart>> {
        // 使用IO调度器处理数据库操作
        return cartDataSource.getAllCarts().flowOn(Dispatchers.IO)
    }

    /**
     * 获取购物车商品总数量
     *
     * @return 商品数量的Flow
     * @author Joker.X
     */
    fun getCartCount(): Flow<Int> {
        // 使用IO调度器处理数据库操作
        return cartDataSource.getCartCount().flowOn(Dispatchers.IO)
    }

    /**
     * 根据商品ID获取购物车商品
     *
     * @param goodsId 商品ID
     * @return 购物车商品，如不存在则返回null
     * @author Joker.X
     */
    suspend fun getCartByGoodsId(goodsId: Long): Cart? {
        return cartDataSource.getCartByGoodsId(goodsId)
    }
}
