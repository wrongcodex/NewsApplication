package com.example.newsapplication.core.apis.newsApi.services

import com.example.newsapplication.core.models.newsModel.NewsApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NewsApiService {
    @GET("top-headlines")
    suspend fun getInstance(
        @Query("category") category: String = "general",
        @Query("lang") lang: String = "en",
        @Query("country") country: String,
        @Query("apikey") apiKey: String
    ): Response<NewsApiModel>
}