package me.brunofelix.news.feature.domain.usecase

import androidx.lifecycle.LiveData
import me.brunofelix.news.feature.data.local.entity.ArticleEntity
import me.brunofelix.news.feature.domain.model.Article
import me.brunofelix.news.feature.domain.repository.NewsLocalRepository
import javax.inject.Inject

class GetLocalNewsUseCase @Inject constructor(
    private val repository: NewsLocalRepository
) {

    operator fun invoke(): LiveData<List<ArticleEntity>> {
        return repository.getLocalNews()
    }
}