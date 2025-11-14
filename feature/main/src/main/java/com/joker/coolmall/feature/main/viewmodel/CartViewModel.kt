package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.CartRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.GoodsSpec
import com.joker.coolmall.core.model.entity.SelectedGoods
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import com.joker.coolmall.navigation.routes.MainRoutes
import com.joker.coolmall.navigation.routes.OrderRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 购物车页面ViewModel
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param cartRepository 购物车仓库
 * @param savedStateHandle 保存状态句柄
 * @author Joker.X
 */
@HiltViewModel
class CartViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val cartRepository: CartRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(navigator, appState) {

    /**
     * 是否显示返回按钮
     */
    private val _showBackIcon = MutableStateFlow(false)
    val showBackIcon: StateFlow<Boolean> = _showBackIcon.asStateFlow()

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
        .map { carts -> carts }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
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
     * 已选中商品的总价
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

    init {
        // 从路由中获取是否显示返回按钮
        _showBackIcon.value = savedStateHandle.toRoute<MainRoutes.Cart>().showBackIcon
    }

    /**
     * 切换编辑模式
     *
     * @author Joker.X
     */
    fun toggleEditMode() {
        // 清空选择状态
        _selectedItems.value = emptyMap()
        _isEditing.value = !_isEditing.value
    }

    /**
     * 切换全选状态
     *
     * @author Joker.X
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
     *
     * @param goodsId 商品ID
     * @param specId 规格ID
     * @author Joker.X
     */
    fun toggleItemSelection(goodsId: Long, specId: Long) {
        _selectedItems.update { currentSelected ->
            val mutableMap = currentSelected.toMutableMap()

            // 获取该商品当前已选中的规格集合
            val currentSpecIds = currentSelected[goodsId]?.toMutableSet() ?: mutableSetOf()

            if (currentSpecIds.contains(specId)) {
                // 如果规格已选中，则取消选中
                currentSpecIds.remove(specId)

                // 如果该商品下没有选中的规格了，则从map中移除该商品
                if (currentSpecIds.isEmpty()) {
                    mutableMap.remove(goodsId)
                } else {
                    mutableMap[goodsId] = currentSpecIds
                }
            } else {
                // 如果规格未选中，则添加选中
                currentSpecIds.add(specId)
                mutableMap[goodsId] = currentSpecIds
            }

            mutableMap.toMap()
        }
    }

    /**
     * 更新购物车商品规格数量
     *
     * @param goodsId 商品ID
     * @param specId 规格ID
     * @param count 更新后的数量
     * @author Joker.X
     */
    fun updateCartItemCount(goodsId: Long, specId: Long, count: Int) {
        viewModelScope.launch {
            cartRepository.updateCartSpecCount(goodsId, specId, count)
        }
    }

    /**
     * 删除选中的商品
     *
     * @author Joker.X
     */
    fun deleteSelectedItems() {
        viewModelScope.launch {
            val itemsToDelete = selectedItems.value

            // 遍历所有选中的商品和规格
            itemsToDelete.forEach { (goodsId, specIds) ->
                // 获取该商品当前所有的规格
                val cart = cartItems.value.find { it.goodsId == goodsId }

                if (cart != null) {
                    // 如果该商品的所有规格都被选中，则直接删除整个商品
                    if (specIds.size == cart.spec.size) {
                        cartRepository.removeFromCart(goodsId)
                    } else {
                        // 否则只删除选中的规格
                        specIds.forEach { specId ->
                            cartRepository.removeSpecFromCart(goodsId, specId)
                        }
                    }
                }
            }

            // 清空选择状态
            _selectedItems.value = emptyMap()
            _isEditing.value = false
        }
    }

    /**
     * 去结算按钮点击事件
     *
     * @author Joker.X
     */
    fun onCheckoutClick() {
        viewModelScope.launch {
            val selected = selectedItems.value
            if (selected.isEmpty()) {
                ToastUtils.showError(R.string.please_select_settlement_goods)
                return@launch
            }

            // 1. 获取选中的购物车项
            val selectedCarts = getSelectedCarts()
            if (selectedCarts.isEmpty()) {
                ToastUtils.showError(R.string.get_selected_goods_failed)
                return@launch
            }

            // 2. 缓存选中的购物车项
            MMKVUtils.putObject("carts", selectedCarts)

            // 3. 将购物车项转换为SelectedGoods
            val selectedGoodsList = convertCartsToSelectedGoods(selectedCarts)

            // 4. 缓存SelectedGoods
            MMKVUtils.putObject("selectedGoodsList", selectedGoodsList)

            // 5. 导航到订单确认页面
            navigate(OrderRoutes.Confirm)
        }
    }

    /**
     * 获取选中的购物车项
     *
     * @return 选中的购物车列表
     * @author Joker.X
     */
    private fun getSelectedCarts(): List<Cart> {
        val result = mutableListOf<Cart>()
        val selected = selectedItems.value

        cartItems.value.forEach { cart ->
            val goodsId = cart.goodsId
            val selectedSpecIds = selected[goodsId] ?: return@forEach

            // 创建一个新的Cart对象，只包含选中的规格
            val newCart = Cart().apply {
                this.goodsId = cart.goodsId
                this.goodsName = cart.goodsName
                this.goodsMainPic = cart.goodsMainPic
                this.spec = cart.spec.filter { selectedSpecIds.contains(it.id) }
            }

            if (newCart.spec.isNotEmpty()) {
                result.add(newCart)
            }
        }

        return result
    }

    /**
     * 将购物车项转换为SelectedGoods
     *
     * @param carts 购物车列表
     * @return 转换后的SelectedGoods列表
     * @author Joker.X
     */
    private fun convertCartsToSelectedGoods(carts: List<Cart>): List<SelectedGoods> {
        val result = mutableListOf<SelectedGoods>()

        carts.forEach { cart ->
            cart.spec.forEach { cartSpec ->
                val selectedGoods = SelectedGoods().apply {
                    goodsId = cart.goodsId
                    count = cartSpec.count
                    spec = GoodsSpec(
                        id = cartSpec.id,
                        goodsId = cartSpec.goodsId,
                        name = cartSpec.name,
                        price = cartSpec.price,
                        stock = cartSpec.stock,
                        images = cartSpec.images
                    )
                    goodsInfo = Goods(
                        id = cart.goodsId,
                        title = cart.goodsName,
                        mainPic = cart.goodsMainPic
                    )
                }

                result.add(selectedGoods)
            }
        }

        return result
    }

    /**
     * 跳转到商品详情
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    fun toGoodsDetailPage(goodsId: Long) {
        navigate(GoodsRoutes.Detail(goodsId = goodsId))
    }
}

