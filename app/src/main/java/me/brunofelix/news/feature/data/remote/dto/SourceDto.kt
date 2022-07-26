package me.brunofelix.news.feature.data.remote.dto

import me.brunofelix.news.feature.domain.model.Source

data class SourceDto(
    val id: String?,
    val name: String
) {
    fun toSource(): Source {
        return Source(
            id = id,
            name = name
        )
    }
}
