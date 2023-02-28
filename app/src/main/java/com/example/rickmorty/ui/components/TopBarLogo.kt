package com.example.rickmorty.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rickmorty.R

@Composable
fun TopBarLogo(
    @DrawableRes resourceId:Int
) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = resourceId),
            contentDescription = "Rick and Morty")
    }
}