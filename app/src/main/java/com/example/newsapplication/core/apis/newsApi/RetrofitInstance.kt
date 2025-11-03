package com.example.newsapplication.core.apis.newsApi

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.example.newsapplication.core.apis.newsApi.services.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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

    @Singleton
    @Provides
    fun providesImageLoader(@ApplicationContext context: Context): ImageLoader{
        return ImageLoader.Builder(context)
            .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
            }
            .diskCache {
                DiskCache.Builder()
                    // This is the directory that was null before
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }.okHttpClient {
                OkHttpClient.Builder().build()
            }.crossfade(true)
            .build()
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