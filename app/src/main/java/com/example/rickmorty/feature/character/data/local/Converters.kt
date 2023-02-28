package com.example.rickmorty.feature.character.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.rickmorty.feature.character.data.util.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromEpisodesJson(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningsJson(episodes: List<String>): String {
        return jsonParser.toJson(
            episodes,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: "[]"
    }
}