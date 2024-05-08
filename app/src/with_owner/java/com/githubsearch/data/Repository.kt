package com.githubsearch

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    @SerialName("id")val id: Long,
    @SerialName("name")val name: String,
    @SerialName("description")val description: String?,
    @SerialName("owner")val owner: Owner,
    @SerialName("stargazers_count")val starCount: Int,
    @SerialName("language")val language: String?
)