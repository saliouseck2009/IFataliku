package com.example.ifataliku

import IFatalikuTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ifataliku.home.reflection.ReflectionDestination
import com.example.ifataliku.home.reflection.ReflectionPage
import com.example.ifataliku.home.souvenirs.SouvenirDestination
import com.example.ifataliku.home.souvenirs.SouvenirPage
import com.example.ifataliku.home.souvenirs.SouvenirViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IFatalikuTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController , startDestination =
                    ReflectionDestination.route ){
                        composable(ReflectionDestination.route) {
                            ReflectionPage(onGoToSouvenir = {
                                navController.navigate(SouvenirDestination.route)
                            })
                        }
                        composable(SouvenirDestination.route) {
                            val viewModel: SouvenirViewModel = hiltViewModel()
                            val pageData by viewModel.souvenirStateData.collectAsState()
                            val state by viewModel.state.collectAsState()
                            SouvenirPage(
                                viewModelEvent = viewModel.viewModelEvent,
                                onEvent= {event -> viewModel.dispatchUiEvent(event)},
                                state = state,
                                data = pageData
                            )
                        }
                    }
                }
            }
        }
    }
}

