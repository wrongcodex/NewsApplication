package com.example.newsapplication.core.repositories.impl

import android.util.Log
import com.example.newsapplication.core.apis.newsApi.ApiKey.api_key
import com.example.newsapplication.core.apis.newsApi.NetworkResponse
import com.example.newsapplication.core.apis.newsApi.Server1
import com.example.newsapplication.core.apis.newsApi.Server2
import com.example.newsapplication.core.apis.newsApi.services.NewsApiService
import com.example.newsapplication.core.models.newsModel.NewsApiModel
import com.example.newsapplication.core.repositories.repos.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
//     @Server1 private val newsApiService: NewsApiService,
     //@Server2 private val newsApiService2: NewsApiService
): NewsRepository {
    //private val coInstance = newsApiService.
    override suspend fun getLatestHighlightsByCountry(country: String): NetworkResponse<NewsApiModel> {
        val response = newsApiService.getInstance(
            country = country,
            apiKey = api_key
        )
        return try {
            if (response.isSuccessful && response.body()!= null){
                val data = response.body()!!
                Log.d("abcdab", "getLatestHighlightsByCountry: ${data.articles}")
                NetworkResponse.Success(response.body()!!)
            } else {
                NetworkResponse.Error("Articles Fetching Error: ${response.message()}")
            }
        }catch (e: Exception){
            NetworkResponse.Error("Articles Fetching Error in catch ${e.message}")
        }
    }
}