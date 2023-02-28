package com.example.rickmorty.feature.character.data.remote.dto


import com.example.rickmorty.feature.character.data.local.entity.CharacterEntity
import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val locationDto: LocationDto,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val originDto: OriginDto,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
){
    fun toCharacterEntry():CharacterEntity{
        return CharacterEntity(
            episodes = episode,
            gender = gender,
            id=id,
            imageUrl = image,
            lastKnownLocationName = locationDto.name,
            lastKnownLocationUrl = locationDto.url,
            originName = originDto.name,
            originUrl = originDto.url,
            name = name,
            species=species,
            status=status,
            type = type,
            url = url
        )
    }
}