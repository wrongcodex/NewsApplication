package com.example.newsapplication.core.repositories.impl

import com.example.newsapplication.core.db.NewsDB.NewsDAO
import com.example.newsapplication.core.models.newsModel.Article
import com.example.newsapplication.core.repositories.repos.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val newsDAO: NewsDAO
): DatabaseRepository {
    override suspend fun getAllArticles(): Flow<List<Article>> {
        newsDAO.selectAllArticles()
        return newsDAO.selectAllArticles()
    }

    override suspend fun saveArticleById(article: Article) {
        newsDAO.insertSingleArticle(article)
    }

    override suspend fun deleteArticleById(id: Int) {
        newsDAO.deleteArticle(id)
    }
}