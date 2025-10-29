package com.example.newsapplication.core.repositories.repos

import com.example.newsapplication.core.apis.newsApi.NetworkResponse
import com.example.newsapplication.core.models.newsModel.NewsApiModel

interface NewsRepository {
    suspend fun getLatestHighlightsByCountry(country: String): NetworkResponse<NewsApiModel>
}