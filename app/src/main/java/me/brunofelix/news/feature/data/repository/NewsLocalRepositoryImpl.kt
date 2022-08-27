package me.brunofelix.news.feature.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.brunofelix.news.core.util.Resource
import me.brunofelix.news.feature.data.local.NewsDatabase
import me.brunofelix.news.feature.data.local.dao.ArticleDao
import me.brunofelix.news.feature.data.local.entity.ArticleEntity
import me.brunofelix.news.feature.domain.model.Article
import me.brunofelix.news.feature.domain.repository.NewsLocalRepository
import javax.inject.Inject

class NewsLocalRepositoryImpl @Inject constructor(
    private val db: NewsDatabase
) : NewsLocalRepository {

    override suspend fun insertNews(article: Article): Long {
        return db.newsDao.insert(article.toArticleEntity())
    }

    override suspend fun deleteNews(article: Article) {
        db.newsDao.delete(article.toArticleEntity())
    }

    override fun getLocalNews(): LiveData<List<ArticleEntity>> {
        return db.newsDao.findAll()
    }
}