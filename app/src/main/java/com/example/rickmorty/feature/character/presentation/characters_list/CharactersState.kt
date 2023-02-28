package com.example.rickmorty.feature.character.presentation.characters_list

import com.example.rickmorty.feature.character.domain.models.*

data class CharactersState (
    val characters:List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val isSearching:Boolean = false,
    val endReached:Boolean = false,
    val searchCharactersQuery: SearchCharactersQuery = SearchCharactersQuery(name="", status = Status.All, gender = Gender.All)
)