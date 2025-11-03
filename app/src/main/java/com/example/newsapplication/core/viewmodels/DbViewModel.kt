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
    private val _articlees = MutableStateFlow<List<NewsEntities>>(emptyList())
    val articlees: StateFlow<List<NewsEntities>> = _articlees

    fun getAllNews(){
        viewModelScope.launch {
            databaseRepository.getAllArticles().collect { listOfArticles ->
                _articlees.value = listOfArticles
            }
        }
    }
//    fun saveNews(newsEntities: NewsEntities){
//        viewModelScope.launch {
//            val data = NewsEntities(
//                content = newsEntities.content,
//                description = newsEntities.description,
//                id = newsEntities.id,
//                image = newsEntities.image,
//                lang = newsEntities.lang,
//                publishedAt = newsEntities.publishedAt,
//                source = newsEntities.source,
//                title = newsEntities.title,
//                url = newsEntities.url
//            )
//            databaseRepository.saveArticleById(data)
//        }
//    }
//    fun deleteNewsById(newsEntities: NewsEntities){
//        viewModelScope.launch {
//            databaseRepository.deleteArticleById(newsEntities)
//        }
//    }

    private val _artilces = MutableStateFlow<List<Article>>(emptyList())
    val artilces : SharedFlow<List<Article>> = _artilces

    //    fun getAllNews(): List<Article>{
//        viewModelScope.launch {
//            _dbNews.value = databaseRepository.getAllArticles()
//        }
//        //return
//        return _dbNews.value
//    }
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
                url = article.url
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
                url = article.url
            )
            databaseRepository.deleteArticleById(newsEntity)
        }
    }

}