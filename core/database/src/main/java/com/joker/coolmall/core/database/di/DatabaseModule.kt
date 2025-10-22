package com.joker.coolmall.core.database.di

import android.content.Context
import androidx.room.Room
import com.joker.coolmall.core.database.AppDatabase
import com.joker.coolmall.core.database.dao.CartDao
import com.joker.coolmall.core.database.dao.FootprintDao
import com.joker.coolmall.core.database.dao.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 数据库模块
 * 负责提供数据库实例及相关DAO的依赖注入
 *
 * @author Joker.X
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * 提供数据库实例
     *
     * @param context 应用上下文
     * @return 应用数据库实例
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    /**
     * 提供购物车DAO
     *
     * @param database 应用数据库实例
     * @return 购物车数据访问对象
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    /**
     * 提供足迹DAO
     *
     * @param database 应用数据库实例
     * @return 足迹数据访问对象
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideFootprintDao(database: AppDatabase): FootprintDao {
        return database.footprintDao()
    }

    /**
     * 提供搜索历史DAO
     *
     * @param database 应用数据库实例
     * @return 搜索历史数据访问对象
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideSearchHistoryDao(database: AppDatabase): SearchHistoryDao {
        return database.searchHistoryDao()
    }
}