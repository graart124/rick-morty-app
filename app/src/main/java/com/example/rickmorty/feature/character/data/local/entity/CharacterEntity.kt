package com.example.rickmorty.feature.character.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickmorty.feature.character.domain.models.Character

@Entity
data class CharacterEntity(
    val episodes: List<String>,
    val gender: String,
    @PrimaryKey val id: Int,
    val imageUrl: String,
    val lastKnownLocationName: String,
    val lastKnownLocationUrl:String,
    val originName:String,
    val originUrl:String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
){
    fun toCharacter():Character{
        return Character(
            episodes, gender, id, imageUrl, lastKnownLocationName, lastKnownLocationUrl,originName,originUrl, name, species, status, type, url
        )
    }
}