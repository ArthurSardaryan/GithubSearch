package com.githubsearch.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val route: String, val icon:  ImageVector) {
    data object Search : Screen("Search", "search", Icons.Filled.Search)
    data object About : Screen("About", "about", Icons.Filled.Info)
}