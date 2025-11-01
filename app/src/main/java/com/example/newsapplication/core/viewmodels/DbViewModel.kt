package com.example.newsapplication.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.core.models.newsModel.Article
import com.example.newsapplication.core.repositories.repos.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
): ViewModel() {
    private val _dbNews = MutableStateFlow<List<Article>>(emptyList())

//    fun getAllNews(): List<Article>{
//        viewModelScope.launch {
//            _dbNews.value = databaseRepository.getAllArticles()
//        }
//        //return
//        return _dbNews.value
//    }
    fun saveNews(article: Article){
        viewModelScope.launch {
            databaseRepository.saveArticleById(article)
        }
    }
    fun deleteNewsById(id: Int){
        viewModelScope.launch {
            databaseRepository.deleteArticleById(id)
        }
    }
}