package com.example.rickmorty.feature.character.domain.repository

import com.example.rickmorty.core.util.Resource
import com.example.rickmorty.feature.character.domain.models.Character
import kotlinx.coroutines.flow.Flow


interface CharacterRepository {

    fun getCharacters(page:Int): Flow<Resource<List<Character>>>

    suspend fun searchCharacters(page:Int=1,name:String?,gender:String?,status:String?):Resource<List<Character>>

    suspend fun getCharacterById(id:Int):Character
}