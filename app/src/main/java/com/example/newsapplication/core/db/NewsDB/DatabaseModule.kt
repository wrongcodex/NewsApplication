package com.example.newsapplication.core.db.NewsDB

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val databseName = "news_database"

    @Singleton
    @Provides
    fun instance(@ApplicationContext context: Context): NewsDatabase{
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            databseName
        ).fallbackToDestructiveMigration(false).build()
    }

    @Singleton
    @Provides
    fun provideDatabase(newsDatabase: NewsDatabase): NewsDAO{
        return newsDatabase.newsDao()
    }
}