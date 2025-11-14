package com.example.newsapplication.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.core.db.NewsDB.NewsEntities
import com.example.newsapplication.core.models.newsModel.Article
import com.example.newsapplication.core.repositories.repos.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class DbViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
): ViewModel() {
    private val _favorites = MutableStateFlow<List<NewsEntities>>(emptyList())
    val favorites: StateFlow<List<NewsEntities>> = _favorites

    fun getAllNews(){
        viewModelScope.launch {
            databaseRepository.getAllArticles().collect (_favorites)
        }
    }

    fun saveNews(article: Article){
        viewModelScope.launch {
            val newsEntity = NewsEntities(
                content = article.content,
                description = article.description,
                id = article.id,
                image = article.image,
                lang = article.lang,
                publishedAt = article.publishedAt,
                title = article.title,
                source = article.source.name,
                url = article.url,
                isFav = false
            )
            databaseRepository.saveArticleById(newsEntity)
        }
    }
    fun deleteNewsById(article: Article){
        viewModelScope.launch {
            val newsEntity = NewsEntities(
                content = article.content,
                description = article.description,
                id = article.id,
                image = article.image,
                lang = article.lang,
                publishedAt = article.publishedAt,
                title = article.title,
                source = article.source.name,
                url = article.url,
                isFav = false
            )
            databaseRepository.deleteArticleById(newsEntity)
        }
    }

}