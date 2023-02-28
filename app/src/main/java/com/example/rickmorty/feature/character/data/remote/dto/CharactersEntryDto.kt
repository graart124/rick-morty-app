package com.example.rickmorty.feature.character.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CharactersEntryDto(
    @SerializedName("info")
    val infoDto: InfoDto,
    @SerializedName("results")
    val results: List<CharacterDto>
)