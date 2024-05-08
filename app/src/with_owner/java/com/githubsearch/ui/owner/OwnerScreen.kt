package com.githubsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerScreen(viewModel: OwnerViewModel = hiltViewModel(), ownerLogin: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getOwner(ownerLogin)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Owner: ${uiState.owner?.login}") })
        }
    ) { paddingValues ->
        Card {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                }
                else if (uiState.error != null) {
                    Text("Error: ${uiState.error}")
                }
                else {
                    uiState.owner?.let { owner ->
                        // Display owner information
                        AvatarImage(url = owner.avatarUrl)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            "Name: ${owner.login}",
                            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                        )
                        // ... other fields ... description
                        Text(
                            "Bio: ${owner.bio ?: "No description available"}",
                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AvatarImage(url: String, modifier: Modifier = Modifier) {
    AsyncImage(model = url, contentDescription = null, modifier = modifier)
}
