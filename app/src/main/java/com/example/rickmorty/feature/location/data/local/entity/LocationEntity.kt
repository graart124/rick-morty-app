package com.example.rickmorty.feature.location.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickmorty.feature.location.domain.models.Location

@Entity(tableName = "location_items")
data class LocationEntity(
    @PrimaryKey val id: Int,
    val dimension: String,
    val name: String,
    val charactersIdsForRequest: String,
    val type: String
){
    fun toLocation(): Location {
        return Location(id,dimension,name,type)
    }
}
