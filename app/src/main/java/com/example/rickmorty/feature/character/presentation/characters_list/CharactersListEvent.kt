package com.example.rickmorty.feature.character.presentation.characters_list

import androidx.compose.ui.focus.FocusState
import com.example.rickmorty.feature.character.domain.models.SearchCharactersQuery

sealed class CharactersListEvent{
    data class SearchCharactersQueryChange(val searchCharactersQuery: SearchCharactersQuery) : CharactersListEvent()
    data class EnteredName(val value: String): CharactersListEvent()
    data class ChangeNameFocus(val focusState: FocusState): CharactersListEvent()
    object ToggleSearchCharactersQuerySection : CharactersListEvent()
    object LoadCharactersPagineted:CharactersListEvent()
}
