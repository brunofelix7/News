package me.brunofelix.news.feature.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.brunofelix.news.core.util.Resource
import me.brunofelix.news.feature.domain.model.Article
import me.brunofelix.news.feature.domain.repository.NewsRemoteRepository
import javax.inject.Inject

class GetRemoteNewsUseCase @Inject constructor(
    private val repository: NewsRemoteRepository
) {

    suspend operator fun invoke(countryCode: String, pageNumber: Int): Flow<Resource<List<Article>>> {
        return repository.getNews(countryCode, pageNumber)
    }
}