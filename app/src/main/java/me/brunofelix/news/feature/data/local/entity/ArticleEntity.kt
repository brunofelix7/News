package me.brunofelix.news.feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.brunofelix.news.feature.domain.model.Article
import me.brunofelix.news.feature.domain.model.Source

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val source: Source?,
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
            source = source
        )
    }
}
