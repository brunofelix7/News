package me.brunofelix.news.feature.domain.repository

import androidx.lifecycle.LiveData
import me.brunofelix.news.feature.data.local.entity.ArticleEntity
import me.brunofelix.news.feature.domain.model.Article

interface NewsLocalRepository {
    suspend fun insertNews(article: Article): Long
    suspend fun deleteNews(article: Article)
    fun getLocalNews(): LiveData<List<ArticleEntity>>
}