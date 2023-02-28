package com.example.rickmorty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColorScheme(
    primary = Color.DarkGray,
    primaryContainer = Color(0xFF034A53),
    secondary = Color(0xFF9e9e9e),
    background = BackgroundColor,
    onBackground = Color(0xFF3c3e44),
    onSurface = Color(0xFFf5f5f5),
    secondaryContainer = TopBarColor,
    onSecondary = Color(0xFF56858B)
)

@Composable
fun RickMortyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    rememberSystemUiController().setSystemBarsColor(
        color = Color(0xFF272b33)
    )


    MaterialTheme(
        colorScheme = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}