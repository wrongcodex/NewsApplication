package com.example.newsapplication.core.repositories.repos

import com.example.newsapplication.core.apis.newsApi.NetworkResponse
import com.example.newsapplication.core.db.NewsDB.NewsEntities
import com.example.newsapplication.core.models.newsModel.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface DatabaseRepository {
    //suspend fun getArticleById(id: Int): Article
    suspend fun getAllArticles(): Flow<List<NewsEntities>>
    suspend fun saveArticleById(newsEntities: NewsEntities)

    suspend fun deleteArticleById(newsEntities: NewsEntities)
}