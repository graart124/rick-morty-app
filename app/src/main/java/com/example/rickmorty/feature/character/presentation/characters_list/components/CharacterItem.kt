package com.example.rickmorty.feature.character.presentation.characters_list.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.rickmorty.feature.character.domain.models.Character
import com.example.rickmorty.ui.components.CharacterImage
import com.example.rickmorty.ui.util.calculateStatusColor


@ExperimentalCoilApi
@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: (Character) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(16.dp))
            .clickable {
                onClick(character)
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CharacterImage(
            modifier = Modifier.size(160.dp),
            imageModifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)),
            character = character
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = character.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(color = calculateStatusColor(character.status))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = character.status + "  -  " + character.species,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            CharacteristicBlock(
                characteristicName = "Gender",
                characteristicInfo = character.gender
            )
            Spacer(modifier = Modifier.height(8.dp))
            CharacteristicBlock(
                characteristicName = "Last known location",
                characteristicInfo = character.lastKnownLocationName
            )

        }
    }

}

@Composable
fun CharacteristicBlock(
    characteristicName: String,
    characteristicInfo: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$characteristicName:",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp,
        )
        Text(
            text = characteristicInfo,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp,
        )
    }
}