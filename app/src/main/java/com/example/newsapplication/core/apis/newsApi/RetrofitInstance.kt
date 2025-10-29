package com.example.newsapplication.core.apis.newsApi

import com.example.newsapplication.core.apis.newsApi.services.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {
    private const val base_url = "https://gnews.io/api/v4/"
    @Singleton
    @Provides
    fun getNewsResponse(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesNewsService(retrofit: Retrofit): NewsApiService{
        return retrofit.create(NewsApiService::class.java)
    }

    //val newsApiService: NewsApiService = RetrofitInstance.getNewsResponse().create(NewsApiService::class.java)
}

//
//@Module
//@InstallIn(SingletonComponent::class)
//object RetrofitInstance {
//    private const val base_url = "https://gnews.io/api/v4/"
//
//    @Singleton
//    @Provides
//    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
//        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//    }
//
//    @Singleton
//    @Provides
//    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor) // Add the interceptor here
//            .build()
//    }
//    @Singleton
//    @Provides
//    fun getNewsResponse(okHttpClient: OkHttpClient): Retrofit{
//        return Retrofit.Builder()
//            .baseUrl(base_url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun providesNewsService(retrofit: Retrofit): NewsApiService{
//        return retrofit.create(NewsApiService::class.java)
//    }
//
//    //val newsApiService: NewsApiService = RetrofitInstance.getNewsResponse().create(NewsApiService::class.java)
//}