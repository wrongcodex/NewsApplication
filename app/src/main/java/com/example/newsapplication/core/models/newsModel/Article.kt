package com.example.newsapplication.core.models.newsModel

data class Article(
    val content: String,
    val description: String,
    val id: String,
    val image: String,
    val lang: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String
)