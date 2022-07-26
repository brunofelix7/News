package me.brunofelix.news.feature.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import me.brunofelix.news.feature.domain.model.Source

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}