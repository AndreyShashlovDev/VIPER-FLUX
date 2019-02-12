package com.sprinter.flux.flux.main

import com.sprinter.fluxlib.BaseData

sealed class MainActionData : BaseData() {
    data class FetchReposAction(val name: String) : MainActionData()
    object FetchingReposAction : MainActionData()
    object ReceiveReposAction : MainActionData()
    data class FetchReposErrorAction(val exception: Throwable) : MainActionData()

    data class FetchReadmeAction(val owner: String, val repoName: String) : MainActionData()
    object FetchingReadmeAction : MainActionData()
    object ReceiveReadmeAction : MainActionData()
    data class FetchReadmeErrorAction(val exception: Throwable) : MainActionData()
}
