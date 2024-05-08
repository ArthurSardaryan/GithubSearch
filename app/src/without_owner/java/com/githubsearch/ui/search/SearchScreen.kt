package com.githubsearch.ui.search


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.githubsearch.data.Repository
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val query by viewModel.query.collectAsState()
    val repositories = viewModel.repositories.collectAsLazyPagingItems()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = repositories.loadState.refresh is LoadState.Loading)
    val lazyColumnState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val isScrollToTopVisible by remember { derivedStateOf { lazyColumnState.firstVisibleItemIndex > 0} }
    Column {
        TextField(
            value = query,
            onValueChange = viewModel::onQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search") },
            singleLine = true,
            maxLines = 1,
            trailingIcon = {
                IconButton(onClick = viewModel::startSearch) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            }
        )
        Scaffold(
            floatingActionButton = {
                if (isScrollToTopVisible) {
                    IconButton(onClick = {
                        scope.launch {
                            lazyColumnState.animateScrollToItem(0)
                        }
                    }) {
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
                    }
                }
            }
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { repositories.refresh() },
                modifier = Modifier.padding(it)
            ) {
                when (repositories.loadState.refresh) {
                    is LoadState.Error -> {
                        ErrorItem(message = (repositories.loadState.refresh as LoadState.Error).error.message.toString())
                    }

                    is LoadState.Loading -> {
                    }

                    else -> {
                        LazyColumn(state = lazyColumnState) {
                            items(
                                count = repositories.itemCount,
                                key = repositories.itemKey(),
                                contentType = repositories.itemContentType()) { index ->
                                val repository = repositories[index]
                                if (repository != null) {
                                    RepositoryItem(repository)
                                }
                            }
                            when (repositories.loadState.append) {
                                is LoadState.NotLoading -> Unit
                                LoadState.Loading -> {
                                    item { LoadingItem() }
                                }

                                is LoadState.Error -> {
                                    item {
                                        ErrorItem(message = (repositories.loadState.append as LoadState.Error).error.message.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(
                text = "Loading...",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(CenterVertically)
            )
        }
    }
}


@Composable
fun ErrorItem(message: String) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp),
                imageVector = Icons.Default.Warning,
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(
                color = Color.White,
                text = message,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(CenterVertically)
            )
        }
    }
}
@Composable
fun RepositoryItem(repository: Repository) {
    Card(
        modifier = Modifier
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = repository.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = repository.description ?: "No description available", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Star icon")
                    Text(text = repository.starCount.toString(), style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(4.dp))
                repository.language?.let { language ->
                    Text(text = "Language: $language", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
