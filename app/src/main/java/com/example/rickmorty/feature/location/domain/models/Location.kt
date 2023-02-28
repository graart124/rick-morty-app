package com.example.rickmorty.feature.location.domain.models

import com.example.rickmorty.feature.character.domain.models.Character

data class Location(
    val id: Int,
    val dimension: String,
    val name: String,
    val type: String,
    var residents: List<Character> = emptyList()
)
