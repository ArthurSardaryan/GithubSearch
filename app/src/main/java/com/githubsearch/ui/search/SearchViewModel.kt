package com.githubsearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.githubsearch.data.GitHubRepository
import com.githubsearch.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel

class SearchViewModel @Inject constructor(private val repository: GitHubRepository) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val finalQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val repositories: Flow<PagingData<Repository>> = finalQuery
        .flatMapLatest { query ->
            repository.searchRepositories(query)
        }
        .cachedIn(viewModelScope)

    fun onQueryChanged(query: String) {
        _query.value = query
    }

    fun startSearch() {
        finalQuery.value = _query.value
    }
}
