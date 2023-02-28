package com.example.rickmorty.feature.location.data.remote.dto


import com.example.rickmorty.feature.character.data.remote.dto.InfoDto
import com.google.gson.annotations.SerializedName

data class LocationsEntryDto(
    @SerializedName("info")
    val info: InfoDto,
    @SerializedName("results")
    val results: List<LocationDto>
)