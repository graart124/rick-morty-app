package com.example.rickmorty.feature.location.domain.use_case

import com.example.rickmorty.core.util.Resource
import com.example.rickmorty.feature.location.domain.models.Location
import com.example.rickmorty.feature.location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocations(
    private val repository: LocationRepository
) {
    operator fun invoke(page:Int):Flow<Resource<List<Location>>>{
        return repository.getLocations(page = page)
    }
}