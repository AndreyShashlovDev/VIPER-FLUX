package com.sprinter.flux.flux.main

import android.annotation.SuppressLint
import com.sprinter.flux.interactor.ReposInteractor
import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.MutableStore
import com.sprinter.fluxlib.Reducer

class ReposReducer(
    private val reposInteractor: ReposInteractor,
    private val mainActionsCreator: MainActionsCreator
) : Reducer<GlobalState, MainActionData>() {

    override fun execute(
        store: MutableStore<GlobalState>,
        state: GlobalState,
        action: Action<MainActionData>
    ) {
        when (action.data) {
            is MainActionData.FetchReposAction -> fetchRepos(
                store, state,
                action.data as MainActionData.FetchReposAction
            )
        }
    }

    override fun isServiceAction(action: Action<MainActionData>): Boolean {
        return action.data is MainActionData.FetchReposAction
    }

    @SuppressLint("CheckResult")
    private fun fetchRepos(
        store: MutableStore<GlobalState>,
        state: GlobalState,
        data: MainActionData.FetchReposAction
    ) {
        store.dispatch(mainActionsCreator.fetchingRepos())

        reposInteractor.buildObservable(data.name)
            .subscribe({ result ->
                val updatedState = state.copy(
                    reposState = state.reposState.copy(
                        items = result
                    )
                )
                store.updateState(updatedState)
                store.dispatch(mainActionsCreator.receiveRepos())
            }, { error -> store.dispatch(mainActionsCreator.fetchReposError(error)) })
    }
}
