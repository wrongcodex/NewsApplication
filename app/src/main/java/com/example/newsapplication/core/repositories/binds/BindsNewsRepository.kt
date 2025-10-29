package com.example.newsapplication.core.repositories.binds

import com.example.newsapplication.core.repositories.impl.NewsRepositoryImpl
import com.example.newsapplication.core.repositories.repos.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsNewsRepository {

    @Binds
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}