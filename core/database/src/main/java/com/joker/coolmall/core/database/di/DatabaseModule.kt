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
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * 提供数据库实例
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
     */
    @Provides
    @Singleton
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    /**
     * 提供足迹DAO
     */
    @Provides
    @Singleton
    fun provideFootprintDao(database: AppDatabase): FootprintDao {
        return database.footprintDao()
    }

    /**
     * 提供搜索历史DAO
     */
    @Provides
    @Singleton
    fun provideSearchHistoryDao(database: AppDatabase): SearchHistoryDao {
        return database.searchHistoryDao()
    }
}