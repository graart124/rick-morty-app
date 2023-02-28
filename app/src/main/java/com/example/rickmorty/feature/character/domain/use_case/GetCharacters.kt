package com.example.rickmorty.feature.character.domain.use_case

import com.example.rickmorty.core.util.Resource
import com.example.rickmorty.feature.character.domain.models.Character
import com.example.rickmorty.feature.character.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharacters(
    private val repository: CharacterRepository
) {
    operator fun invoke(page:Int): Flow<Resource<List<Character>>> {
        return repository.getCharacters(page = page)
    }
}