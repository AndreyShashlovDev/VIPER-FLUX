package com.sprinter.flux.flux.main

import android.annotation.SuppressLint
import com.sprinter.flux.interactor.ReadmeInteractor
import com.sprinter.flux.interactor.ReadmeParams
import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.MutableStore
import com.sprinter.fluxlib.Reducer

class ReadmeReducer(
    private val readmeInteractor: ReadmeInteractor,
    private val mainActionsCreator: MainActionsCreator
) : Reducer<GlobalState, MainActionData>() {

    override fun execute(
        store: MutableStore<GlobalState>,
        state: GlobalState,
        action: Action<MainActionData>
    ) {
        when (action.data) {
            is MainActionData.FetchReadmeAction ->
                fetchReadme(
                    store, state,
                    action.data as MainActionData.FetchReadmeAction
                )
        }
    }

    override fun isServiceAction(action: Action<MainActionData>): Boolean {
        return action.data is MainActionData.FetchReadmeAction
    }

    @SuppressLint("CheckResult")
    private fun fetchReadme(
        store: MutableStore<GlobalState>,
        state: GlobalState,
        data: MainActionData.FetchReadmeAction
    ) {
        store.dispatch(mainActionsCreator.fetchingReadme())
        readmeInteractor
            .buildObservable(ReadmeParams(data.owner, data.repoName))
            .subscribe({ result ->
                val updatedState = state.copy(
                    readme = result
                )
                store.updateState(updatedState)
                store.dispatch(mainActionsCreator.receiveReadme())
            }, { error -> store.dispatch(mainActionsCreator.fetchReadmeError(error)) })
    }
}
