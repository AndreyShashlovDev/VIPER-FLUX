package com.sprinter.flux.di

import com.sprinter.flux.repository.github.GithubRepository
import com.sprinter.flux.repository.github.GithubRepositoryImpl
import com.sprinter.flux.repository.source.network.api.GithubApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [SourceModule::class])
class RepositoriesModule {

    @Singleton
    @Provides
    fun provideGitHubRepository(githubApi: GithubApi): GithubRepository =
        GithubRepositoryImpl(githubApi)
}
