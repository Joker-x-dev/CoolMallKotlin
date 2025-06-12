package com.joker.coolmall.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joker.coolmall.core.database.dao.CartDao
import com.joker.coolmall.core.database.dao.FootprintDao
import com.joker.coolmall.core.database.dao.SearchHistoryDao
import com.joker.coolmall.core.database.entity.CartEntity
import com.joker.coolmall.core.database.entity.FootprintEntity
import com.joker.coolmall.core.database.entity.SearchHistoryEntity
import com.joker.coolmall.core.database.util.CartSpecConverter

/**
 * 应用数据库
 */
@Database(
    entities = [
        CartEntity::class,
        FootprintEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(CartSpecConverter::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * 获取购物车DAO
     */
    abstract fun cartDao(): CartDao

    /**
     * 获取足迹DAO
     */
    abstract fun footprintDao(): FootprintDao

    /**
     * 获取搜索历史DAO
     */
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        const val DATABASE_NAME = "coolmall-database"
    }
}