package com.sprinter.flux.flux.main.action

import com.sprinter.fluxlib.Action

class MainActionsCreator {

    fun fetchRepos(userName: String) =
        Action<ReposActionData>(ReposActionData.FetchReposAction(userName))

    fun fetchingRepos() =
        Action<ReposActionData>(ReposActionData.FetchingReposAction)

    fun receiveRepos() =
        Action<ReposActionData>(ReposActionData.ReceiveReposAction)

    fun fetchReposError(e: Throwable) =
        Action<ReposActionData>(ReposActionData.FetchReposErrorAction(e))

    fun receiveReadme() =
        Action<ReadmeActionData>(ReadmeActionData.ReceiveReadmeAction)

    fun fetchReadme(userName: String, repoName: String) =
        Action<ReadmeActionData>(ReadmeActionData.FetchReadmeAction(userName, repoName))

    fun fetchingReadme() =
        Action<ReadmeActionData>(ReadmeActionData.FetchingReadmeAction)

    fun fetchReadmeError(e: Throwable) =
        Action<ReadmeActionData>(ReadmeActionData.FetchReadmeErrorAction(e))
}
