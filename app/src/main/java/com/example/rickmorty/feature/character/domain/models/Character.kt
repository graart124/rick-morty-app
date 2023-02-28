package com.example.rickmorty.feature.character.domain.models

data class Character(
    val episodes: List<String>,
    val gender: String,
    val id: Int,
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
)
