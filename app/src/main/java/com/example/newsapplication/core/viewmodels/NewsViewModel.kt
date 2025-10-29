package com.example.newsapplication.core.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.core.apis.newsApi.NetworkResponse
import com.example.newsapplication.core.models.newsModel.NewsApiModel
import com.example.newsapplication.core.repositories.repos.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {
    private val _news = MutableStateFlow<NetworkResponse<NewsApiModel>>(NetworkResponse.Loading)
    val news: StateFlow<NetworkResponse<NewsApiModel>> = _news

    fun getListOfNewsByCountry(country:String){
        viewModelScope.launch {
            _news.value = newsRepository.getLatestHighlightsByCountry(country)
            Log.d("response13242df", "getListOfNewsByCountry: ${_news.value}")
        }
    }
}