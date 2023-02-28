package com.example.rickmorty.ui.util

import androidx.compose.ui.graphics.Color
import com.example.rickmorty.ui.theme.GreenAlive
import com.example.rickmorty.ui.theme.RedDead
import com.example.rickmorty.ui.theme.UnknownStatus


fun calculateStatusColor(status: String): Color {
    return when (status) {
        "Alive" -> {
            GreenAlive
        }
        "Dead" -> {
            RedDead
        }
        "unknown" -> {
            UnknownStatus
        }
        else -> {
            UnknownStatus
        }
    }
}

fun calculateGenderColor(gender: String): Color {
    return when (gender.lowercase()) {
        "female" -> Color(0xFFE65486)
        "genderless" -> Color(0xFF56858B)
        "male" -> Color.Blue
        "unknown" -> Color.DarkGray
        else -> {
            Color.White
        }
    }
}