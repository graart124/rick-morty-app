package com.example.rickmorty.feature.location.domain.use_case

import com.example.rickmorty.core.util.Resource
import com.example.rickmorty.feature.location.domain.models.Location
import com.example.rickmorty.feature.location.domain.repository.LocationRepository

class SearchLocation(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(page:Int,name:String):Resource<List<Location>>{
        return repository.searchLocations(page,name)
    }
}