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
 */
@Singleton
class CartDataSource @Inject constructor(
    private val cartDao: CartDao
) {

    /**
     * 添加商品到购物车
     */
    suspend fun addToCart(cart: Cart) {
        cartDao.insertCart(cart.toCartEntity())
    }

    /**
     * 更新购物车中的商品
     */
    suspend fun updateCart(cart: Cart) {
        cartDao.updateCart(cart.toCartEntity())
    }

    /**
     * 更新购物车中商品的规格数量
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
     */
    suspend fun removeFromCart(goodsId: Long) {
        cartDao.deleteCartByGoodsId(goodsId)
    }

    /**
     * 从购物车删除商品规格
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
     */
    suspend fun clearCart() {
        cartDao.clearCart()
    }

    /**
     * 获取购物车中所有商品
     * 返回响应式Flow
     */
    fun getAllCarts(): Flow<List<Cart>> {
        return cartDao.getAllCarts().map { entities ->
            entities.map { it.toCart() }
        }
    }

    /**
     * 获取购物车总数量
     * 返回响应式Flow
     */
    fun getCartCount(): Flow<Int> {
        return cartDao.getCartCount()
    }

    /**
     * 根据商品ID获取购物车商品
     */
    suspend fun getCartByGoodsId(goodsId: Long): Cart? {
        return cartDao.getCartByGoodsId(goodsId)?.toCart()
    }

    // 扩展函数：将实体模型转换为领域模型
    private fun CartEntity.toCart(): Cart {
        return Cart().apply {
            goodsId = this@toCart.goodsId
            goodsName = this@toCart.goodsName
            goodsMainPic = this@toCart.goodsMainPic
            spec = this@toCart.spec
        }
    }

    // 扩展函数：将领域模型转换为实体模型
    private fun Cart.toCartEntity(): CartEntity {
        return CartEntity(
            goodsId = this.goodsId,
            goodsName = this.goodsName,
            goodsMainPic = this.goodsMainPic,
            spec = this.spec
        )
    }
}