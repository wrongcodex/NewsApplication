package com.example.newsapplication.core.repositories.repos

import com.example.newsapplication.core.apis.newsApi.NetworkResponse
import com.example.newsapplication.core.models.newsModel.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface DatabaseRepository {
    //suspend fun getArticleById(id: Int): Article
    suspend fun getAllArticles(): Flow<List<Article>>
    suspend fun saveArticleById(article: Article)

    suspend fun deleteArticleById(id: Int)
}