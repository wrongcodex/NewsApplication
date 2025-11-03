package com.example.newsapplication.core.repositories.binds

import com.example.newsapplication.core.repositories.impl.DatabaseRepositoryImpl
import com.example.newsapplication.core.repositories.repos.DatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindDatabaseRepository {
    @Binds
    abstract fun bindsDatabaseRepository(databaseRepositoryImpl: DatabaseRepositoryImpl): DatabaseRepository
}