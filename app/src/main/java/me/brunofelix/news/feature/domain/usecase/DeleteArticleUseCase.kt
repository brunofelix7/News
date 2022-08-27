package me.brunofelix.news.feature.domain.usecase

import me.brunofelix.news.feature.domain.model.Article
import me.brunofelix.news.feature.domain.repository.NewsLocalRepository
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    private val repository: NewsLocalRepository
) {

    suspend operator fun invoke(article: Article) {
        return repository.deleteNews(article)
    }
}