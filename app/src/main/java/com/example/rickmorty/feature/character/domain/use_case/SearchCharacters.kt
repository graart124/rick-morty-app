package com.example.rickmorty.feature.character.domain.use_case

import com.example.rickmorty.core.util.Resource
import com.example.rickmorty.feature.character.domain.models.Character
import com.example.rickmorty.feature.character.domain.models.SearchCharactersQuery
import com.example.rickmorty.feature.character.domain.repository.CharacterRepository

class SearchCharacters(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(page:Int,searchCharactersQuery: SearchCharactersQuery): Resource<List<Character>> {
        return repository.searchCharacters(
            page = page,
            name = searchCharactersQuery.name,
            gender = searchCharactersQuery.gender.value,
            status = searchCharactersQuery.status.value
        )
    }
}