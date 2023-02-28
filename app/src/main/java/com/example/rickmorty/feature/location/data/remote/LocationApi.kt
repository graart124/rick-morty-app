package com.example.rickmorty.feature.location.data.remote

import com.example.rickmorty.feature.location.data.remote.dto.LocationDto
import com.example.rickmorty.feature.location.data.remote.dto.LocationsEntryDto
import retrofit2.http.GET
import retrofit2.http.Query


interface LocationApi {

    @GET("location/")
    suspend fun getLocationEntry(
        @Query("page") page:Int
    ):LocationsEntryDto

    @GET("location/")
    suspend fun getSearchedLocationEntry(
        @Query("page") page:Int,
        @Query("name") name:String
    ):LocationsEntryDto

    @GET("location/{id}")
    suspend fun getLocationById(id:Int):LocationDto

}