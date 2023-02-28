package com.example.rickmorty.feature.character.domain.use_case

data class CharactersUseCase(
    val getCharacters: GetCharacters,
    val searchCharacters: SearchCharacters,
    val getCharacter: GetCharacter
)