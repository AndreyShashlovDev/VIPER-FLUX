package com.sprinter.flux.repository.github

import com.sprinter.flux.repository.entity.ReadmeEntity
import com.sprinter.flux.repository.entity.RepoEntity
import io.reactivex.Single

interface GithubRepository {

    fun getUserRepos(username: String): Single<List<RepoEntity>>

    fun getReadme(username: String, repo: String): Single<ReadmeEntity>
}
