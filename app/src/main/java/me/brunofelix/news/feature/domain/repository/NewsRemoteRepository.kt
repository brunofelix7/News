package me.brunofelix.news.feature.domain.repository

import me.brunofelix.news.core.util.Resource
import me.brunofelix.news.feature.domain.model.Article

interface NewsRemoteRepository {
    suspend fun getNews(): Resource<List<Article>>
}