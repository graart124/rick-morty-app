package com.example.rickmorty.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CharacteristicInfoSection(
    infoName: String,
    infoValue: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = infoName, style = MaterialTheme.typography.labelMedium)
        Text(text = infoValue.ifBlank { "Unknown" }, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(3.dp))
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
        )
    }
}