package me.brunofelix.news.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import me.brunofelix.news.feature.data.local.entity.ArticleEntity

@Parcelize
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val source: Source?
) : Parcelable {
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
