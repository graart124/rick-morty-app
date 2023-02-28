package com.example.rickmorty.feature.location.presentation.locations_list

import com.example.rickmorty.feature.location.domain.models.Location

data class LocationsState(
    val locations:List<Location> = emptyList(),
    val isLoading: Boolean = false,
    val errorLoading:Boolean = false,
    val isSearching:Boolean = false,
    val endReached:Boolean = false
)
