package com.sprinter.flux.repository.source.network.api

import com.sprinter.flux.repository.entity.ReadmeEntity
import com.sprinter.flux.repository.entity.RepoEntity
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("/users/{username}/repos")
    fun getUserRepos(@Path("username") userName: String): Single<Response<List<RepoEntity>>>

    @GET("/repos/{owner}/{repo}/readme")
    fun getReadme(
        @Path("owner") userName: String,
        @Path("repo") repo: String
    ): Single<Response<ReadmeEntity>>
}
