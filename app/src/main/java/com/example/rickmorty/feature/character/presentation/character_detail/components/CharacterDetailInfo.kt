package com.example.rickmorty.feature.character.presentation.character_detail.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.rickmorty.R
import com.example.rickmorty.feature.character.domain.models.Character
import com.example.rickmorty.ui.components.CharacterImage
import com.example.rickmorty.ui.components.CharacteristicInfoSection
import com.example.rickmorty.ui.theme.Roboto
import com.example.rickmorty.ui.theme.UnknownStatus
import com.example.rickmorty.ui.util.calculateGenderColor
import com.example.rickmorty.ui.util.calculateStatusColor

@ExperimentalCoilApi
@Composable
fun CharacterDetailInfo(
    character: Character,
    modifier: Modifier,
    onInfoWithUrlClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CharacterImage(
            modifier = Modifier
                .size(150.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                ), imageModifier = Modifier
                .size(150.dp)
                .clip(CircleShape), character = character
        )
        Spacer(modifier = Modifier.height(16.dp))
        CharacterInformation(character, onInfoWithUrlClick)
    }
}

@Composable
fun CharacterInformation(character: Character, onInfoWithUrlClick: (Int) -> Unit) {

    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = character.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Roboto,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
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
            CharacterStatusAndGenderSection(character = character)
            Information(character, onInfoWithUrlClick)
        }
    }
}

@Composable
fun CharacterStatusAndGenderSection(character: Character) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CharacterState(
            stateName = "Gender:",
            stateValue = character.gender,
            stateColor = calculateGenderColor(gender = character.gender)
        )
        CharacterState(
            stateName = "Status:",
            stateValue = character.status,
            stateColor = calculateStatusColor(status = character.status)
        )

    }
}

@Composable
fun CharacterState(
    stateName: String,
    stateValue: String,
    stateColor: Color
) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stateName, style = MaterialTheme.typography.labelMedium, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .background(if (stateColor != UnknownStatus) stateColor else Color.DarkGray)
                .height(35.dp)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Text(
                text = stateValue,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp
            )
        }
    }
}

@Composable
fun Information(character: Character, onInfoWithUrlClick: (Int) -> Unit) {
    CharacteristicInfoSection(infoName = "Specie", infoValue = character.species)
    CharacteristicInfoSection(infoName = "Type", infoValue = character.type)
    CharacterInfoButton(
        infoName = "Last known location",
        infoValue = character.lastKnownLocationName,
        infoUrl = character.lastKnownLocationUrl,
        onClick = {
            onInfoWithUrlClick(character.lastKnownLocationUrl.takeLastWhile { it != '/' }.toInt())
        }
    )
    CharacterInfoButton(
        infoName = "Origin",
        infoValue = character.originName,
        infoUrl = character.originUrl,
        onClick = {
            onInfoWithUrlClick(character.originUrl.takeLastWhile { it != '/' }.toInt())
        }
    )

}


@Composable
fun CharacterInfoButton(
    infoName: String,
    infoValue: String,
    infoUrl: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Button(
            onClick = onClick,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            enabled = infoUrl.isNotBlank(),
            contentPadding = PaddingValues(0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (infoUrl.isBlank()) Arrangement.Start else Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = infoName, style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = infoValue.ifBlank { "Unknown" },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (infoUrl.isNotBlank()) {
                    Box(
                        modifier = Modifier.padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.right_arrow_for_button),
                            contentDescription = "right arrow"
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
        )
    }
}