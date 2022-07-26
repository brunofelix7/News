package me.brunofelix.news.feature.data.remote.dto

import me.brunofelix.news.feature.domain.model.Article

data class ArticleDto(
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val title: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val source: SourceDto? = SourceDto(),
) {
    fun toArticle(): Article {
        return Article(
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            title = title,
            url = url,
            urlToImage = urlToImage,
            source = source?.toSource()
        )
    }
}
