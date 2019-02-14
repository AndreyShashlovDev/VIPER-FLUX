package com.sprinter.flux.flux.main.action

import com.sprinter.fluxlib.BaseData

sealed class ReadmeActionData : BaseData() {
    data class FetchReadmeAction(val owner: String, val repoName: String) : ReadmeActionData()
    object FetchingReadmeAction : ReadmeActionData()
    object ReceiveReadmeAction : ReadmeActionData()
    data class FetchReadmeErrorAction(val exception: Throwable) : ReadmeActionData(),
        IsErrorAction {
        override fun getThrowable(): Throwable = exception
    }
}
