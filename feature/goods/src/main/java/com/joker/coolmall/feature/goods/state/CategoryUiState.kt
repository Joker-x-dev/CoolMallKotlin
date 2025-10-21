import com.joker.coolmall.core.model.entity.CategoryTree

/**
 * 分类页面UI状态
 *
 * @author Joker.X
 */
sealed class CategoryUiState {
    /**
     * 加载中状态
     */
    object Loading : CategoryUiState()

    /**
     * 加载成功状态
     * @param data 分类数据列表（树形结构）
     */
    data class Success(val data: List<CategoryTree>) : CategoryUiState()

    /**
     * 加载失败状态
     */
    class Error() : CategoryUiState()
} 