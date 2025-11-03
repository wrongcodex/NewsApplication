package com.example.newsapplication.core.db.NewsDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [NewsEntities::class], exportSchema = false, version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDAO
//    companion object{
//        private var instance : NewsDatabase? = null
//
//        @Synchronized
//        fun getInstance(context: Context): NewsDatabase{
//            if (instance == null)
//                instance = Room.databaseBuilder(
//                    context.applicationContext, NewsDatabase::class.java, "weather_table"
//                ).fallbackToDestructiveMigration(false).build()
//            return instance!!
//        }
//    }
}