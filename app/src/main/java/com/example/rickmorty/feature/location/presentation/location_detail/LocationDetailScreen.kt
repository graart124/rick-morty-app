@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rickmorty.feature.location.presentation.location_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickmorty.R
import com.example.rickmorty.feature.character.domain.models.Character
import com.example.rickmorty.feature.destinations.CharacterDetailScreenDestination
import com.example.rickmorty.feature.location.domain.models.Location
import com.example.rickmorty.ui.components.CharacterImage
import com.example.rickmorty.ui.components.CharacteristicInfoSection
import com.example.rickmorty.ui.theme.Gray3
import com.example.rickmorty.ui.theme.Roboto
import com.example.rickmorty.ui.theme.TopBarColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalCoilApi
@Composable
@Destination
fun LocationDetailScreen(
    navigator: DestinationsNavigator,
    viewModel: LocationDetailViewModel = hiltViewModel(),
    locationId: Int
) {
    val location = viewModel.location

    LaunchedEffect(key1 = true) {
        viewModel.loadLocation(id = locationId)
    }

    Scaffold(
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
            if (location.value != null) {
                LocationDetailInfo(
                    location = location.value!!,
                    onResidentClick = { id ->
                        navigator.navigate(CharacterDetailScreenDestination(characterId = id))
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

@Composable
fun LocationDetailInfo(
    location: Location,
    onResidentClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = location.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Roboto,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Information",
                fontSize = 20.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        CharacteristicInfoSection(infoName = "Dimension", infoValue = location.dimension)
        CharacteristicInfoSection(infoName = "Type", infoValue = location.type)
        Spacer(modifier = Modifier.height(16.dp))
        if (location.residents.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Residents",
                    fontSize = 20.sp,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ResidentsSection(location.residents, onResidentClick = onResidentClick)
        }
    }
}

@Composable
fun ResidentsSection(
    residents: List<Character>,
    onResidentClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(residents) {
            ResidentItem(resident = it, onResidentClick = onResidentClick)
        }
    }
}

@Composable
fun ResidentItem(
    resident: Character,
    onResidentClick: (Int) -> Unit
) {
    CharacterImage(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onResidentClick(resident.id)
            }, imageModifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp)), character = resident
    )

    Box(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        TopBarColor
                    )
                ), RoundedCornerShape(10.dp)
            ), contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .padding(horizontal = 2.dp),
            text = resident.name,
            style = MaterialTheme.typography.displayMedium,
            fontSize = 18.sp
        )
    }
}