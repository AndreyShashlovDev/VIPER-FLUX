package com.sprinter.flux.flux.main

import com.sprinter.flux.interactor.ReposInteractor
import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.BaseData
import com.sprinter.fluxlib.ConfigurableStore
import com.sprinter.fluxlib.ReceiveAction
import com.sprinter.fluxlib.Reducer
import io.reactivex.Observable

class ReposReducer(
    private val reposInteractor: ReposInteractor,
    private val mainActionsCreator: MainActionsCreator
) : Reducer<GlobalState, ReposActionData>() {

    override fun execute(
        store: ConfigurableStore<GlobalState>,
        state: GlobalState,
        action: Action<ReposActionData>
    ): Observable<ReceiveAction<Action<BaseData>, GlobalState>> {
        return when (action.data) {
            is ReposActionData.FetchReposAction -> fetchRepos(
                store, state,
                action.data as ReposActionData.FetchReposAction
            )
            else -> ReceiveAction.createEmpty(state)
        }
    }

    override fun isServiceAction(action: Action<ReposActionData>): Boolean {
        return action.data is ReposActionData.FetchReposAction
    }

    private fun fetchRepos(
        store: ConfigurableStore<GlobalState>,
        state: GlobalState,
        data: ReposActionData.FetchReposAction
    ): Observable<ReceiveAction<Action<BaseData>, GlobalState>> {
        store.dispatch(mainActionsCreator.fetchingRepos())

        return reposInteractor.buildObservable(data.name)
            .map { result -> state.copy(reposState = state.reposState.copy(items = result)) }
            .flatMap { updatedState ->
                Observable.just(
                    ReceiveAction<Action<BaseData>, GlobalState>(
                        mainActionsCreator.receiveRepos(),
                        updatedState
                    )
                )
            }
            .onErrorReturn { error: Throwable ->
                ReceiveAction(mainActionsCreator.fetchReposError(error), state)
            }
    }
}
