package com.example.rickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.rickmorty.core.util.NetworkManager
import com.example.rickmorty.feature.NavGraphs
import com.example.rickmorty.ui.components.BottomNavigation
import com.example.rickmorty.ui.theme.RickMortyTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalCoilApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            RickMortyTheme {
                val networkState =
                    NetworkManager.getNetworkLiveData(LocalContext.current).observeAsState()
                val navController = rememberNavController()
                val showLostInternetDialog = remember { mutableStateOf(false) }

                LaunchedEffect(key1 = networkState.value) {
                    if (networkState.value != null && !networkState.value!! && !showLostInternetDialog.value) {
                        showLostInternetDialog.value = true
                    }
                }

                Scaffold(
                    bottomBar = {
                        BottomNavigation(navController = navController)
                    }
                ) {
                    Column(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController
                        )
                    }
                    if (showLostInternetDialog.value) {
                        LostInternetConnectionDialog(showLostInternetDialog)
                    }
                }

            }
        }
    }
}

@Composable
fun LostInternetConnectionDialog(showLostInternetDialog: MutableState<Boolean>) {
    Dialog(onDismissRequest = { showLostInternetDialog.value = false }) {
        Column(modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.lost_inet_connection_dialog),
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { showLostInternetDialog.value = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ){
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        }
    }
}