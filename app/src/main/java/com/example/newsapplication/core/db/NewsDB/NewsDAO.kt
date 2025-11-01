package com.example.newsapplication.core.db.NewsDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsapplication.core.models.newsModel.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO{
    @Upsert
    suspend fun insertArticleInDatabase(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleArticle(newsEntities: NewsEntities)

    @Delete
    suspend fun deleteArticle(id: Int)
    @Query("DELETE FROM news_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM news_table")
    fun selectAllArticles(): Flow<List<Article>>
}