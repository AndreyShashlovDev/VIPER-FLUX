package com.sprinter.flux.repository.source.network

import com.sprinter.flux.repository.source.network.api.GithubApi

interface NetService {

    fun getGithubApi(): GithubApi
}
