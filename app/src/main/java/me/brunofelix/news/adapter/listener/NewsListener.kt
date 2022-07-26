package me.brunofelix.news.adapter.listener

import me.brunofelix.news.feature.domain.model.Article

interface NewsListener {
    fun onItemClick(article: Article)
}