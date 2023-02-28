package com.example.rickmorty.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickmorty.feature.character.domain.models.Character

@Composable
fun CharacterImage(
    modifier: Modifier,
    imageModifier: Modifier,
    character: Character
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        var showProgressBar by remember { mutableStateOf(true) }
        if (showProgressBar) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        AsyncImage(
            modifier = imageModifier,
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.imageUrl)
                .build(),
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            onSuccess = {
                showProgressBar = false
            }, onError = {showProgressBar = false}
        )
    }
}