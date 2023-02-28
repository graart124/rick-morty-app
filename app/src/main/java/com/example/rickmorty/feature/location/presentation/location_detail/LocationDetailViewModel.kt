package com.example.rickmorty.feature.location.presentation.location_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.feature.location.domain.models.Location
import com.example.rickmorty.feature.location.domain.use_case.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val useCase: LocationUseCase
) : ViewModel() {

    private val _location = mutableStateOf<Location?>(null)
    val location: State<Location?> = _location

    fun loadLocation(id:Int){
        viewModelScope.launch {
            try {
                _location.value = useCase.getLocation(id)
            }catch (_:Exception){
                delay(5000)
                loadLocation(id)
            }
        }
    }

}