package com.sprinter.flux.flux.main

import com.sprinter.fluxlib.Action

class MainActionsCreator {

    fun fetchRepos(userName: String) =
        Action<MainActionData>(MainActionData.FetchReposAction(userName))

    fun fetchingRepos() =
        Action<MainActionData>(MainActionData.FetchingReposAction)

    fun receiveRepos() =
        Action<MainActionData>(MainActionData.ReceiveReposAction)

    fun fetchReposError(e: Throwable) =
        Action<MainActionData>(MainActionData.FetchReposErrorAction(e))

    fun receiveReadme() =
        Action<MainActionData>(MainActionData.ReceiveReadmeAction)

    fun fetchReadme(userName: String, repoName: String) =
        Action<MainActionData>(MainActionData.FetchReadmeAction(userName, repoName))

    fun fetchingReadme() =
        Action<MainActionData>(MainActionData.FetchingReadmeAction)

    fun fetchReadmeError(e: Throwable) =
        Action<MainActionData>(MainActionData.FetchReadmeErrorAction(e))
}
