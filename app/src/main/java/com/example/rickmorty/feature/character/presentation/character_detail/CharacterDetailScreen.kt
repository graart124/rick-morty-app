package com.example.rickmorty.feature.character.presentation.character_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.rickmorty.R
import com.example.rickmorty.feature.character.presentation.character_detail.components.CharacterDetailInfo
import com.example.rickmorty.feature.destinations.LocationDetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalCoilApi
@ExperimentalMaterial3Api
@Composable
@Destination
fun CharacterDetailScreen(
    navigator: DestinationsNavigator,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
    characterId: Int
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val character = viewModel.character

    LaunchedEffect(key1 = true) {
        viewModel.loadCharacterInfo(characterId)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "GO BACK",

                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator.navigateUp()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "arrow_back"
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (character.value != null) {
                CharacterDetailInfo(
                    character = character.value!!,
                    modifier = Modifier.fillMaxSize(),
                    onInfoWithUrlClick = { id->
                        navigator.navigate(LocationDetailScreenDestination(locationId = id))
                    }
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(0.4f),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

