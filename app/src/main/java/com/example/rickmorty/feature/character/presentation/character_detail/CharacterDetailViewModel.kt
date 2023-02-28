package com.example.rickmorty.feature.character.presentation.character_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.feature.character.domain.models.Character
import com.example.rickmorty.feature.character.domain.repository.CharacterRepository
import com.example.rickmorty.feature.character.domain.use_case.CharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val useCase: CharactersUseCase
) : ViewModel() {

    private val _character = mutableStateOf<Character?>(null)
    val character: State<Character?> = _character

    fun loadCharacterInfo(id: Int) {
        viewModelScope.launch {
            _character.value = useCase.getCharacter(id)
        }
    }
}