package com.githubsearch.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.githubsearch.ui.about.AboutScreen
import com.githubsearch.ui.components.Screen
import com.githubsearch.ui.search.SearchScreen

@Composable
fun NavHost(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = Screen.Search.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Search.route) {
            SearchScreen()
        }
        composable(Screen.About.route) {
            AboutScreen()
        }
    }
}