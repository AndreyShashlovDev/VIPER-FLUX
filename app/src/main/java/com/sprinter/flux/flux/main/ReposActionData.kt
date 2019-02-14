package com.sprinter.flux.flux.main

import com.sprinter.fluxlib.BaseData

interface IsErrorAction {
    fun getThrowable(): Throwable
}

sealed class ReposActionData : BaseData() {
    data class FetchReposAction(val name: String) : ReposActionData()
    object FetchingReposAction : ReposActionData()
    object ReceiveReposAction : ReposActionData()
    data class FetchReposErrorAction(val exception: Throwable) : ReposActionData(), IsErrorAction {
        override fun getThrowable(): Throwable = exception
    }
}
