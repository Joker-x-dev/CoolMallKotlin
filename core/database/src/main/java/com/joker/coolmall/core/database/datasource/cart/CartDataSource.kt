package com.joker.coolmall.core.database.datasource.cart

import com.joker.coolmall.core.database.dao.CartDao
import com.joker.coolmall.core.database.entity.CartEntity
import com.joker.coolmall.core.model.entity.Cart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 购物车数据源
 * 负责购物车相关的数据库操作
 *
 * @param cartDao 购物车数据访问对象
 * @author Joker.X
 */
@Singleton
class CartDataSource @Inject constructor(
    private val cartDao: CartDao
) {

    /**
     * 添加商品到购物车
     *
     * @param cart 购物车商品
     * @author Joker.X
     */
    suspend fun addToCart(cart: Cart) {
        cartDao.insertCart(cart.toCartEntity())
    }

    /**
     * 更新购物车中的商品
     *
     * @param cart 购物车商品
     * @author Joker.X
     */
    suspend fun updateCart(cart: Cart) {
        cartDao.updateCart(cart.toCartEntity())
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
        val cart = cartDao.getCartByGoodsId(goodsId) ?: return

        // 更新特定规格的数量
        val updatedSpecs = cart.spec.map { spec ->
            if (spec.id == specId) {
                spec.copy(count = count)
            } else {
                spec
            }
        }

        // 如果更新后规格全部数量为0，则删除整个购物车项
        if (updatedSpecs.all { it.count <= 0 }) {
            cartDao.deleteCartByGoodsId(goodsId)
        } else {
            // 否则更新购物车
            cartDao.updateCart(cart.copy(spec = updatedSpecs))
        }
    }

    /**
     * 从购物车删除商品
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    suspend fun removeFromCart(goodsId: Long) {
        cartDao.deleteCartByGoodsId(goodsId)
    }

    /**
     * 从购物车删除商品规格
     *
     * @param goodsId 商品ID
     * @param specId 规格ID
     * @author Joker.X
     */
    suspend fun removeSpecFromCart(goodsId: Long, specId: Long) {
        val cart = cartDao.getCartByGoodsId(goodsId) ?: return

        // 移除特定规格
        val updatedSpecs = cart.spec.filter { it.id != specId }

        // 如果移除后没有规格了，则删除整个购物车项
        if (updatedSpecs.isEmpty()) {
            cartDao.deleteCartByGoodsId(goodsId)
        } else {
            // 否则更新购物车
            cartDao.updateCart(cart.copy(spec = updatedSpecs))
        }
    }

    /**
     * 清空购物车
     *
     * @author Joker.X
     */
    suspend fun clearCart() {
        cartDao.clearCart()
    }

    /**
     * 获取购物车中所有商品
     * 返回响应式Flow
     *
     * @return 购物车商品列表的Flow
     * @author Joker.X
     */
    fun getAllCarts(): Flow<List<Cart>> {
        return cartDao.getAllCarts().map { entities ->
            entities.map { it.toCart() }
        }
    }

    /**
     * 获取购物车总数量
     * 返回响应式Flow
     *
     * @return 商品数量的Flow
     * @author Joker.X
     */
    fun getCartCount(): Flow<Int> {
        return cartDao.getCartCount()
    }

    /**
     * 根据商品ID获取购物车商品
     *
     * @param goodsId 商品ID
     * @return 购物车商品，如不存在则返回null
     * @author Joker.X
     */
    suspend fun getCartByGoodsId(goodsId: Long): Cart? {
        return cartDao.getCartByGoodsId(goodsId)?.toCart()
    }

    /**
     * 扩展函数：将实体模型转换为领域模型
     *
     * @return 领域模型Cart
     * @author Joker.X
     */
    private fun CartEntity.toCart(): Cart {
        return Cart(
            goodsId = this.goodsId,
            goodsName = this.goodsName,
            goodsMainPic = this.goodsMainPic,
            spec = this.spec
        )
    }

    /**
     * 扩展函数：将领域模型转换为实体模型
     *
     * @return 实体模型CartEntity
     * @author Joker.X
     */
    private fun Cart.toCartEntity(): CartEntity {
        return CartEntity(
            goodsId = this.goodsId,
            goodsName = this.goodsName,
            goodsMainPic = this.goodsMainPic,
            spec = this.spec
        )
    }
}