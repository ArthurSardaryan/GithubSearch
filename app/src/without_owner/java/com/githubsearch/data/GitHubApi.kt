package com.githubsearch.data

import android.util.Log
import com.githubsearch.data.GitHubResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class GitHubApi(@Contextual private val client: HttpClient) {
    suspend fun searchRepositories(query: String, page: Int, perPage: Int): GitHubResponse {
        return try {
            val response = client.get("https://api.github.com/search/repositories") {
                parameter("q", query)
                parameter("page", page)
                parameter("per_page", perPage)
            }.body<GitHubResponse>()
            response
        } catch (ignored: Exception) {
            Log.e("GitHubApi", "Error loading repositories", ignored)
            GitHubResponse(emptyList())
        }
    }
}

fun createHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
}