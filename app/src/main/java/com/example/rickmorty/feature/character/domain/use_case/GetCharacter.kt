package com.example.rickmorty.feature.character.domain.use_case

import com.example.rickmorty.feature.character.domain.models.Character
import com.example.rickmorty.feature.character.domain.repository.CharacterRepository

class GetCharacter(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id:Int): Character? {
        return repository.getCharacterById(id = id)
    }
}