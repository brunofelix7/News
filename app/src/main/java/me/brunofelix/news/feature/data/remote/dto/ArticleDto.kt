package me.brunofelix.news.feature.data.remote.dto

import me.brunofelix.news.feature.data.local.entity.ArticleEntity

data class ArticleDto(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val title: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val source: SourceDto,
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
            source = source.toSource()
        )
    }
}
