@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rickmorty.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rickmorty.R
import com.example.rickmorty.feature.NavGraphs
import com.example.rickmorty.feature.appCurrentDestinationAsState
import com.example.rickmorty.feature.destinations.CharactersListScreenDestination
import com.example.rickmorty.feature.destinations.Destination
import com.example.rickmorty.feature.destinations.LocationsListScreenDestination
import com.example.rickmorty.feature.startAppDestination

@Composable
fun BottomNavigation(
    navController: NavController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination
    val items = listOf(
        BottomNavItem.CharacterList,
        BottomNavItem.LocationList
    )
    if (items.any { it.destination == currentDestination }) {
        BottomAppBar(
            modifier = Modifier.height(52.dp),
            containerColor = MaterialTheme.colorScheme.background,
            contentPadding = PaddingValues(0.dp)
        ) {
            items.forEach { bottomNavItem ->
                val isSelected = bottomNavItem.destination == currentDestination
                BottomNavItem(
                    modifier = Modifier.weight(1f),
                    bottomNavItem,
                    isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.popBackStack()
                            navController.navigate(bottomNavItem.destination.route)
                        }
                    }
                )
            }
        }
    }

}

@Composable
private fun BottomNavItem(
    modifier: Modifier = Modifier,
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: (BottomNavItem) -> Unit
) {
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSecondary
    Column(
        modifier = modifier
            .clickable {
                onClick(item)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = "icon",
                tint = contentColor
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = item.title, style = MaterialTheme.typography.displayMedium.copy(
                color = contentColor, fontSize = 12.sp
            )
        )
    }
}


private sealed class BottomNavItem(val title: String, val icon: Int, val destination: Destination) {
    object CharacterList : BottomNavItem(
        "Characters",
        R.drawable.bottom_item_character,
        CharactersListScreenDestination
    )

    object LocationList :
        BottomNavItem("Locations", R.drawable.bottom_item_map, LocationsListScreenDestination)
}