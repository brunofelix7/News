package me.brunofelix.news.data.api.dto

data class NewsDto(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleDto>
)
