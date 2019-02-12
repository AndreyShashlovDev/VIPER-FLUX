package com.sprinter.flux.repository.github

import com.sprinter.flux.repository.entity.ReadmeEntity
import com.sprinter.flux.repository.entity.RepoEntity
import com.sprinter.flux.repository.source.network.api.GithubApi
import io.reactivex.Single

class GithubRepositoryImpl(private val githubApi: GithubApi) : GithubRepository {

    override fun getUserRepos(username: String): Single<List<RepoEntity>> =
        githubApi
            .getUserRepos(username)
            .retry(2)
            .map { response -> response.body() }

    override fun getReadme(username: String, repo: String): Single<ReadmeEntity> =
        githubApi.getReadme(username, repo)
            .retry(2)
            .map { response -> response.body() }
}
