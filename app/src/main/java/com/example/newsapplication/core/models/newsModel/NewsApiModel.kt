package com.example.newsapplication.core.models.newsModel

data class NewsApiModel(
    val articles: List<Article>,
    val information: Information,
    val totalArticles: Int
)