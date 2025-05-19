package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.CartRepository
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.util.log.LogUtils
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 购物车页面ViewModel
 */
@HiltViewModel
class CartViewModel @Inject constructor(
    navigator: AppNavigator,
    private val cartRepository: CartRepository
) : BaseViewModel(navigator) {

    /**
     * 编辑模式状态
     */
    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing

    /**
     * 选中的商品ID和规格ID的集合
     * Map的key是商品ID，value是该商品下选中的规格ID集合
     */
    private val _selectedItems = MutableStateFlow<Map<Long, Set<Long>>>(emptyMap())
    val selectedItems: StateFlow<Map<Long, Set<Long>>> = _selectedItems

    /**
     * 购物车列表数据
     */
    val cartItems: StateFlow<List<Cart>> = cartRepository.getAllCarts()
        .map { carts ->
            // 每次数据更新时打印日志
            LogUtils.d("购物车数据更新 >>>>>>>>>>>>>>>>>>>>")
            LogUtils.d("购物车商品数量: ${carts.size}")
            carts.forEach { cart ->
                LogUtils.d("商品: ${cart.goodsName}")
                LogUtils.d("  ID: ${cart.goodsId}")
                LogUtils.d("  主图: ${cart.goodsMainPic}")
                cart.spec.forEach { spec ->
                    LogUtils.d("  规格: ${spec.name}")
                    LogUtils.d("    ID: ${spec.id}")
                    LogUtils.d("    价格: ${spec.price}")
                    LogUtils.d("    数量: ${spec.count}")
                }
            }
            LogUtils.d("<<<<<<<<<<<<<<<<<<<<")
            carts
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * 购物车商品总数量
     */
    val cartCount: StateFlow<Int> = cartRepository.getCartCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    /**
     * 购物车空状态
     */
    val isEmpty: StateFlow<Boolean> = cartItems.map { it.isEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    /**
     * 是否全选状态
     */
    val isAllSelected: StateFlow<Boolean> = combine(cartItems, selectedItems) { carts, selected ->
        if (carts.isEmpty()) return@combine false
        
        // 统计所有规格的数量
        val totalSpecCount = carts.sumOf { it.spec.size }
        
        // 统计已选择的规格数量
        val selectedSpecCount = selected.entries.sumOf { (goodsId, specIds) ->
            specIds.size
        }
        
        // 全选状态：所有规格都被选中
        totalSpecCount > 0 && totalSpecCount == selectedSpecCount
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    /**
     * 已选中的商品数量
     */
    val selectedCount: StateFlow<Int> = selectedItems.map { selected ->
        selected.values.sumOf { it.size }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    /**
     * 已选中商品的总价（分）
     */
    val selectedTotalAmount: StateFlow<Int> = combine(cartItems, selectedItems) { carts, selected ->
        var total = 0
        
        carts.forEach { cart ->
            val goodsId = cart.goodsId
            // 获取该商品下选中的规格ID集合
            val selectedSpecIds = selected[goodsId]?.toMutableSet() ?: mutableSetOf()
            
            // 计算该商品下选中规格的总价
            cart.spec.forEach { spec ->
                if (selectedSpecIds.contains(spec.id)) {
                    total += spec.price * spec.count
                }
            }
        }
        
        total
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    /**
     * 购物车总金额（分）
     */
    val totalAmount: StateFlow<Int> = cartItems.map { carts ->
        carts.sumOf { cart ->
            cart.spec.sumOf { spec ->
                spec.price * spec.count
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    init {
        println("CartViewModel 初始化...")
        LogUtils.d("开始刷新购物车数据...")
        // 初始化时主动获取一次购物车数据
        refreshCartData()
    }

    /**
     * 刷新购物车数据
     */
    private fun refreshCartData() {
        LogUtils.d("开始刷新购物车数据...")
        viewModelScope.launch {
            try {
                LogUtils.d("开始获取购物车数据...")
                cartRepository.getAllCarts().collect { carts ->
                    LogUtils.d("获取到购物车数据: ${carts.size} 件商品")
                }
            } catch (e: Exception) {
                LogUtils.e("获取购物车数据失败: ${e.message}")
            }
        }
    }

    /**
     * 切换编辑模式
     */
    fun toggleEditMode() {
        _isEditing.value = !_isEditing.value
    }

    /**
     * 切换全选状态
     */
    fun toggleSelectAll() {
        val currentAllSelected = isAllSelected.value
        
        if (currentAllSelected) {
            // 当前是全选状态，取消全选
            _selectedItems.value = emptyMap()
        } else {
            // 当前非全选状态，设置全选
            val newSelectedMap = mutableMapOf<Long, Set<Long>>()
            
            cartItems.value.forEach { cart ->
                val specIds = cart.spec.map { it.id }.toSet()
                if (specIds.isNotEmpty()) {
                    newSelectedMap[cart.goodsId] = specIds
                }
            }
            
            _selectedItems.value = newSelectedMap
        }
    }

    /**
     * 切换商品规格的选中状态
     */
    fun toggleItemSelection(goodsId: Long, specId: Long) {
        LogUtils.d("切换选中状态开始: goodsId=$goodsId, specId=$specId, 当前状态=${isItemSelected(goodsId, specId)}")
        
        _selectedItems.update { currentSelected ->
            val mutableMap = currentSelected.toMutableMap()
            
            // 获取该商品当前已选中的规格集合
            val currentSpecIds = currentSelected[goodsId]?.toMutableSet() ?: mutableSetOf()
            
            if (currentSpecIds.contains(specId)) {
                // 如果规格已选中，则取消选中
                currentSpecIds.remove(specId)
                LogUtils.d("取消选中: goodsId=$goodsId, specId=$specId")
                
                // 如果该商品下没有选中的规格了，则从map中移除该商品
                if (currentSpecIds.isEmpty()) {
                    mutableMap.remove(goodsId)
                } else {
                    mutableMap[goodsId] = currentSpecIds
                }
            } else {
                // 如果规格未选中，则添加选中
                currentSpecIds.add(specId)
                LogUtils.d("添加选中: goodsId=$goodsId, specId=$specId")
                mutableMap[goodsId] = currentSpecIds
            }
            
            // 打印更新后的状态
            val result = mutableMap.toMap()
            LogUtils.d("选中状态更新后: $result")
            result
        }
    }

    /**
     * 检查商品规格是否被选中
     */
    fun isItemSelected(goodsId: Long, specId: Long): Boolean {
        val selectedSpecIds = selectedItems.value[goodsId] ?: return false
        val isSelected = selectedSpecIds.contains(specId)
        LogUtils.d("检查选中状态: goodsId=$goodsId, specId=$specId, isSelected=$isSelected")
        return isSelected
    }

    /**
     * 更新购物车商品规格数量
     */
    fun updateCartItemCount(goodsId: Long, specId: Long, count: Int) {
        viewModelScope.launch {
            try {
                cartRepository.updateCartSpecCount(goodsId, specId, count)
                LogUtils.d("更新购物车商品数量成功: goodsId=$goodsId, specId=$specId, count=$count")
            } catch (e: Exception) {
                LogUtils.e("更新购物车商品数量失败: ${e.message}")
            }
        }
    }

    /**
     * 从购物车移除商品
     */
    fun removeCartItem(goodsId: Long) {
        viewModelScope.launch {
            try {
                cartRepository.removeFromCart(goodsId)
                LogUtils.d("移除购物车商品成功: goodsId=$goodsId")
                
                // 同时从选中集合中移除该商品
                _selectedItems.update { current ->
                    val newMap = current.toMutableMap()
                    newMap.remove(goodsId)
                    newMap
                }
            } catch (e: Exception) {
                LogUtils.e("移除购物车商品失败: ${e.message}")
            }
        }
    }

    /**
     * 从购物车移除规格
     */
    fun removeCartItemSpec(goodsId: Long, specId: Long) {
        viewModelScope.launch {
            try {
                cartRepository.removeSpecFromCart(goodsId, specId)
                LogUtils.d("移除购物车商品规格成功: goodsId=$goodsId, specId=$specId")
                
                // 同时从选中集合中移除该规格
                _selectedItems.update { current ->
                    val newMap = current.toMutableMap()
                    val specIds = newMap[goodsId]?.toMutableSet()
                    
                    if (specIds != null) {
                        specIds.remove(specId)
                        if (specIds.isEmpty()) {
                            newMap.remove(goodsId)
                        } else {
                            newMap[goodsId] = specIds
                        }
                    }
                    
                    newMap
                }
            } catch (e: Exception) {
                LogUtils.e("移除购物车商品规格失败: ${e.message}")
            }
        }
    }

    /**
     * 删除选中的商品
     */
    fun deleteSelectedItems() {
        viewModelScope.launch {
            try {
                val itemsToDelete = selectedItems.value
                
                // 遍历所有选中的商品和规格
                itemsToDelete.forEach { (goodsId, specIds) ->
                    // 获取该商品当前所有的规格
                    val cart = cartItems.value.find { it.goodsId == goodsId }
                    
                    if (cart != null) {
                        // 如果该商品的所有规格都被选中，则直接删除整个商品
                        if (specIds.size == cart.spec.size) {
                            cartRepository.removeFromCart(goodsId)
                            LogUtils.d("删除整个商品: goodsId=$goodsId")
                        } else {
                            // 否则只删除选中的规格
                            specIds.forEach { specId ->
                                cartRepository.removeSpecFromCart(goodsId, specId)
                                LogUtils.d("删除商品规格: goodsId=$goodsId, specId=$specId")
                            }
                        }
                    }
                }
                
                // 清空选择状态
                _selectedItems.value = emptyMap()
                LogUtils.d("删除选中的商品成功")
                
            } catch (e: Exception) {
                LogUtils.e("删除选中的商品失败: ${e.message}")
            }
        }
    }

    /**
     * 清空购物车
     */
    fun clearCart() {
        viewModelScope.launch {
            try {
                cartRepository.clearCart()
                LogUtils.d("清空购物车成功")
                
                // 清空选择状态
                _selectedItems.value = emptyMap()
            } catch (e: Exception) {
                LogUtils.e("清空购物车失败: ${e.message}")
            }
        }
    }
}

