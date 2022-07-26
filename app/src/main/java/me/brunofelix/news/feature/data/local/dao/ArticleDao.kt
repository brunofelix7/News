package me.brunofelix.news.feature.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.brunofelix.news.feature.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: ArticleEntity): Long

    @Delete
    suspend fun delete(news: ArticleEntity)

    @Query("SELECT * FROM articles")
    fun findAll(): LiveData<List<ArticleEntity>>
}