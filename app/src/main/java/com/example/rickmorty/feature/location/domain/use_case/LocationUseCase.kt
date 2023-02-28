package com.example.rickmorty.feature.location.domain.use_case

data class LocationUseCase(
    val getLocation: GetLocation,
    val searchLocation: SearchLocation,
    val getLocations: GetLocations
)
