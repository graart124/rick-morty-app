package com.example.rickmorty.feature.location.presentation.locations_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.core.util.Resource
import com.example.rickmorty.feature.character.data.repository.PAGE_SIZE
import com.example.rickmorty.feature.character.presentation.characters_list.CharactersListViewModel
import com.example.rickmorty.feature.location.domain.use_case.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsListViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase
) : ViewModel() {

    private val _locationListState = mutableStateOf(LocationsState())
    val locationListState: State<LocationsState> = _locationListState

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private var lastSearchedName = ""

    private val _isHintDisplayed = mutableStateOf(true)
    val isHintDisplayed: State<Boolean> = _isHintDisplayed

    private var curPage = 1
    private var searchJob: Job? = null

    init {
        load()
    }

    fun onEvent(event: LocationsListEvent) {
        when (event) {
            is LocationsListEvent.ChangeNameFocus -> {
                _isHintDisplayed.value = !event.focusState.isFocused
                        && name.value.isBlank()
            }
            is LocationsListEvent.EnteredName -> {
                _name.value = event.value
                load()
            }
            LocationsListEvent.LoadLocationsPagineted -> {
                load()
            }
        }
    }

    private fun load() {
        if (lastSearchedName != name.value) {
            curPage = 1
            _locationListState.value = LocationsState(
                locations = emptyList(),
                isLoading = false,
                errorLoading = false,
                endReached = false,
                isSearching = name.value.isNotBlank()
            )
        }
        if (locationListState.value.isSearching) {
            searchLocationsPagineted()
        } else {
            loadLocationsPagineted()
        }
    }

    private fun loadLocationsPagineted() {
        viewModelScope.launch {
            locationUseCase.getLocations(curPage).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _locationListState.value = locationListState.value.copy(
                            locations = locationListState.value.locations+(result.data ?: emptyList()),
                            isLoading = false,
                            errorLoading = true
                        )
                    }
                    is Resource.Loading -> {
                        _locationListState.value = locationListState.value.copy(
                            isLoading = true,
                            errorLoading = false
                        )
                    }
                    is Resource.Success -> {
                        _locationListState.value = locationListState.value.copy(
                            locations = locationListState.value.locations + result.data!!,
                            isLoading = false,
                            errorLoading = false,
                            endReached = PAGE_SIZE - 1 >= result.data.size
                        )
                        curPage++
                    }
                }
            }.launchIn(this)
        }
    }

    private fun searchLocationsPagineted() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            val result = locationUseCase.searchLocation(curPage, name = name.value)
            when (result) {
                is Resource.Error -> {
                    _locationListState.value = locationListState.value.copy(
                        locations = locationListState.value.locations+(result.data ?: emptyList()),
                        isLoading = false,
                        errorLoading = true
                    )
                }
                is Resource.Success -> {
                    lastSearchedName = name.value
                    _locationListState.value = locationListState.value.copy(
                        locations = locationListState.value.locations + result.data!!,
                        isLoading = false,
                        errorLoading = false,
                        endReached = PAGE_SIZE - 1 >= result.data.size
                    )
                    curPage++
                }
                else -> {}
            }
        }
    }

}