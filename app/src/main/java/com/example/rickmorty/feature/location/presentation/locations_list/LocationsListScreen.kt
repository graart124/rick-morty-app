@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rickmorty.feature.location.presentation.locations_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickmorty.R
import com.example.rickmorty.feature.destinations.LocationDetailScreenDestination
import com.example.rickmorty.feature.location.domain.models.Location
import com.example.rickmorty.ui.components.SearchBox
import com.example.rickmorty.ui.components.TopBarLogo
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LocationsListScreen(
    navigator: DestinationsNavigator,
    viewModel: LocationsListViewModel = hiltViewModel()
) {
    val locationsState = viewModel.locationListState
    val name = viewModel.name
    val isHintDisplayed = viewModel.isHintDisplayed


    Scaffold(
        topBar = {
            TopBarLogo(resourceId = R.drawable.portal_rick_morty)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp)
                .padding(top = it.calculateTopPadding())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchBox(
                text = name.value,
                isHintDisplayed = isHintDisplayed.value,
                onValueChange = { text ->
                    viewModel.onEvent(LocationsListEvent.EnteredName(text))
                },
                onFocusChange = { focusState ->
                    viewModel.onEvent(LocationsListEvent.ChangeNameFocus(focusState))
                }, hint = "Enter location name..."
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (locationsState.value.isLoading && locationsState.value.locations.isEmpty()) {
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
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(locationsState.value.locations) { id, location ->

                        if (id >= locationsState.value.locations.size - 1 && !locationsState.value.isLoading && !locationsState.value.endReached) {
                            LaunchedEffect(key1 = true) {
                                viewModel.onEvent(LocationsListEvent.LoadLocationsPagineted)
                            }
                        }

                        if (location.id != locationsState.value.locations[0].id) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        LocationItem(location = location, onClick = {
                            navigator.navigate(LocationDetailScreenDestination(locationId = location.id))
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun LocationItem(
    location: Location,
    onClick: (Location) -> Unit
) {
    Button(
        onClick = { onClick(location) },
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = location.type,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}