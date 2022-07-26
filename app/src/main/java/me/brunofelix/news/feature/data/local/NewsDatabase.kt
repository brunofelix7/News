package me.brunofelix.news.feature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.brunofelix.news.feature.data.local.converters.Converters
import me.brunofelix.news.feature.data.local.dao.ArticleDao
import me.brunofelix.news.feature.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): ArticleDao
}