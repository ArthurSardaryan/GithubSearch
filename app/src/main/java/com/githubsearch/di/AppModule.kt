package com.githubsearch.di

import com.githubsearch.data.GitHubApi
import com.githubsearch.data.createHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return createHttpClient()
    }

    @Provides
    @Singleton
    fun provideGitHubApi(client: HttpClient): GitHubApi {
        return GitHubApi(client)
    }
}