package com.example.rickmorty.feature.location.presentation.locations_list

import androidx.compose.ui.focus.FocusState


sealed class LocationsListEvent{
    data class EnteredName(val value:String):LocationsListEvent()
    data class ChangeNameFocus(val focusState: FocusState): LocationsListEvent()
    object LoadLocationsPagineted: LocationsListEvent()
}
