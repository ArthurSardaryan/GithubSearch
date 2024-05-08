package com.githubsearch

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

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
            SearchScreen(navigateToOwner = {
                navController.navigate("owner/${it}")
            })
        }
        composable(Screen.About.route) {
            AboutScreen()
        }
        composable(route = "owner/{ownerLogin}",
            arguments = listOf(
                navArgument("ownerLogin") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )) {
            OwnerScreen(ownerLogin = it.arguments?.getString("ownerLogin") ?: "")
        }
    }
}