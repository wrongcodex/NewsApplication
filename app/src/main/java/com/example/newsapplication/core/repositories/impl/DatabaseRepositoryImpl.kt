package com.example.newsapplication.core.repositories.impl

import com.example.newsapplication.core.db.NewsDB.NewsDAO
import com.example.newsapplication.core.db.NewsDB.NewsEntities
import com.example.newsapplication.core.models.newsModel.Article
import com.example.newsapplication.core.repositories.repos.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val newsDAO: NewsDAO
): DatabaseRepository {
    override suspend fun getAllArticles(): Flow<List<NewsEntities>> {
        return newsDAO.selectAllArticles()
    }

    override suspend fun saveArticleById(newsEntities: NewsEntities) {
        newsDAO.insertSingleArticle(newsEntities)
    }

    override suspend fun deleteArticleById(newsEntities: NewsEntities) {
        newsDAO.deleteArticle(newsEntities)
    }
}