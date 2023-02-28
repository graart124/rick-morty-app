package com.example.rickmorty.feature.location.domain.repository

import com.example.rickmorty.core.util.Resource
import com.example.rickmorty.feature.location.domain.models.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocations(page:Int):Flow<Resource<List<Location>>>

    suspend fun searchLocations(page: Int,name:String):Resource<List<Location>>

    suspend fun getLocationById(id:Int):Location
}