package me.brunofelix.news.feature.domain.repository

import kotlinx.coroutines.flow.Flow
import me.brunofelix.news.core.util.Resource
import me.brunofelix.news.feature.domain.model.Article

interface NewsRemoteRepository {
    suspend fun getNews(countryCode: String, pageNumber: Int): Flow<Resource<List<Article>>>
}