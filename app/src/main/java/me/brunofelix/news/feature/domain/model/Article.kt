package me.brunofelix.news.feature.domain.model

import me.brunofelix.news.feature.data.local.entity.ArticleEntity

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val source: Source
) {
    fun toArticleEntity(): ArticleEntity {
        return ArticleEntity(
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            title = title,
            url = url,
            urlToImage = urlToImage,
            source = source
        )
    }
}
