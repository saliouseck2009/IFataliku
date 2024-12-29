package com.example.ifataliku.home

import IFatalikuTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ifataliku.home.reflection.ReflectionDestination
import com.example.ifataliku.home.reflection.ReflectionPage
import com.example.ifataliku.home.souvenirs.SouvenirDestination
import com.example.ifataliku.home.souvenirs.SouvenirPage
import com.example.ifataliku.home.souvenirs.SouvenirViewModel
import com.example.ifataliku.home.stats.StatPage
import com.example.ifataliku.home.stats.StatPageDestination


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val destination: String,
    val badgeCount: Int? = null
)

@Preview(showBackground = true)
@Preview(apiLevel = 33)
@Composable
fun HomePagePreview() {
    IFatalikuTheme {
        HomePage()
    }
}
@Preview(showBackground = true)
@Preview(apiLevel = 33)
@Composable
fun HomePageDarkPreview() {
    IFatalikuTheme(darkTheme = true) {
        HomePage()
    }
}

@Composable
fun HomePage(navController : NavHostController = rememberNavController()) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val items = listOf(
        BottomNavigationItem(
            title = "Categories",
            selectedIcon = Icons.Filled.Category,
            unselectedIcon = Icons.Outlined.Category,
            destination = ReflectionDestination.route,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Souvenirs",
            selectedIcon = Icons.Filled.CardGiftcard,
            unselectedIcon = Icons.Outlined.CardGiftcard,
            destination = SouvenirDestination.route,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Stats",
            selectedIcon = Icons.Filled.Analytics,
            unselectedIcon = Icons.Outlined.Analytics,
            destination = StatPageDestination.route,
            hasNews = false,
        )
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            bottomBar = {
                    NavigationBar(
                            containerColor = Color.Transparent,
                    ) {
                        items.forEachIndexed { index, item ->
                            val isSelected = selectedItemIndex == index
                            val menuItemColor = if (isSelected) {
                                MaterialTheme.colorScheme.onBackground
                            } else {
                                MaterialTheme.colorScheme.outline
                            }
                            NavigationBarItem(
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent,
                                ),
                                selected = selectedItemIndex == index,
                                onClick = {
                                    selectedItemIndex = index
                                    navController.navigate(item.destination) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                label = {
                                    Text(
                                        text = item.title,
                                        color = menuItemColor
                                    )
                                },
                                alwaysShowLabel = true,
                                icon = {
                                    BadgedBox(
                                        badge = {}
                                    ) {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) {
                                                item.selectedIcon
                                            } else item.unselectedIcon,
                                            tint = menuItemColor,
                                            contentDescription = item.title
                                        )
                                    }
                                }
                            )
                        }
                    }


            }
        ) {
            NavHost(navController = navController , startDestination = ReflectionDestination.route,
                modifier = Modifier.padding(it) ){

                composable(ReflectionDestination.route) {
                    ReflectionPage()
                }
                composable(SouvenirDestination.route) {
                    val viewModel: SouvenirViewModel = hiltViewModel()
                    val pageData by viewModel.souvenirStateData.collectAsState()
                    val state by viewModel.state.collectAsState()
                    SouvenirPage(
                        viewModelEvent = viewModel.viewModelEvent,
                        onEvent= {event -> viewModel.dispatchUiEvent(event)},
                        addSouvenirViewModelEvent = viewModel.addSouvenirViewModelEvent,
                        state = state,
                        data = pageData
                    )
                }
                composable(StatPageDestination.route) {
                    StatPage()
                }
            }
        }
    }
}