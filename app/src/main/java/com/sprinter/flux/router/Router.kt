package com.sprinter.flux.router

interface Router {

    fun openReposPage()
    fun openReadmePage(userName: String, repoName: String)
}
