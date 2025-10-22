package com.joker.coolmall.core.model.entity

/**
 * 商品分类树结构
 * 用于UI展示的树形分类数据
 *
 * @param id ID
 * @param name 名称
 * @param parentId 父ID
 * @param sortNum 排序
 * @param pic 图片
 * @param status 状态 0-禁用 1-启用
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @param children 子分类列表
 * @author Joker.X
 */
data class CategoryTree(
    /**
     * ID
     */
    val id: Long,

    /**
     * 名称
     */
    val name: String,

    /**
     * 父ID
     */
    val parentId: Int?,

    /**
     * 排序
     */
    val sortNum: Int,

    /**
     * 图片
     */
    val pic: String?,

    /**
     * 状态 0-禁用 1-启用
     */
    val status: Int,

    /**
     * 创建时间
     */
    val createTime: String?,

    /**
     * 更新时间
     */
    val updateTime: String?,

    /**
     * 子分类列表
     */
    val children: List<CategoryTree> = emptyList()
) {
    companion object {
        /**
         * 从Category转换为CategoryTree
         *
         * @param category 商品分类
         * @return 商品分类树结构
         * @author Joker.X
         */
        fun fromCategory(category: Category): CategoryTree {
            return CategoryTree(
                id = category.id,
                name = category.name,
                parentId = category.parentId,
                sortNum = category.sortNum,
                pic = category.pic,
                status = category.status,
                createTime = category.createTime,
                updateTime = category.updateTime
            )
        }
    }
} 