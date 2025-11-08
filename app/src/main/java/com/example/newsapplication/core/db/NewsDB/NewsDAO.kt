package com.example.newsapplication.core.db.NewsDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO{
    @Upsert
    suspend fun insertArticleInDatabase(newsEntities: NewsEntities)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleArticle(newsEntities: NewsEntities)

    @Delete
    suspend fun deleteArticle(newsEntities: NewsEntities)
    @Query("DELETE FROM news_table")
    suspend fun deleteAll()

    //Query toggle fav
//    @Query("SELECT * FROM news_table WHERE")
//    suspend fun deleteAll()

    @Query("SELECT * FROM news_table")
    fun selectAllArticles(): Flow<List<NewsEntities>>
}