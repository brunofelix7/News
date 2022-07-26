package me.brunofelix.news.feature.presentation.news

import me.brunofelix.news.feature.domain.model.Article

data class NewsState (
    val isLoading: Boolean = false,
    val articleList: List<Article> = emptyList()
)