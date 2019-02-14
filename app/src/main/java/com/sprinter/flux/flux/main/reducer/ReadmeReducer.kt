package com.sprinter.flux.flux.main.reducer

import com.sprinter.flux.flux.main.GlobalState
import com.sprinter.flux.flux.main.action.MainActionsCreator
import com.sprinter.flux.flux.main.action.ReadmeActionData
import com.sprinter.flux.interactor.ReadmeInteractor
import com.sprinter.flux.interactor.ReadmeParams
import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.BaseData
import com.sprinter.fluxlib.ConfigurableStore
import com.sprinter.fluxlib.ReceiveAction
import com.sprinter.fluxlib.Reducer
import io.reactivex.Observable

class ReadmeReducer(
    private val readmeInteractor: ReadmeInteractor,
    private val mainActionsCreator: MainActionsCreator
) : Reducer<GlobalState, ReadmeActionData>() {

    override fun execute(
        store: ConfigurableStore<GlobalState>,
        state: GlobalState,
        action: Action<ReadmeActionData>
    ): Observable<ReceiveAction<Action<BaseData>, GlobalState>> {
        return when (action.data) {
            is ReadmeActionData.FetchReadmeAction ->
                fetchReadme(
                    store, state,
                    action.data as ReadmeActionData.FetchReadmeAction
                )
            else -> ReceiveAction.createEmpty(state)
        }
    }

    override fun isServiceAction(action: Action<ReadmeActionData>): Boolean {
        return action.data is ReadmeActionData.FetchReadmeAction
    }

    private fun fetchReadme(
        store: ConfigurableStore<GlobalState>,
        state: GlobalState,
        data: ReadmeActionData.FetchReadmeAction
    ): Observable<ReceiveAction<Action<BaseData>, GlobalState>> {
        store.dispatch(mainActionsCreator.fetchingReadme())

        return readmeInteractor
            .buildObservable(ReadmeParams(data.owner, data.repoName))
            .map { result -> state.copy(readme = result) }
            .flatMap { updatedState ->
                Observable.just(
                    ReceiveAction<Action<BaseData>, GlobalState>(
                        mainActionsCreator.receiveReadme(),
                        updatedState
                    )
                )
            }
            .onErrorReturn { error: Throwable ->
                ReceiveAction(mainActionsCreator.fetchReadmeError(error), state)
            }
    }
}
