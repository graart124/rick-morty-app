package com.example.rickmorty.feature.location.domain.use_case

import com.example.rickmorty.feature.location.domain.models.Location
import com.example.rickmorty.feature.location.domain.repository.LocationRepository

class GetLocation(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(id:Int): Location {
        return repository.getLocationById(id)
    }
}