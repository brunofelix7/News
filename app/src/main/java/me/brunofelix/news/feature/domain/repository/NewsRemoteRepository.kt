package me.brunofelix.news.feature.domain.repository

import kotlinx.coroutines.flow.Flow
import me.brunofelix.news.core.util.Resource
import me.brunofelix.news.feature.domain.model.Article

interface NewsRemoteRepository {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Flow<Resource<List<Article>>>
    suspend fun searchNews(countryCode: String, pageNumber: Int): Flow<Resource<List<Article>>>
}