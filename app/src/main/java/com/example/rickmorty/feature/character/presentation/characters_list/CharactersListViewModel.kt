package com.example.rickmorty.feature.character.presentation.characters_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.core.util.Resource
import com.example.rickmorty.feature.character.data.repository.PAGE_SIZE
import com.example.rickmorty.feature.character.domain.models.*
import com.example.rickmorty.feature.character.domain.use_case.CharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val charactersUseCase: CharactersUseCase,
) : ViewModel() {

    private val _characterListState = mutableStateOf(CharactersState())
    val characterListState: State<CharactersState> = _characterListState

    private val _isSearchCharactersQuerySectionVisible = mutableStateOf(false)
    val isSearchCharactersQuerySectionVisible: State<Boolean> =
        _isSearchCharactersQuerySectionVisible

    private val _isHintDisplayed = mutableStateOf(true)
    val isHintDisplayed: State<Boolean> = _isHintDisplayed

    private var lastKnownSearchCharactersQuery: SearchCharactersQuery = SearchCharactersQuery(
        name = "", status = Status.All, gender = Gender.All)
    private var curPage: Int = 1

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    init {
        loadCharactersPagineted()
    }

    fun onEvent(event: CharactersListEvent) {
        when (event) {
            is CharactersListEvent.ChangeNameFocus -> {
                _isHintDisplayed.value = !event.focusState.isFocused
                        && characterListState.value.searchCharactersQuery.name.isBlank()
            }
            is CharactersListEvent.EnteredName -> {
                _characterListState.value = characterListState.value.copy(
                    searchCharactersQuery = characterListState.value.searchCharactersQuery.copy(
                        name = event.value
                    )
                )
                load()
            }
            is CharactersListEvent.SearchCharactersQueryChange -> {
                _characterListState.value = characterListState.value.copy(
                    searchCharactersQuery = event.searchCharactersQuery
                )
                load()
            }
            CharactersListEvent.ToggleSearchCharactersQuerySection -> {
                _isSearchCharactersQuerySectionVisible.value =
                    !isSearchCharactersQuerySectionVisible.value
            }
            CharactersListEvent.LoadCharactersPagineted -> {
                load()
            }
        }
    }

    private fun load() {
        searchCharactersQueryChecker()
        if (characterListState.value.isSearching) {
            searchCharactersPagineted()
        } else {
            loadCharactersPagineted()
        }
    }

    private fun searchCharactersQueryChecker() {
        if (!lastKnownSearchCharactersQuery.isEqual(_characterListState.value.searchCharactersQuery)) {
            curPage = 1
            _characterListState.value = characterListState.value.copy(
                characters = emptyList(),
                isSearching = !characterListState.value.searchCharactersQuery.isDefaultQuery(),
                endReached = false
            )
        }
    }

    private fun loadCharactersPagineted() {
        viewModelScope.launch {
            charactersUseCase.getCharacters(curPage).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _characterListState.value = characterListState.value.copy(
                            characters = characterListState.value.characters+(result.data ?: emptyList()),
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar(
                            result.message ?: "Unknown error"
                        ))
                    }
                    is Resource.Loading -> {
                        _characterListState.value = characterListState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        lastKnownSearchCharactersQuery =
                            characterListState.value.searchCharactersQuery
                        _characterListState.value = characterListState.value.copy(
                            characters = characterListState.value.characters + result.data!!,
                            isLoading = false,
                            endReached = PAGE_SIZE - 1 >= result.data.size
                        )
                        curPage++
                    }
                }
            }.launchIn(this)
        }
    }

    private fun searchCharactersPagineted() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            val result = charactersUseCase.searchCharacters(curPage,
                characterListState.value.searchCharactersQuery)
            when (result) {
                is Resource.Error -> {
                    _characterListState.value = characterListState.value.copy(
                        characters = characterListState.value.characters+(result.data ?: emptyList()),
                        isLoading = false
                    )
                    _eventFlow.emit(UIEvent.ShowSnackbar(
                        result.message ?: "Unknown error"
                    ))
                }
                is Resource.Success -> {
                    lastKnownSearchCharactersQuery = characterListState.value.searchCharactersQuery
                    _characterListState.value = characterListState.value.copy(
                        characters = characterListState.value.characters + result.data!!,
                        isLoading = false,
                        endReached = PAGE_SIZE - 1 >= result.data.size
                    )
                    curPage++
                }
                else -> {}
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}