package com.githubsearch.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubResponse(
    @SerialName("items")val items: List<Repository>
)