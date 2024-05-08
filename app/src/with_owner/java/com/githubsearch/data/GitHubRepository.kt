package com.githubsearch

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import javax.inject.Inject
@Serializable
class GitHubRepository @Inject constructor(private val api: GitHubApi) {
    fun searchRepositories(query: String): Flow<PagingData<Repository>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { GitHubPagingSource(api, query) }
        ).flow
    }
    suspend fun getUser(login: String): Owner {
        return api.getOwner(login)
    }

}