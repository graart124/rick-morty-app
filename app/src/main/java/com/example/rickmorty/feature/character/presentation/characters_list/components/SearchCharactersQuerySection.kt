package com.example.rickmorty.feature.character.presentation.characters_list.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rickmorty.feature.character.domain.models.*

@Composable
fun SearchCharactersQuerySection(
    modifier: Modifier = Modifier,
    searchCharactersQuery: SearchCharactersQuery = SearchCharactersQuery(name = "", status = Status.All, gender = Gender.All),
    onSearchCharactersQueryChange: (SearchCharactersQuery) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            DefaultRadioButton(
                text = "All",
                selected = searchCharactersQuery.status is Status.All,
                onSelect = { onSearchCharactersQueryChange(searchCharactersQuery.copy(status = Status.All)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Alive",
                selected = searchCharactersQuery.status is Status.Alive,
                onSelect = { onSearchCharactersQueryChange(searchCharactersQuery.copy(status = Status.Alive)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Dead",
                selected = searchCharactersQuery.status is Status.Dead,
                onSelect = { onSearchCharactersQueryChange(searchCharactersQuery.copy(status = Status.Dead)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Unknown",
                selected = searchCharactersQuery.status is Status.Unknown,
                onSelect = { onSearchCharactersQueryChange(searchCharactersQuery.copy(status = Status.Unknown)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            DefaultRadioButton(
                text = "All",
                selected = searchCharactersQuery.gender is Gender.All,
                onSelect = {
                    onSearchCharactersQueryChange(searchCharactersQuery.copy(gender = Gender.All))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Male",
                selected = searchCharactersQuery.gender is Gender.Male,
                onSelect = {
                    onSearchCharactersQueryChange(searchCharactersQuery.copy(gender = Gender.Male))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Female",
                selected = searchCharactersQuery.gender is Gender.Female,
                onSelect = {
                    onSearchCharactersQueryChange(searchCharactersQuery.copy(gender = Gender.Female))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Genderless",
                selected = searchCharactersQuery.gender is Gender.Genderless,
                onSelect = {
                    onSearchCharactersQueryChange(searchCharactersQuery.copy(gender = Gender.Genderless))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Unknown",
                selected = searchCharactersQuery.gender is Gender.Unknown,
                onSelect = {
                    onSearchCharactersQueryChange(searchCharactersQuery.copy(gender = Gender.Unknown))
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}