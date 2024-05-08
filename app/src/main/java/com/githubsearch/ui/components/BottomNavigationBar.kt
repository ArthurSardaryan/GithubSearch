package com.githubsearch.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import okhttp3.internal.immutableListOf

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {
    val items = remember {
        immutableListOf(Screen.Search, Screen.About)
    }
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Search) }
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = null)
                },
                label = { Text(screen.title) },
                selected = currentScreen == screen,
                onClick = {
                    navHostController.navigate(screen.route)
                    currentScreen = screen
                }
            )
        }
    }
}
