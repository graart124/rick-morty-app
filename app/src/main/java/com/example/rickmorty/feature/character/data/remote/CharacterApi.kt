package com.example.rickmorty.feature.character.data.remote

import com.example.rickmorty.feature.character.data.remote.dto.CharacterDto
import com.example.rickmorty.feature.character.data.remote.dto.CharactersEntryDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("character/")
    suspend fun getCharactersEntry(
        @Query("page") page:Int,
    ):CharactersEntryDto

    @GET("character/")
    suspend fun getFilteredCharactersList(
        @Query("page") page: Int=1,
        @Query("name") name: String?,
        @Query("gender") gender:String?,
        @Query("status") status:String?
    ):CharactersEntryDto

    @GET("character/{ids}")
    suspend fun getMultipleCharactersList(
        @Path("ids") ids:String
    ):List<CharacterDto>

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}