package com.example.newsapplication.core.db.NewsDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapplication.core.models.newsModel.Source


@Entity(tableName = "news_table")
data class NewsEntities (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("roomId") val roomId: Int = 0,
    val content: String,
    val description: String,
    val id: String,
    val image: String,
    val lang: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    val isFav: Boolean
)