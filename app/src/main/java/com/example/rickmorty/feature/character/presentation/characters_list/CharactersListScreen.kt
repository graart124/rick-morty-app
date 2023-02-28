@file:OptIn(ExperimentalAnimationApi::class)

package com.example.rickmorty.feature.character.presentation.characters_list

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.rickmorty.R
import com.example.rickmorty.feature.character.presentation.characters_list.components.CharacterItem
import com.example.rickmorty.ui.components.SearchBox
import com.example.rickmorty.feature.character.presentation.characters_list.components.SearchCharactersQuerySection
import com.example.rickmorty.feature.destinations.CharacterDetailScreenDestination
import com.example.rickmorty.ui.components.TopBarLogo
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@OptIn(ExperimentalCoilApi::class)
@Composable
@RootNavGraph(start = true)
@Destination
fun CharactersListScreen(
    viewModel: CharactersListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val characterListState = viewModel.characterListState
    val isSearchCharactersQuerySectionVisible = viewModel.isSearchCharactersQuerySectionVisible
    val isHintDisplayed = viewModel.isHintDisplayed

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CharactersListViewModel.UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost ={ SnackbarHost(hostState =snackbarHostState )} ,
        topBar = {
            TopBarLogo(resourceId = R.drawable.rickmortyimage)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp)
                .padding(top = it.calculateTopPadding())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBox(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = characterListState.value.searchCharactersQuery.name,
                    isHintDisplayed = isHintDisplayed.value,
                    onValueChange = {
                        viewModel.onEvent(CharactersListEvent.EnteredName(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(CharactersListEvent.ChangeNameFocus(it))
                    }
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(CharactersListEvent.ToggleSearchCharactersQuerySection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Sort",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            AnimatedVisibility(
                visible = isSearchCharactersQuerySectionVisible.value,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SearchCharactersQuerySection(
                    onSearchCharactersQueryChange = {
                        viewModel.onEvent(CharactersListEvent.SearchCharactersQueryChange(it))
                    }, searchCharactersQuery = characterListState.value.searchCharactersQuery
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(characterListState.value.characters) { id, character ->

                    if (id >= characterListState.value.characters.size - 1 && !characterListState.value.isLoading && !characterListState.value.endReached) {
                        LaunchedEffect(key1 = true) {
                            viewModel.onEvent(CharactersListEvent.LoadCharactersPagineted)
                        }
                    }

                    if (character.id != characterListState.value.characters[0].id) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    CharacterItem(character = character, onClick = {
                        navigator.navigate(CharacterDetailScreenDestination(characterId = character.id))
                    })
                }
            }
        }
    }

}