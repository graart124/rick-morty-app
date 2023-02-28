package com.example.rickmorty.feature.location.data.remote.dto


import com.example.rickmorty.feature.location.data.local.entity.LocationEntity
import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("created")
    val created: String,
    @SerializedName("dimension")
    val dimension: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("residents")
    val residents: List<String>,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
) {
    fun toLocationEntity(): LocationEntity {
        return LocationEntity(
            id = id,
            dimension = dimension,
            name = name,
            type = type,
            charactersIdsForRequest = convertIdsToRequest(residents)
        )
    }
}

fun convertIdsToRequest(residents: List<String>): String {
    val ids = residents.map { it.takeLastWhile { char -> char != '/' } }
    return buildString {
        append("[")
        ids.forEachIndexed { index, id ->
            append(id)
            if (index < ids.size-1)
                append(",")
        }
        append("]")
    }
}